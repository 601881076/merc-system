package com.xhnj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.component.ValidateProcessor;
import com.xhnj.constant.ValidateTypeConstant;
import com.xhnj.mapper.TDismissBatchMapper;
import com.xhnj.mapper.TWithholdAgreeMapper;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.service.TWithholdAgreeService;
import com.xhnj.util.BusinUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/*
 @Description
 *@author kang.li
 *@date 2021/9/19 18:00
 */
@Service
@Slf4j
public class TWithholdAgreeServiceImpl implements TWithholdAgreeService {
    @Autowired
    private ValidateProcessor validateProcessor;
    @Resource
    private TWithholdAgreeMapper withholdAgreeMapper;
    @Resource
    private TDismissBatchMapper dismissBatchMapper;
    @Autowired
    private BusinUtil businUtil;

    @Override
    public IPage conditionQuery(TWithholdAgree withholdAgree, Integer pageSize, Integer pageNum) {
        log.info("授权报告查询");
        IPage<TWithholdAgree> page = new Page<>(pageNum, pageSize);

        // 根据前端上送状态判断本次查询内容
        // 1 > 成功; 2 > 失败；3 > 短信发送未完成授权（需要关联短信表）；4 > 只显示最新授权状态（查询最新一条）；5 > 授权取消查标识为接触的数据
        if (null != withholdAgree.getStatus()) {
            int status = withholdAgree.getStatus();
            log.info("状态{}", status);
            switch (status) {
                case 3:
                    // 短信发送未完成授权（需要关联短信表）
                    return withholdAgreeMapper.selectSmsIsNotCompleted(page,withholdAgree);
                case 4:
                    // 查询最新的一条数据
                    page = new Page<>(1,1);
                    return withholdAgreeMapper.selectLatestDate(page,withholdAgree);
                case 5:
                    //
                    return withholdAgreeMapper.conditionQuery(page,withholdAgree);
                default:
                    return withholdAgreeMapper.conditionQuery(page,withholdAgree);
            }
        }

        return withholdAgreeMapper.conditionQuery(page,withholdAgree);
    }

    @Override
    public IPage batchPage(TDismissBatch dismissBatch,Integer pageSize, Integer pageNum) {
        IPage<TDismissBatch> page = new Page<>(pageNum, pageSize);
//        QueryWrapper<TDismissBatch> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("create_time");
        return dismissBatchMapper.listPage(page,dismissBatch);
    }

    @Override
    public IPage listPage(String batchNo, Integer pageSize, Integer pageNum) {
        IPage<TWithholdAgree> page = new Page<>(pageNum, pageSize);
        List<TWithholdAgree> records = page.getRecords();
        records.stream().forEach(e ->e.setCardNo(businUtil.maskBankCard(e.getCardNo())));
        records.stream().forEach(e ->e.setCertNo(businUtil.maskBankCard(e.getCertNo())));
        records.stream().forEach(e ->e.setMobileNo(businUtil.maskPhone(e.getMobileNo())));
        page.setRecords(records);
        return withholdAgreeMapper.listPageByBatchNo(page,batchNo);
    }

    @Override
    public int uploadExcel(MultipartFile file) {
        //业务校验
        BusinValidatorContext validatorContext = BusinValidatorContext.getCurrentContext();
        validatorContext.setRequestDto(file);
        validateProcessor.validate(ValidateTypeConstant.WITHHOLDAGREE_BATCH_CANCLE);

        return 1;
    }

}
