package com.xhnj.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.enums.BatchStatusEnum;
import com.xhnj.enums.CheckResultEnum;
import com.xhnj.enums.CheckStatusEnum;
import com.xhnj.mapper.TDismissBatchMapper;
import com.xhnj.model.TDismissBatch;
import com.xhnj.service.AuthorizationCancelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* @Description:    授权取消列表 服务类
* @Author:         tan_yi
* @CreateDate:     2021/11/6 15:44
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/6 15:44
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
@Service
@Slf4j
public class AuthorizationCancelServiceImpl implements AuthorizationCancelService {

    @Resource
    private TDismissBatchMapper dismissBatchMapper;

    @Override
    public IPage listPage(TDismissBatch dismissBatch, Integer pageSize, Integer pageNum) {
        IPage<TDismissBatch> page = new Page<>(pageNum, pageSize);
        return dismissBatchMapper.cancelPage(page,dismissBatch);
    }

    @Override
    public void exportExcel(HttpServletResponse response, TDismissBatch dismissBatch) {
        log.info("授权取消列表批量导出");

        // 获取导出数据
        List<TDismissBatch> list = dismissBatchMapper.cancelExport(dismissBatch);

        list.stream().forEach(item -> {
            // 审核结果
            switch (item.getCheckResult()) {
                case 0 :
                    item.setCheckResultString(CheckResultEnum.CHECK_RESULT_WATI.desc());
                    break;
                case 1:
                    item.setCheckResultString(CheckResultEnum.CHECK_RESULT_PASS.desc());
                    break;
                case 2 :
                    item.setCheckResultString(CheckResultEnum.CHECK_RESULT_REFUSE.desc());
                    break;
                default:
                    break;
            }

            // 审核状态
            switch (item.getCheckStatus()) {
                case 0 :
                    item.setCheckStatusString(CheckStatusEnum.CHECK_STATUS_WAIT.desc());
                    break;
                case 1 :
                    item.setCheckStatusString(CheckStatusEnum.CHECK_STATUS_COMMIT.desc());
                    break;
                case 2 :
                    item.setCheckStatusString(CheckStatusEnum.CHECK_STATUS_PASS.desc());
                    break;
                case 3 :
                    item.setCheckStatusString(CheckStatusEnum.CHECK_STATUS_REFUSE.desc());
                    break;
                default:
                    break;
            }

            // 授权处理状态
            // 状态
            switch (item.getStatus()) {
                case -1 :
                    item.setStatusString(BatchStatusEnum.STATUS_HOLD.desc());
                    break;
                case 0 :
                    item.setStatusString(BatchStatusEnum.STATUS_WATI.desc());
                    break;
                case 1 :
                    item.setStatusString(BatchStatusEnum.STATUS_COMMIT.desc());
                    break;
                case 2 :
                    item.setStatusString(BatchStatusEnum.STATUS_REQUEST.desc());
                    break;
                case 3 :
                    item.setStatusString(BatchStatusEnum.STATUS_MSG.desc());
                    break;
                default:
                    break;
            }
        });

        String fileName = "授权取消审批报告";

        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, TDismissBatch.class);
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
