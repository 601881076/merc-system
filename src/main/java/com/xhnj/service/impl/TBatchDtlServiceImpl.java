package com.xhnj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.mapper.TBatchDtlMapper;
import com.xhnj.model.*;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.pojo.vo.WithholdDetailVO;
import com.xhnj.service.TBatchDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
}
