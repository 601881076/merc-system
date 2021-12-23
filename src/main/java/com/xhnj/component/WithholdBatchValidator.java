package com.xhnj.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xhnj.annotation.BusinValidator;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.common.exception.BusinValidateException;
import com.xhnj.constant.ValidateTypeConstant;
import com.xhnj.constant.ValueConstant;
import com.xhnj.mapper.*;
import com.xhnj.model.*;
import com.xhnj.pojo.vo.WithholdDetailVO;
import com.xhnj.service.TAdminService;
import com.xhnj.service.TBatchDtlService;
import com.xhnj.service.TWithholdService;
import com.xhnj.util.BusinUtil;
import com.xhnj.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
 @Description 手动上传批量代扣excel校验
 *@author kang.li
 *@date 2021/8/16 10:28   
 */
@Component
@Slf4j
@BusinValidator(validateTypes = ValidateTypeConstant.WITHHOLD_BATCH, validateOrder = 1)
public class WithholdBatchValidator extends BusinValidatorTemplate {

    @Autowired
    private TBatchNoMapper batchNoMapper;
    @Autowired
    private BusinUtil businUtil;
    @Autowired
    private TAdminService adminService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ExcelListener excelListener;
    @Autowired
    private TWithholdAgreeMapper tWithholdAgreeMapper;
    @Autowired
    private TBatchCheckMapper batchCheckMapper;
    @Autowired
    private TBatchDtlService batchDtlService;
    @Autowired
    private TBankCodeMappMapper bankCodeMappMapper;

    @Value("${rsa.privateKey}")
    private String privateKey;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void validateInner() throws BusinValidateException {
        //验证权限
        BusinValidatorContext context = BusinValidatorContext.getCurrentContext();

        MultipartFile multipartFile = context.getRequestDto();
        if(multipartFile == null){
            throw new BusinValidateException("未获取到上传文件");
        }
        String fileName = multipartFile.getOriginalFilename().toLowerCase();
        if(!(fileName.endsWith(".xlsx") || fileName.endsWith(".xls"))){
            throw new BusinValidateException("文件格式错误");
        }

        String username = UserUtil.getCurrentAdminUser().getUsername();
        String password= (String) context.get("password");
        TAdmin admin = adminService.getAdminByUsername(username);
        if(!passwordEncoder.matches(password,admin.getPassword())){
            excelListener.getDatas().clear();
            throw new BusinValidateException("密码不正确");
        }

        //读取excel
        try {
            ExcelReader excelReader = new ExcelReader(multipartFile.getInputStream(), null, excelListener);
            //读取信息
            excelReader.read(new Sheet(1, 1, WithholdDetailVO.class));
            //获取数据
            List<Object> list = excelListener.getDatas();
            if(CollUtil.isEmpty(list)){
                throw new BusinValidateException("未读取到数据");
            }
            log.info("receive list size: {}",list.size());
            if(list.size() > 1000){
                excelListener.getDatas().clear();
                throw new BusinValidateException("明细条数不能超过1000条");
            }

            Integer totalTrans = (Integer)context.get("totalTrans");
            if(totalTrans.intValue() != list.size()){
                excelListener.getDatas().clear();
                BusinValidateException validateException = new BusinValidateException("总数量与上传文件明细数量不一致");
                throw validateException;
            }

            //生成批次号,由当前用户+当前日期组成+随机数

            String batchNoStr = username + DateUtil.format(DateUtil.date(), "yyyyMMdd")+RandomUtil.randomNumbers(7);
            context.set("batchNo",batchNoStr);
            List<TBatchDtl> originalList = new ArrayList<>();
            //转换数据类型
            WithholdDetailVO withholdDetailVO = null;
            TBatchDtl batchDtl = null;
            String bankCode = null;
            BigDecimal sumAmt = new BigDecimal("0");
            List<String> bankCodeParamList = new ArrayList<>();
            Set<String> set = new HashSet<>();
            int count = 1;
            for (int i = 0; i < list.size(); i++) {
                withholdDetailVO = (WithholdDetailVO) list.get(i);
                bankCode = withholdDetailVO.getBankCode();

                batchDtl = BeanUtil.copyProperties(withholdDetailVO, TBatchDtl.class);
                batchDtl.setBatchNo(batchNoStr);
                batchDtl.setOrderNo(businUtil.generateOrderNo());
                batchDtl.setPayRecv(ValueConstant.WITHHOLD);
                batchDtl.setSystemType(6);
                batchDtl.setSourceType(ValueConstant.SOURCE_MDD);
                batchDtl.setPayType(0);
                BigDecimal bigDecimal = new BigDecimal(withholdDetailVO.getMoney());
                batchDtl.setMoney(businUtil.coverAmount(bigDecimal,1));
                batchDtl.setCurrency("人民币");

                //合同编号
                batchDtl.setContractNo(withholdDetailVO.getContractNo());
                batchDtl.setSeqNo("00000" + count);
                if(ValueConstant.CMB_BANK_CODE.equals(bankCode)){
                    batchDtl.setSeqNo(businUtil.getSeqNo(count));
                }
                originalList.add(batchDtl);
                set.add(bankCode);
                sumAmt = sumAmt.add(batchDtl.getMoney());

                if(!bankCodeParamList.contains(bankCode)){
                    bankCodeParamList.add(bankCode);
                }
                count++;
            }
            BigDecimal totalAmt = new BigDecimal(context.get("totalAmt").toString());

//            password = RSAUtils.decryptDataOnJava(password, privateKey);  //解密密码
            sumAmt = businUtil.coverAmount(sumAmt,0);

            if (totalAmt.compareTo(sumAmt) != 0){
                excelListener.getDatas().clear();
                BusinValidateException validateException = new BusinValidateException("总金额与上传文件明细总额不一致");
                throw validateException;

            }

            QueryWrapper<TBankCodeMapp> wrapper = new QueryWrapper<>();
            List<TBankCodeMapp> bankCodeMappList = bankCodeMappMapper.selectList(wrapper);
            List<String> bankCodeList = bankCodeMappList.stream().map(TBankCodeMapp::getBusinCode).collect(Collectors.toList());

            //校验bankCode
            List<String> diffList = bankCodeParamList.stream()
                    .filter(e -> !bankCodeList.contains(e)).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(diffList)){
                throw new BusinValidateException("银行编码不存在: " + diffList.toString());
            }

            TBatchNo batchNo = BeanUtil.copyProperties(list.get(0), TBatchNo.class);
            batchNo.setTotalTrans(list.size());
            batchNo.setTotalAmt(businUtil.coverAmount(totalAmt,1));
            batchNo.setSystemType(6);
            batchNo.setSourceType(ValueConstant.SOURCE_MDD);
            batchNo.setBatchNo(batchNoStr);
            batchNo.setIsHold(0);

            batchDtlService.handleBatchDtl(batchNo, originalList, set);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            excelListener.getDatas().clear();
        }

    }

   private void assemblyParam(List<WithholdDetailVO> payList, List<TBatchDtl> detail, int sourceType){
        TBatchDtl pla = null;
        for(WithholdDetailVO vo: payList){
            pla = new TBatchDtl();
            pla.setBatchNo(vo.getBatchNoStr());
            pla.setAgreementId(vo.getAgreementId());
            pla.setSourceType(sourceType);
            pla.setBankCode(vo.getBankCode());
            pla.setOrderNo(vo.getOrderNo());
            pla.setPayType(0);
            BigDecimal bigDecimal = new BigDecimal(vo.getMoney());
            pla.setMoney(bigDecimal);
            pla.setPayRecv(0);
            detail.add(pla);
        }
    }
}
