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

        List<TWithholdCancel> cancelList = new ArrayList<>();
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
            for (int i = 0; i < list.size(); i++) {
                vo = (AgreeDismissDetailVO) list.get(i);
                bankCode = vo.getBankCode();
               /* List<TWithholdAgree> tWithholdAgreeIPage = tWithholdAgreeMapper.selectBankcode(bankCode);
                if(tWithholdAgreeIPage.size()<1){
                    throw new BusinValidateException(bankCode+"银行码不存在！！！请检查");
                }*/
                TWithholdCancel withholdCancle = BeanUtil.copyProperties(vo, TWithholdCancel.class);
                withholdCancle.setSystemType(ValueConstant.SYSTEM_TYPE_MDD);
                withholdCancle.setSourceType(ValueConstant.SOURCE_MDD);
                withholdCancle.setSystemBatch(batchNo);
                withholdCancle.setAgreementId(vo.getAgreementid());

                // 获取银行名称
               // withholdCancle.setBankName(tWithholdAgreeIPage.get(0).getBankName());

                cancelList.add(withholdCancle);
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

           /* List<String> cardNoList = cancelList.stream()
                    .map(TWithholdCancle::getCardNo).distinct()
                    .collect(Collectors.toList());
            List<TWithholdCancle> withholdCancleList = withholdCancleMapper.getByCardNo(cardNoList);

            // 通过银行卡号 + 协议号 确认数据是否已重复
            boolean flag = true;
            StringBuffer sb = new StringBuffer("以下卡号+协议号重复[");
            for (int i = 0; i < cancelList.size(); i++) {
                List<TWithholdCancle> withholdCancle = withholdCancleMapper.isRepeatData(cancelList.get(i));

                if (0 < withholdCancle.size()) {
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
            }*/


            List<TWithholdCancel> whCancelList = withholdCancleMapper.getByCardNoAndAgreeId(cancelList);
            if(CollUtil.isNotEmpty(whCancelList)){
                throw new BusinValidateException("[协议编号+卡号]不能重复");
            }

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
        withholdCancleService.partialInsert(cancelList, 80);


        // 授权取消审核批次表新增数据
        TDismissBatchCheck dismissBatchCheck = new TDismissBatchCheck();
        dismissBatchCheck.setBatchId(dismissBatch.getId());
        dismissBatchCheck.setSystemBatch(dismissBatch.getSystemBatch());
        dismissBatchCheck.setUpUserId(UserUtil.getCurrentAdminUser().getId());
        dismissBatchCheck.setUpUserName(UserUtil.getCurrentAdminUser().getUsername());
        dismissBatchCheckMapper.insert(dismissBatchCheck);

    }


    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        list1.add("a");
        list1.add("b");
        //list1.add("c");
        list1.add("d");

        list2.add("a");
        list2.add("b");
        list2.add("c");

        List<String> colList = list1.stream()
                .filter
                        (
                                e -> !list2.contains(e)
                        )
                .collect(Collectors.toList());

        if(CollUtil.isNotEmpty(colList)){
            System.out.println("存在差集");
        } else {
            System.out.println("不存在差集");
        }

    }
}
