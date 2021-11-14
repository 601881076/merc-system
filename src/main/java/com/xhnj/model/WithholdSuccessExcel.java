package com.xhnj.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 @Description
 *@author kang.li
 *@date 2021/9/20 16:43
 */
@Data
public class WithholdSuccessExcel extends BaseRowModel {
    @ExcelProperty(value = "Bank Account", index = 0)
    private String recvAccount;
    @ExcelProperty(value = "Effective (Trade)Date", index = 1)
    private Date tradeTime;
    @ExcelProperty(value = "Credit (Payment)Amount", index = 2)
    private BigDecimal money;
    @ExcelProperty(value = "Description", index = 3)
    private String description;
    @ExcelProperty(value = "Customer Name", index = 4)
    private String customerName;
    @ExcelProperty(value = "Customer FAFC Account", index = 5)
    private String contractNo;
    @ExcelProperty(value = "Date Posted to Account", index = 6)
    private String tradedt;
    @ExcelProperty(value = "Belong to", index = 7)
    private String belong;
    @ExcelProperty(value = "Comments", index = 8)
    private String comments;
    @ExcelProperty(value = "Effective Date", index = 9)
    private String effdt;

}
