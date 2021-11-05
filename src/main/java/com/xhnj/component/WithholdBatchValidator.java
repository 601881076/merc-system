package com.xhnj.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xhnj.annotation.BusinValidator;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.common.ResultCode;
import com.xhnj.common.exception.BusinValidateException;
import com.xhnj.constant.ValidateTypeConstant;
import com.xhnj.constant.ValueConstance;
import com.xhnj.mapper.TAdminMapper;
import com.xhnj.mapper.TBatchNoMapper;
import com.xhnj.mapper.TBatchDtlMapper;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TBatchNo;
import com.xhnj.model.TBatchDtl;
import com.xhnj.pojo.vo.BatchNoVO;
import com.xhnj.pojo.vo.WithholdDetailVO;
import com.xhnj.service.TAdminService;
import com.xhnj.service.impl.TAdminServiceImpl;
import com.xhnj.util.BusinUtil;
import com.xhnj.util.RSAUtils;
import com.xhnj.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private TBatchDtlMapper platformserialMapper;
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
        //读取excel
        try {
            ExcelReader excelReader = new ExcelReader(multipartFile.getInputStream(), null, excelListener);
            //读取信息
            excelReader.read(new Sheet(1, 1, WithholdDetailVO.class));
            //获取数据
            List<Object> list = excelListener.getDatas();
            if(list == null){
                throw new BusinValidateException("未读取到数据");
            }
            log.info("receive list size: {}",list.size());
            if(list.size() > 1000){
                throw new BusinValidateException("明细条数不能超过1000条");
            }
            //生成批次号,由当前用户+当前日期组成+随机数
            String username = UserUtil.getCurrentAdminUser().getUsername();
            String batchNoStr = username + DateUtil.format(DateUtil.date(), "yyyyMMdd")+RandomUtil.randomNumbers(7);
            context.set("batchNo",batchNoStr);
            List<TBatchDtl> originalList = new ArrayList<>();
            //转换数据类型
            WithholdDetailVO withholdDetailVO = null;
            TBatchDtl platformserial = null;
            BigDecimal totalAmt = new BigDecimal("0");
            for (int i = 0; i < list.size(); i++) {
                withholdDetailVO = (WithholdDetailVO) list.get(i);
                platformserial = BeanUtil.copyProperties(withholdDetailVO, TBatchDtl.class);
                platformserial.setBatchNo(batchNoStr);
                platformserial.setOrderNo(businUtil.generateOrderNo());
                platformserial.setPayRecv(ValueConstance.WITHHOLD);
                platformserial.setFromType(ValueConstance.SOURCE_MDD);
                platformserial.setPayType(0);
                platformserial.setMoney(businUtil.coverAmount(withholdDetailVO.getMoney(),1));
                platformserial.setCurrency("人民币");
                originalList.add(platformserial);
                totalAmt = totalAmt.add(platformserial.getMoney());
            }
            String totalAmt1 = context.get("totalAmt").toString();
            Integer totalTrans = (Integer) context.get("totalTrans");
            String password= (String) context.get("password");
//            password = RSAUtils.decryptDataOnJava(password, privateKey);  //解密密码
            totalAmt=businUtil.coverAmount(totalAmt,0);
            TAdmin admin = adminService.getAdminByUsername(username);
            if(!passwordEncoder.matches(password,admin.getPassword())){
                throw new BusinValidateException("密码不正确");
            }
            if (!(totalTrans.equals(list.size())&&totalAmt1.equals(totalAmt.toString()))){
                BusinValidateException validateException = new BusinValidateException("总金额或总数量与上传文件不一致");
                throw validateException;

            }
            //插入批次数据
            TBatchNo batchNo = BeanUtil.copyProperties(list.get(0), TBatchNo.class);
            batchNo.setTotalTrans(list.size());
            batchNo.setTotalAmt(businUtil.coverAmount(totalAmt,1));
            batchNo.setSourceType(ValueConstance.SOURCE_MDD);
            batchNo.setBatchNo(batchNoStr);

            batchNoMapper.insert(batchNo);
            //批量插入明细
            platformserialMapper.addBatch(originalList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        excelListener.getDatas().clear();

    }

   private void assemblyParam(List<WithholdDetailVO> payList, List<TBatchDtl> detail, int sourceType){
        TBatchDtl pla = null;
        for(WithholdDetailVO vo: payList){
            pla = new TBatchDtl();
            pla.setBatchNo(vo.getBatchNoStr());
            pla.setAgreementId(vo.getAgreementId());
            pla.setFromType(sourceType);
            pla.setBankCode(vo.getBankCode());
            pla.setOrderNo(vo.getOrderNo());
            pla.setPayType(0);
            pla.setMoney(vo.getMoney());
            pla.setPayRecv(0);
            detail.add(pla);
        }
    }
}
