package com.xhnj.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.mapper.TBatchCheckMapper;
import com.xhnj.mapper.TDismissBatchQueryMapper;
import com.xhnj.model.TBatchCheckSuccessExcel;
import com.xhnj.model.TBatchNo;
import com.xhnj.model.WithholdSuccessExcel;
import com.xhnj.pojo.query.DeductionBatchQuery;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.service.ApprovalManagementService;
import com.xhnj.service.BatchCheckService;
import com.xhnj.service.TBatchDtlService;
import com.xhnj.service.TWithholdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class BatchCheckServiceImpl implements BatchCheckService {

    @Resource
    private TBatchDtlService platformserialService;

    @Override
    public void exportExcelSuccess(HttpServletResponse response, DisMissBatchQuery disMissBatchQuery) {
        List<TBatchCheckSuccessExcel> list = platformserialService.getListToBatchCheck(disMissBatchQuery);

        String fileName = "授权取消审批报告";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, WithholdSuccessExcel.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setSheetName("授权取消审批报告");
            writer.write(list,sheet);
            writer.finish();
            out.flush();
            response.getOutputStream().close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
