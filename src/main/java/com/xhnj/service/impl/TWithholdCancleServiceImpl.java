package com.xhnj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.mapper.TWithholdAgreeMapper;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.TWithholdAgree;
import com.xhnj.model.TWithholdCancle;
import com.xhnj.mapper.TWithholdCancleMapper;
import com.xhnj.service.TWithholdCancleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.util.BusinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 代扣协议取消表 服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
@Service
public class TWithholdCancleServiceImpl extends ServiceImpl<TWithholdCancleMapper, TWithholdCancle> implements TWithholdCancleService {
    @Autowired
    private BusinUtil businUtil;

    @Resource
    private TWithholdCancleMapper withholdCancleMapper;


    @Override
    public IPage listPage(String batchNo, Integer pageSize, Integer pageNum) {
        IPage<TWithholdCancle> page = new Page<>(pageNum, pageSize);
        List<TWithholdCancle> records = page.getRecords();
        records.stream().forEach(e ->e.setCardNo(businUtil.maskBankCard(e.getCardNo())));
        records.stream().forEach(e ->e.setCertNo(businUtil.maskBankCard(e.getCertNo())));
        records.stream().forEach(e ->e.setMobileNo(businUtil.maskPhone(e.getMobileNo())));
        page.setRecords(records);
        return withholdCancleMapper.listPageByBatchNo(page,batchNo);
    }

    @Override
    public IPage<TWithholdAgree> conditionQuery(TWithholdAgree withholdAgree, Integer pageSize, Integer pageNum) {
        IPage<TWithholdAgree> page = new Page<>(pageNum, pageSize);
        return withholdCancleMapper.conditionQuery(page,withholdAgree);
    }
}
