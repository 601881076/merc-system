package com.xhnj.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
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
import com.xhnj.pojo.vo.AgreeDismissDetailVO;
import com.xhnj.service.TAdminService;
import com.xhnj.service.TWithholdCancelService;
import com.xhnj.util.BusinUtil;
import com.xhnj.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
 @Description 代扣协议批量取消校验
 *@author kang.li
 *@date 2021/9/22 18:32   
 */
@Component
@Slf4j
@BusinValidator(validateTypes = ValidateTypeConstant.WITHHOLDAGREE_BATCH_CANCLE, validateOrder = 1)
public class WithholdAgreeCancleValidator extends BusinValidatorTemplate{
    @Autowired
    private ExcelListener excelListener;

    @Autowired
    private TWithholdCancleMapper withholdCancleMapper;
    @Autowired
    private TDismissBatchMapper dismissBatchMapper;
    @Autowired
    private BusinUtil businUtil;
    @Autowired
    private TAdminService adminService;
    @Autowired
    private TBankCodeMappMapper bankCodeMappMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TDismissBatchCheckMapper dismissBatchCheckMapper;

    @Autowired
    private TWithholdCancelService withholdCancleService;

    @Override
    public void validateInner() throws BusinValidateException {
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
//            password = RSAUtils.decryptDataOnJava(password, privateKey);  //解密密码
        TAdmin admin = adminService.getAdminByUsername(username);
        if(!passwordEncoder.matches(password,admin.getPassword())){
            excelListener.getDatas().clear();
            throw new BusinValidateException("密码不正确");
        }

        List<TWithholdCancel> cancelList = new ArrayList<>();
        try {
            ExcelReader excelReader = new ExcelReader(multipartFile.getInputStream(), null, excelListener);
            //读取信息
            excelReader.read(new Sheet(1, 1, AgreeDismissDetailVO.class));
            //获取数据
            List<Object> list = excelListener.getDatas();
            if(CollUtil.isEmpty(list)){
                throw new BusinValidateException("未读取到数据");
            }
            log.info("receive list size 授权取消上传: {}",list.size());
            if(list.size() > 1000){
                excelListener.getDatas().clear();
                throw new BusinValidateException("明细条数不能超过1000条");
            }

            Integer totalTrans = (Integer) context.get("totalTrans");
            if (totalTrans.intValue() != list.size()){
                excelListener.getDatas().clear();
                BusinValidateException validateException = new BusinValidateException("总数量与上传文件明细数量不一致");
                throw validateException;
            }

            QueryWrapper<TBankCodeMapp> wrapper = new QueryWrapper<>();
            List<TBankCodeMapp> bankCodeMappList = bankCodeMappMapper.selectList(wrapper);
            List<String> bankCodeList = bankCodeMappList.stream().map(TBankCodeMapp::getBusinCode).collect(Collectors.toList());

            //生成系统批次号
            String batchNo = businUtil.getBatchNo("yyyyMMdd", 10);
            context.set("batchNo",batchNo);
            context.set("totalTrans",list.size());
            AgreeDismissDetailVO vo = null;
            String bankCode = null;
            List<String> bankCodeParamList = new ArrayList<>();
            Set<String> set = new HashSet<>();
            for (int i = 0; i < list.size(); i++) {
                vo = (AgreeDismissDetailVO) list.get(i);
                bankCode = vo.getBankCode();

                TWithholdCancel withholdCancle = BeanUtil.copyProperties(vo, TWithholdCancel.class);
                withholdCancle.setSystemType(ValueConstant.SYSTEM_TYPE_MDD);
                withholdCancle.setSourceType(ValueConstant.SOURCE_MDD);
                withholdCancle.setSystemBatch(batchNo);
                withholdCancle.setAgreementId(vo.getAgreementid());

                cancelList.add(withholdCancle);
                set.add(bankCode);
                if(!bankCodeParamList.contains(bankCode)){
                    bankCodeParamList.add(bankCode);
                }
            }

            //校验bankCode
            List<String> diffList = bankCodeParamList.stream()
                    .filter(e -> !bankCodeList.contains(e)).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(diffList)){
                throw new BusinValidateException("银行码不存在: " + diffList.toString());
            }

            List<TWithholdCancel> whCancelList = withholdCancleMapper.getByCardNoAndAgreeId(cancelList);
            if(CollUtil.isNotEmpty(whCancelList)){
                throw new BusinValidateException("[协议编号+卡号]不能重复");
            }

            TDismissBatch dismissBatch = new TDismissBatch();
            dismissBatch.setSystemType(ValueConstant.SYSTEM_TYPE_MDD);
            dismissBatch.setSourceType(ValueConstant.SOURCE_MDD);
            dismissBatch.setSystemBatch((String) context.get("batchNo"));
            dismissBatch.setTotalTrans((Integer) context.get("totalTrans"));
            dismissBatch.setStatus(-1);

            //添加批次，明细
            withholdCancleService.handleBatchCancel(dismissBatch, cancelList, set);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            excelListener.getDatas().clear();
        }
    }
}
