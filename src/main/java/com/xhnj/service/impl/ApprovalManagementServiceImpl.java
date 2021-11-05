package com.xhnj.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.constant.ValueConstance;
import com.xhnj.mapper.TBatchCheckMapper;
import com.xhnj.mapper.TBatchNoMapper;
import com.xhnj.mapper.TDismissBatchMapper;
import com.xhnj.mapper.TDismissBatchQueryMapper;
import com.xhnj.model.*;
import com.xhnj.pojo.query.DeductionBatchQuery;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.service.ApprovalManagementService;
import com.xhnj.service.BatchCheckService;
import com.xhnj.service.TWithholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        int status = option == 1 ? 1 : 2;
        // 审核状态(0->待提交;1->已提交;2->审核通过;3->审核拒绝)
        int checkResult = option == 1 ? 2 : 3;

        return batchCheckMapper.updateStatusByBatchNo(status, checkResult, batchNoList, admin);
    }

    @Override
    public void exportExcel(HttpServletResponse response, DisMissBatchQuery disMissBatchQuery) {
        batchCheckService.exportExcelSuccess(response,disMissBatchQuery);
    }
}
