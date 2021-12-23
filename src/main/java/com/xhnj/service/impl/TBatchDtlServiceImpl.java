package com.xhnj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.mapper.TBatchCheckMapper;
import com.xhnj.mapper.TBatchDtlMapper;
import com.xhnj.mapper.TBatchNoMapper;
import com.xhnj.mapper.TBatchRelationMapper;
import com.xhnj.model.*;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.pojo.vo.WithholdDetailVO;
import com.xhnj.service.TBatchDtlService;
import com.xhnj.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 平台流水表 服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-08-11
 */
@Service
@Slf4j
public class TBatchDtlServiceImpl extends ServiceImpl<TBatchDtlMapper, TBatchDtl> implements TBatchDtlService {
    @Resource
    private TBatchDtlMapper batchDtlMapper;
    @Resource
    private TBatchNoMapper batchNoMapper;
    @Resource
    private TBatchRelationMapper batchRelationMapper;
    @Resource
    private TBatchCheckMapper batchCheckMapper;

    @Override
    public List<WithholdSuccessExcel> getList(WithholdParam withholdParam) {
        log.info("进入的是 WithholdSuccessExcel");
        return batchDtlMapper.getList(withholdParam);
    }

    @Override
    public List<TBatchCheckSuccessExcel> getListToBatchCheck(DisMissBatchQuery dismissBatch) {
        log.info("进入的是TBatchCheckSuccessExcel");
        return batchDtlMapper.getListToBatchNo(dismissBatch);
    }

    @Override
    public List<WithholdDetailVO> getByBatchNoList(List<String> list) {
        return batchDtlMapper.getByBatchNoList(list);
    }

    @Override
    public List<WithholdFailExcel> getFailList(WithholdParam withholdParam) {
        return batchDtlMapper.getFailList(withholdParam);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int partialInsert(List<TBatchDtl> list, int count){
        int insertLength = list.size();
        int i = 0;
        List<TBatchDtl> partialList = null;
        while (insertLength > count){
            partialList = list.subList(i, i+count);
            batchDtlMapper.addBatch(partialList);
            i = i + count;
            insertLength = insertLength - count;
        }
        if(insertLength > 0){
            partialList = list.subList(i, i+insertLength);
            batchDtlMapper.addBatch(partialList);
        }
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int handleBatchDtl(TBatchNo batchNo, List<TBatchDtl> originalList, Set<String> bankCodeSet) {
        batchNoMapper.insert(batchNo);

        //添加明细
        partialInsert(originalList, 80);

        //添加batchRelation
        List<TBatchRelation> relationList = new ArrayList<>();
        TBatchRelation batchRelation = null;
        String systemBatch = originalList.get(0).getBatchNo();
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
        TBatchCheck batchCheck = new TBatchCheck();
        batchCheck.setBatchId(batchNo.getId());
        batchCheck.setStatus(0);
        batchCheck.setBatchNo(batchNo.getBatchNo());
        batchCheck.setUpUserId(UserUtil.getCurrentAdminUser().getId());
        batchCheck.setUpUserName(UserUtil.getCurrentAdminUser().getUsername());
        batchCheck.setCheckResult(0);

        List<TBatchCheck> list = new ArrayList<>();
        list.add(batchCheck);
        batchCheckMapper.insert(list);
        return 1;
    }
}
