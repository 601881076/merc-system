package com.xhnj.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.xhnj.enums.CancleStatusEnum;
import com.xhnj.enums.CertTypeEnum;
import com.xhnj.model.TWithholdCancelExcel;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.service.BatchCheckService;
import com.xhnj.service.TBatchChecUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class BatchCheckServiceImpl implements BatchCheckService {

    @Resource
    private TBatchChecUploadService tBatchChecUploadService;

    @Override
    public void exportExcelSuccess(HttpServletResponse response, DisMissBatchQuery disMissBatchQuery) {
        log.info("授权取消审批明细报告,systemBatchList{}", disMissBatchQuery.getSystemBatchList());
        List<TWithholdCancelExcel> list = tBatchChecUploadService.getListToBatchCheck(disMissBatchQuery.getSystemBatchList());

        list.stream().forEach(item -> {
            if (null != item.getStatus()) {
                switch (item.getStatus()) {
                    case "0" :
                        item.setStatus(CancleStatusEnum.systemIDS.desc());
                        break;
                    case "1" :
                        item.setStatus(CancleStatusEnum.systemCIF.desc());
                        break;
                    case "2" :
                        item.setStatus(CancleStatusEnum.systemPAS.desc());
                        break;
                    default:
                        break;
                }
            }
            item.setCertType(CertTypeEnum.of(item.getCertType()).getDesc());
        });

        String fileName = "授权取消审批明细报告";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, TWithholdCancelExcel.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setSheetName("授权取消审批明细报告");
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
