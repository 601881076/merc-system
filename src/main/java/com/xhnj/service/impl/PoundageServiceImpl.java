package com.xhnj.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.xhnj.mapper.TBatchDtlMapper;
import com.xhnj.model.PoundageExcel;
import com.xhnj.model.TBatchDtl;
import com.xhnj.pojo.query.PoundageParam;
import com.xhnj.service.PoundageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/*
 @Description
 *@author kang.li
 *@date 2021/9/25 20:46   
 */
@Service
@Slf4j
public class PoundageServiceImpl implements PoundageService {
    @Resource
    private TBatchDtlMapper platformserialMapper;

    @Override
    public void exportExcel(HttpServletResponse response, PoundageParam poundageParam) {
        QueryWrapper<TBatchDtl> wrapper = new QueryWrapper<>();
        wrapper.and(StrUtil.isNotBlank(poundageParam.getStartDate()),wp -> wp.ge("trade_time",poundageParam.getStartDate()));
        wrapper.and(StrUtil.isNotBlank(poundageParam.getEndDate()),wp -> wp.le("trade_time",poundageParam.getEndDate()));
        wrapper.eq("pay_result",2);
        List<TBatchDtl> tBatchDtls = platformserialMapper.selectList(wrapper);
        PoundageExcel poundageExcel=new PoundageExcel();
        poundageExcel.setDateRange(poundageParam.getStartDate()+"---"+poundageParam.getEndDate());
        poundageExcel.setSuccessTrans(tBatchDtls.size());
        if (tBatchDtls.size()!=0) {
            poundageExcel.setBankCode(tBatchDtls.get(0).getPayBankName());
        }
        Double charge=Double.valueOf(tBatchDtls.size()/10);
        poundageExcel.setCharge(charge);
        List<PoundageExcel> list = new ArrayList<>();
        list.add(poundageExcel);
        String fileName = "信息手续费报表";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, PoundageExcel.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setSheetName("信息手续费报表");
            writer.write(list,sheet);
            writer.finish();
            out.flush();
            response.getOutputStream().close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void pdfExport(HttpServletResponse response, PoundageParam poundageParam) {

        try {
            String fileName = "信息手续费报表PDF";
            response.setContentType("application/force-download;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1" ) + ".pdf");
            ServletOutputStream out = response.getOutputStream();

            createPDF(out);

            out.flush();
            response.getOutputStream().close();
            out.close();

        } catch (IOException e) {
            log.error("PDF写入报错1{}", e.getMessage());
        }
    }

    public void createPDF(OutputStream out) {
        // 创建文档对象
        Document document = new Document();


        try {
            // 设置字体
            BaseFont bf;
            Font font = null;
            Font font2 = null;

            bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);//创建字体
            font = new Font(bf, 12);//使用字体
            font2 = new Font(bf, 12, Font.BOLD);//使用字体

            // 获取PDF写入流
            PdfWriter.getInstance(document, out);

            document.open();
            // 设置标题
            Paragraph elements = new Paragraph("常州武进1区飞行报告", font2);
            elements.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(elements);

            // 生成一个两列的表格
            PdfPTable table = new PdfPTable(3);
            PdfPCell cell;
            cell = new PdfPCell(new Phrase("Cell with colspan 3"));
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
            cell.setRowspan(2);
            table.addCell(cell);
            table.addCell("row 1; cell 1");
            table.addCell("row 1; cell 2");
            table.addCell("row 2; cell 1");
            table.addCell("row 2; cell 2");

            // 将表格对象塞入PDF
            document.add(table);
            document.addCreationDate();
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            document.close();

        }


    }
}
