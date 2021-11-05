package com.xhnj.service.impl;

import com.alibaba.druid.util.StringUtils;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class DeductionBatchServiceImpl implements DeductionBatchService {

    @Resource
    TDeductionBatchQueryMapper tDeductionBatchQueryMapper;


    @Override
    public IPage page(DeductionBatchQuery deductionBatchQuery, Integer pageSize, Integer pageNum) {
        IPage<DeductionBatchQuery> page = new Page<>(pageNum, pageSize);
        String[] statusList = deductionBatchQuery.getStatus().split(",");

        ArrayList< String> arrayList = new ArrayList<String>(statusList.length);
        Collections.addAll(arrayList, statusList);

        /*if (status.contains(",")) {
            String[] statusList = status.split(",");
            String temp = "";
            for (int i = 0; i < statusList.length; i++)
                temp += "'" + statusList[i] + "',";

            temp = temp.substring(0,temp.length() - 1);

            deductionBatchQuery.setStatus(temp);
        } else {
            deductionBatchQuery.setStatus("'" + status + "'");
        }*/



        return tDeductionBatchQueryMapper.listPage(page,deductionBatchQuery, arrayList);
    }


}
