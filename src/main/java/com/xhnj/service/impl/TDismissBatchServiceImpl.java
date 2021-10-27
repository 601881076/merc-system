package com.xhnj.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.xhnj.model.TDismissBatch;
import com.xhnj.mapper.TDismissBatchMapper;
import com.xhnj.pojo.vo.AgreeDismissDetailVO;
import com.xhnj.service.TDismissBatchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 授权取消批次表 服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-09-22
 */
@Service
public class TDismissBatchServiceImpl extends ServiceImpl<TDismissBatchMapper, TDismissBatch> implements TDismissBatchService {
    @Resource
    private TDismissBatchService dismissBatchService;
    @Override
    public void exportExcel(HttpServletResponse response){
//        List<AgreeDismissDetailVO> list = new ArrayList<>();
//        list.stream().forEach(e ->e.setCardNo(businUtil.maskBankCard(e.getCardNo())));
        String fileName = "授权取消模板";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, AgreeDismissDetailVO.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setSheetName("授权取消");
            writer.write(null,sheet);
            writer.finish();
            out.flush();
            response.getOutputStream().close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
