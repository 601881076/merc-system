package com.xhnj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.component.ValidateProcessor;
import com.xhnj.constant.ValidateTypeConstant;
import com.xhnj.mapper.TDismissBatchMapper;
import com.xhnj.mapper.TWithholdAgreeMapper;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.pojo.query.WithholdAgreeParam;
import com.xhnj.service.TWithholdAgreeService;
import com.xhnj.util.BusinUtil;
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
    public IPage conditionQuery(WithholdAgreeParam withholdAgreeParam, Integer pageSize, Integer pageNum) {
        IPage<TWithholdAgree> page = new Page<>(pageNum, pageSize);
        return withholdAgreeMapper.conditionQuery(page,withholdAgreeParam);
    }

    @Override
    public IPage batchPage(Integer pageSize, Integer pageNum) {
        IPage<TDismissBatch> page = new Page<>(pageNum, pageSize);
        QueryWrapper<TDismissBatch> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        return dismissBatchMapper.selectPage(page,wrapper);
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
