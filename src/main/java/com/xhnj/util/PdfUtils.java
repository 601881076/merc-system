package com.xhnj.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
* @Description:    PDF导出工具类
* @Author:         tan_yi
* @CreateDate:     2021/11/6 15:00
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/6 15:00
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public class PdfUtils {

    public static void main(String[] args) throws IOException, DocumentException {
        /*PdfUtils pdf = new PdfUtils();
        String filename = "D:\\Desktop\\test.pdf";
        pdf.createPDF(filename);
        System.out.println("打印完成");*/

        exportReport();

    }

    private static void exportReport() {
        BaseFont bf;
        Font font = null;
        Font font2 = null;
        /*try {

            bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);//创建字体
            font = new Font(bf, 12);//使用字体
            font2 = new Font(bf, 12, Font.BOLD);//使用字体
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        // 创建文档对象
        Document document = new Document();
        try {
            // 获取PDF写入流
            PdfWriter.getInstance(document, new FileOutputStream("D:\\Desktop\\test.pdf"));
            document.open();

            // 设置字体
            //新增改进代码 -------------------------------
            BaseFont bfChinese = BaseFont.createFont("C:\\Windows\\Fonts\\simsun.ttc",  BaseFont.IDENTITY_H, 	BaseFont.NOT_EMBEDDED);//1 不能省略
            Font fontChinese = new Font(bfChinese, 10, Font.NORMAL);



            // 设置标题
            Paragraph elements = new Paragraph("常州武进1区飞行报告", font2);
            elements.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(elements);

            // 插入图片
            /*Image png = Image.getInstance("D:\\Desktop\\正面.jpg");
            png.setAlignment(Image.ALIGN_CENTER);
            document.add(png);*/

            //3、创建左边数据表格PdfPTable iTable，划分为N列

            PdfPTable leftTable = new PdfPTable(4);//创建左边表格
            //4、往左边表格中写入数据，加入iTable中
            //4-1表格中可以合并单元格
            PdfPCell leftCell = new PdfPCell(new Paragraph("Hello"));
            leftCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            leftCell.setFixedHeight(20);
            leftCell.setColspan(4);
            leftTable.addCell(leftCell);

            // 将表格对象塞入PDF
            document.add(leftTable);

            document.addCreationDate();
            document.close();
        } catch (Exception e) {
            System.out.println("file create exception");
        } finally {
            document.close();
        }
    }


    /**
     * 创建PDF文件
     * @param filename
     * @throws IOException
     */
    public void createPDF(String filename) throws IOException {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.addTitle("example of PDF");
            document.open();
            PdfPTable table = createTable(writer);
            document.add(table);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    /**
     * 生成PDF表格
     * @param writer
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static PdfPTable createTable(PdfWriter writer) throws DocumentException, IOException{
        PdfPTable table = new PdfPTable(2);//生成一个两列的表格
        PdfPCell cell;
        int size = 15;
        cell = new PdfPCell(new Phrase("1"));
        cell.setFixedHeight(size);//设置高度
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("2"));
        cell.setFixedHeight(size);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("3"));
        cell.setFixedHeight(size);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("4"));
        cell.setFixedHeight(size);
        table.addCell(cell);
        return table;
    }

    /**
     * 生成PDF表格
     * @param
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static PdfPTable createTable() throws DocumentException, IOException{
        PdfPTable table = new PdfPTable(2);//生成一个两列的表格
        PdfPCell cell;
        int size = 15;
        cell = new PdfPCell(new Phrase("1"));
        cell.setFixedHeight(size);//设置高度
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("2"));
        cell.setFixedHeight(size);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("3"));
        cell.setFixedHeight(size);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("4"));
        cell.setFixedHeight(size);
        table.addCell(cell);
        return table;
    }
}
