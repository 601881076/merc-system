package com.xhnj.component;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.xhnj.annotation.BusinValidator;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.common.exception.BusinValidateException;
import com.xhnj.constant.ValidateTypeConstant;
import com.xhnj.constant.ValueConstant;
import com.xhnj.mapper.TDismissBatchMapper;
import com.xhnj.mapper.TWithholdAgreeMapper;
import com.xhnj.mapper.TWithholdCancleMapper;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.TWithholdAgree;
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
    private TWithholdAgreeMapper tWithholdAgreeMapper;
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
                excelListener.getDatas().clear();
                throw new BusinValidateException("明细条数不能超过1000条");
            }


            //生成系统批次号
            String batchNo = businUtil.getBatchNo("yyyyMMdd", 10);
            context.set("batchNo",batchNo);
            context.set("totalTrans",list.size());
            AgreeDismissDetailVO vo = null;
            for (int i = 0; i < list.size(); i++) {
                vo = (AgreeDismissDetailVO) list.get(i);
                String bankCode = vo.getBankCode();
                List<TWithholdAgree> tWithholdAgreeIPage = tWithholdAgreeMapper.selectBankcode(bankCode);
                if(tWithholdAgreeIPage.size()<1){
                    throw new BusinValidateException(bankCode+"银行码不存在！！！请检查");
                }
                TWithholdCancle withholdCancle = BeanUtil.copyProperties(vo, TWithholdCancle.class);
                withholdCancle.setSystemType(ValueConstant.SYSTEM_TYPE_MDD);
                withholdCancle.setSourceType(ValueConstant.SOURCE_MDD);
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
                excelListener.getDatas().clear();
                throw new BusinValidateException("密码不正确");
            }
            if (!totalTrans.equals(list.size())){
                excelListener.getDatas().clear();
                BusinValidateException validateException = new BusinValidateException("总数量与上传文件不一致");
                throw validateException;

            }
            List<String> cardNoList = cancelList.stream()
                    .map(TWithholdCancle::getCardNo).distinct()
                    .collect(Collectors.toList());
//            List<TWithholdCancle> withholdCancleList = withholdCancleMapper.getByCardNo(cardNoList);

            // 通过银行卡号 + 协议号 确认数据是否已重复
            boolean flag = true;
            StringBuffer sb = new StringBuffer("以下卡号+协议号重复[");
            for (int i = 0; i < cancelList.size(); i++) {
                TWithholdCancle withholdCancle = withholdCancleMapper.isRepeatData(cancelList.get(i));

                if (null != withholdCancle) {
                    flag = false;
                    sb.append(cancelList.get(i).getCardNo())
                            .append("+")
                            .append(cancelList.get(i).getAgreementId())
                            .append(",");
                }
            }

            if (!flag) {
                excelListener.getDatas().clear();
                throw new BusinValidateException(sb.append("]").toString() + "已重复上传");
            }

            /*if(CollUtil.isNotEmpty(withholdCancleList)){
                boolean flag = true;
                StringBuffer sb = new StringBuffer("卡号[");
                for (TWithholdCancle cancle: withholdCancleList) {
                    if(!"2".equals(cancle.getStatus().toString())){
                        sb.append(cancle.getCardNo()).append(",");
                        flag = false;
                    }
                }

                if (!flag) {
                    excelListener.getDatas().clear();
                    throw new BusinValidateException(sb.append("]").toString() + "已重复上传");
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            excelListener.getDatas().clear();
        }

        //添加批次
        TDismissBatch dismissBatch = new TDismissBatch();
        dismissBatch.setSystemType(ValueConstant.SYSTEM_TYPE_MDD);
        dismissBatch.setSourceType(ValueConstant.SOURCE_MDD);
        dismissBatch.setSystemBatch((String) context.get("batchNo"));
        dismissBatch.setTotalTrans((Integer) context.get("totalTrans"));
        dismissBatch.setStatus(-1);
        dismissBatchMapper.insert(dismissBatch);
        //添加明细
        withholdCancleMapper.addBatch(cancelList);
    }
}
