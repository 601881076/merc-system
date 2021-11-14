package com.xhnj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.mapper.TBatchCheckMapper;
import com.xhnj.mapper.TDismissBatchQueryMapper;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TBatchCheck;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.service.ApprovalManagementService;
import com.xhnj.service.BatchCheckService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ApprovalManagementServiceImpl implements ApprovalManagementService {


    @Resource
    TBatchCheckMapper batchCheckMapper;

    @Resource
    TDismissBatchQueryMapper listPageDouble;

    @Resource
    BatchCheckService batchCheckService;

    @Override
    public IPage batchPage(DisMissBatchQuery dismissBatch, Integer pageSize, Integer pageNum) {
        IPage<DisMissBatchQuery> page = new Page<>(pageNum, pageSize);
        return listPageDouble.listPageDouble(page,dismissBatch);
    }

    @Override
    public int update(int option, List<String> batchNoList, TAdmin admin) {
        // option 操作类型：1-批准，2-拒绝

        // 审核结果(0->待审核；1->通过;2->拒绝)
        int checkResult = option == 1 ? 1 : 2;
        // 审核状态(0->待提交;1->已提交;2->审核通过;3->审核拒绝)
        int status = option == 1 ? 2 : 3;

        return batchCheckMapper.updateStatusByBatchNo(status, checkResult, batchNoList, admin);
    }

    @Override
    public void exportExcel(HttpServletResponse response, DisMissBatchQuery disMissBatchQuery) {
        batchCheckService.exportExcelSuccess(response,disMissBatchQuery);
    }

    /**
     * 根据批次查询审核已通过的
     * @param batchNoList
     * @return
     */
    @Override
    public List<TBatchCheck> selectCheckPassBatch(List<String> batchNoList) {
        return batchCheckMapper.selectCheckPassBatch(batchNoList);
    }









































}
