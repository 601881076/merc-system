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
    private TBatchDtlMapper platformserialMapper;

    @Override
    public List<WithholdSuccessExcel> getList(WithholdParam withholdParam) {
        log.info("进入的是 WithholdSuccessExcel");
        return platformserialMapper.getList(withholdParam);
    }

    @Override
    public List<TBatchCheckSuccessExcel> getListToBatchCheck(DisMissBatchQuery dismissBatch) {
        log.info("进入的是TBatchCheckSuccessExcel");
        return platformserialMapper.getListToBatchNo(dismissBatch);
    }

    @Override
    public List<WithholdDetailVO> getByBatchNoList(List<String> list) {
        return platformserialMapper.getByBatchNoList(list);
    }

    @Override
    public List<WithholdFailExcel> getFailList(WithholdParam withholdParam) {
        return platformserialMapper.getFailList(withholdParam);
    }

}
