package com.xhnj.service.impl;

import com.alibaba.excel.exception.ExcelAnalysisException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TDismissBatchCheck;
import com.xhnj.mapper.TDismissBatchCheckMapper;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.service.TDismissBatchCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 授权取消批次审批表 服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
@Service
public class TDismissBatchCheckServiceImpl extends ServiceImpl<TDismissBatchCheckMapper, TDismissBatchCheck> implements TDismissBatchCheckService {

    @Resource
    private TDismissBatchCheckMapper dismissBatchCheckMapper;


    @Override
    public IPage batchPage(DisMissBatchQuery dismissBatch, Integer pageSize, Integer pageNum) {
        IPage<DisMissBatchQuery> page = new Page<>(pageNum, pageSize);
        String[] statusList = dismissBatch.getStatus().split(",");

        ArrayList< String> arrayList = new ArrayList<String>(statusList.length);
        Collections.addAll(arrayList, statusList);
        return dismissBatchCheckMapper.listPage(page,dismissBatch,arrayList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(int option, List<String> batchNoList, TAdmin admin) {
        // option 操作类型：1-批准，2-拒绝

        // 审核结果(0->待审核；1->通过;2->拒绝)
        int checkResult = option == 1 ? 1 : 2;
        // 审核状态(0->待提交;1->已提交;2->审核通过;3->审核拒绝)
        int status = option == 1 ? 2 : 3;

        return dismissBatchCheckMapper.updateStatusByBatchNo(status, checkResult, batchNoList, admin);
    }
}
