package com.xhnj.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.xhnj.model.TWithholdCancleExcel;
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
        log.info("授权取消审批明细报告");
        List<TWithholdCancleExcel> list = tBatchChecUploadService.getListToBatchCheck(disMissBatchQuery.getSystemBatchList());

        list.stream().forEach(item -> {
            log.info("sourceType = " + item.getSourceType());
            // 资源类型
            /*if (null != item.getSourceType()) {
                switch (item.getSourceType()) {
                    case "0" :
                        item.setSourceType(SourceTypeEnum.sourceTypeDD.desc());
                        break;
                    case "1" :
                        item.setSourceType(SourceTypeEnum.sourceTypeSDSP.desc());
                        break;
                    case "2" :
                        item.setSourceType(SourceTypeEnum.sourceTypeSDSP.desc());
                        break;
                    default:
                        break;
                }
            }


            // 状态
            if (null != item.getStatus()) {
                switch (item.getStatus()) {
                    case "-1" :
                        item.setStatus(BatchStatusEnum.STATUS_HOLD.desc());
                        break;
                    case "0" :
                        item.setStatus(BatchStatusEnum.STATUS_WATI.desc());
                        break;
                    case "1" :
                        item.setStatus(BatchStatusEnum.STATUS_COMMIT.desc());
                        break;
                    case "2" :
                        item.setStatus(BatchStatusEnum.STATUS_REQUEST.desc());
                        break;
                    case "3" :
                        item.setStatus(BatchStatusEnum.STATUS_MSG.desc());
                        break;
                    default:
                        break;
                }
            }

            // 审核结果
            if (null != item.getCheckResult()) {
                switch (item.getCheckResult()) {
                    case "0" :
                        item.setCheckResult(CheckResultEnum.CHECK_RESULT_WATI.desc());
                        break;
                    case "1" :
                        item.setCheckResult(CheckResultEnum.CHECK_RESULT_PASS.desc());
                        break;
                    case "2" :
                        item.setCheckResult(CheckResultEnum.CHECK_RESULT_REFUSE.desc());
                        break;
                    default:
                        break;
                }
            }


            // 审核状态
            if (null != item.getChkStatus()) {
                switch (item.getChkStatus()) {
                    case "0" :
                        item.setChkStatus(CheckStatusEnum.CHECK_STATUS_WAIT.desc());
                        break;
                    case "1" :
                        item.setChkStatus(CheckStatusEnum.CHECK_STATUS_COMMIT.desc());
                        break;
                    case "2" :
                        item.setChkStatus(CheckStatusEnum.CHECK_STATUS_PASS.desc());
                        break;
                    case "3" :
                        item.setChkStatus(CheckStatusEnum.CHECK_STATUS_REFUSE.desc());
                        break;
                    default:
                        break;
                }
            }*/
        });

        String fileName = "授权取消审批明细报告";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, TWithholdCancleExcel.class);
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
