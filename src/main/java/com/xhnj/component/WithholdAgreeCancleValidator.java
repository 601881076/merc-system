package com.xhnj.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.xhnj.annotation.BusinValidator;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.common.exception.BusinValidateException;
import com.xhnj.constant.ValidateTypeConstant;
import com.xhnj.constant.ValueConstance;
import com.xhnj.mapper.TDismissBatchMapper;
import com.xhnj.mapper.TWithholdCancleMapper;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.TWithholdCancle;
import com.xhnj.pojo.vo.AgreeDismissDetailVO;
import com.xhnj.service.TAdminService;
import com.xhnj.util.BusinUtil;
import com.xhnj.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private PasswordEncoder passwordEncoder;

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

        List<TWithholdCancle> cancelList = new ArrayList<>();
        try {
            ExcelReader excelReader = new ExcelReader(multipartFile.getInputStream(), null, excelListener);
            //读取信息
            excelReader.read(new Sheet(1, 1, AgreeDismissDetailVO.class));
            //获取数据
            List<Object> list = excelListener.getDatas();
            if(list == null){
                throw new BusinValidateException("未读取到数据");
            }
            log.info("receive list size 授权取消上传: {}",list.size());
            if(list.size() > 1000){
                throw new BusinValidateException("明细条数不能超过1000条");
            }
            //校验是否重复取消
            //生成系统批次号
            String batchNo = businUtil.getBatchNo("yyyyMMdd", 10);
            context.set("batchNo",batchNo);
            context.set("totalTrans",list.size());
            AgreeDismissDetailVO vo = null;
            for (int i = 0; i < list.size(); i++) {
                vo = (AgreeDismissDetailVO) list.get(i);
                TWithholdCancle withholdCancle = BeanUtil.copyProperties(vo, TWithholdCancle.class);
                withholdCancle.setSystemType(ValueConstance.SYSTEM_TYPE_MDD);
                withholdCancle.setSourceType(ValueConstance.SOURCE_MDD);
                withholdCancle.setSystemBatch(batchNo);
                withholdCancle.setAgreementId(vo.getAgreementid());

                cancelList.add(withholdCancle);
            }
            String username = UserUtil.getCurrentAdminUser().getUsername();
            String password= (String) context.get("password");
//            password = RSAUtils.decryptDataOnJava(password, privateKey);  //解密密码
            TAdmin admin = adminService.getAdminByUsername(username);
            Integer totalTrans = (Integer) context.get("totalTrans");
            if(!passwordEncoder.matches(password,admin.getPassword())){
                throw new BusinValidateException("密码不正确");
            }
            if (!totalTrans.equals(list.size())){
                BusinValidateException validateException = new BusinValidateException("总数量与上传文件不一致");
                throw validateException;

            }
            List<String> cardNoList = cancelList.stream()
                    .map(TWithholdCancle::getCardNo).distinct()
                    .collect(Collectors.toList());
            List<TWithholdCancle> withholdCancleList = withholdCancleMapper.getByCardNo(cardNoList);

            log.info("agreeList");
            log.info(withholdCancleList.toString());

            if(CollUtil.isNotEmpty(withholdCancleList)){
                for (TWithholdCancle cancle: withholdCancleList) {
                    if(!"2".equals(cancle.getStatus().toString())){
                        throw new BusinValidateException("重置失败数据");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //添加批次
        TDismissBatch dismissBatch = new TDismissBatch();
        dismissBatch.setSystemType(ValueConstance.SYSTEM_TYPE_MDD);
        dismissBatch.setSourceType(ValueConstance.SOURCE_MDD);
        dismissBatch.setSystemBatch((String) context.get("batchNo"));
        dismissBatch.setTotalTrans((Integer) context.get("totalTrans"));
        dismissBatchMapper.insert(dismissBatch);
        //添加明细
        withholdCancleMapper.addBatch(cancelList);
        excelListener.getDatas().clear();
    }
}
