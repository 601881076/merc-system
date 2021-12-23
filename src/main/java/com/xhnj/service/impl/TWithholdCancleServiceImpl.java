package com.xhnj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.mapper.TBatchRelationMapper;
import com.xhnj.mapper.TDismissBatchCheckMapper;
import com.xhnj.mapper.TDismissBatchMapper;
import com.xhnj.model.*;
import com.xhnj.mapper.TWithholdCancleMapper;
import com.xhnj.service.TWithholdCancelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.util.BusinUtil;
import com.xhnj.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 代扣协议取消表 服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
@Service
public class TWithholdCancleServiceImpl extends ServiceImpl<TWithholdCancleMapper, TWithholdCancel> implements TWithholdCancelService {
    @Autowired
    private BusinUtil businUtil;

    @Resource
    private TWithholdCancleMapper withholdCancleMapper;
    @Resource
    private TDismissBatchMapper dismissBatchMapper;
    @Resource
    private TDismissBatchCheckMapper dismissBatchCheckMapper;
    @Resource
    private TBatchRelationMapper batchRelationMapper;


    @Override
    public IPage listPage(String batchNo, Integer pageSize, Integer pageNum) {
        IPage<TWithholdCancel> page = new Page<>(pageNum, pageSize);
       /* List<TWithholdCancel> records = page.getRecords();
        records.stream().forEach(e ->e.setCardNo(businUtil.maskBankCard(e.getCardNo())));
        records.stream().forEach(e ->e.setCertNo(businUtil.maskBankCard(e.getCertNo())));
        records.stream().forEach(e ->e.setMobileNo(businUtil.maskPhone(e.getMobileNo())));
        page.setRecords(records);*/
        return withholdCancleMapper.listPageByBatchNo(page,batchNo);
    }

    @Override
    public IPage<TWithholdAgree> conditionQuery(TWithholdAgree withholdAgree, Integer pageSize, Integer pageNum) {
        IPage<TWithholdAgree> page = new Page<>(pageNum, pageSize);
        return withholdCancleMapper.conditionQuery(page,withholdAgree);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int partialInsert(List<TWithholdCancel> list, int count){
        int insertLength = list.size();
        int i = 0;
        List<TWithholdCancel> partialList = null;
        while (insertLength > count){
            partialList = list.subList(i, i+count);
            withholdCancleMapper.addBatch(partialList);
            i = i + count;
            insertLength = insertLength - count;
        }
        if(insertLength > 0){
            partialList = list.subList(i, i+insertLength);
            withholdCancleMapper.addBatch(partialList);
        }
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int handleBatchCancel(TDismissBatch dismissBatch, List<TWithholdCancel> cancelList, Set<String> bankCodeSet) {
        dismissBatchMapper.insert(dismissBatch);
        //添加明细
        partialInsert(cancelList, 80);

        //添加batchRelation
        List<TBatchRelation> relationList = new ArrayList<>();
        TBatchRelation batchRelation = null;
        String systemBatch = cancelList.get(0).getSystemBatch();
        for(String bankCode: bankCodeSet){
            batchRelation = new TBatchRelation();
            batchRelation.setBatchNo(systemBatch);
            batchRelation.setSystemBatch(systemBatch);
            batchRelation.setType(1);
            batchRelation.setBankCode(bankCode);
            batchRelation.setStatus(0);
            relationList.add(batchRelation);
        }
        batchRelationMapper.addBatch(relationList);

        //插入批次审批表
        TDismissBatchCheck dismissBatchCheck = new TDismissBatchCheck();
        dismissBatchCheck.setBatchId(dismissBatch.getId());
        dismissBatchCheck.setStatus(0);
        dismissBatchCheck.setSystemBatch(dismissBatch.getSystemBatch());
        dismissBatchCheck.setUpUserId(UserUtil.getCurrentAdminUser().getId());
        dismissBatchCheck.setUpUserName(UserUtil.getCurrentAdminUser().getUsername());
        dismissBatchCheck.setCheckResult(0);
        dismissBatchCheckMapper.insert(dismissBatchCheck);
        return 1;
    }
}
