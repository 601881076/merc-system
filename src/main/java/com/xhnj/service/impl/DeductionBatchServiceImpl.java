package com.xhnj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.mapper.TBatchCheckMapper;
import com.xhnj.mapper.TDeductionBatchQueryMapper;
import com.xhnj.mapper.TDismissBatchQueryMapper;
import com.xhnj.model.TBatchNo;
import com.xhnj.pojo.query.DeductionBatchQuery;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.service.ApprovalManagementService;
import com.xhnj.service.DeductionBatchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class DeductionBatchServiceImpl implements DeductionBatchService {

    @Resource
    TDeductionBatchQueryMapper tDeductionBatchQueryMapper;


    @Override
    public IPage page(DeductionBatchQuery deductionBatchQuery, Integer pageSize, Integer pageNum) {
        IPage<DeductionBatchQuery> page = new Page<>(pageNum, pageSize);
        return tDeductionBatchQueryMapper.listPage(page,deductionBatchQuery);
    }
}
