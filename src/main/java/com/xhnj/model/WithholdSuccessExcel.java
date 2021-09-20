package com.xhnj.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/*
 @Description
 *@author kang.li
 *@date 2021/9/20 16:43   
 */
@Data
public class WithholdSuccessExcel extends BaseRowModel {
    @ExcelProperty(value = "银行账号", index = 0)
    private String recvAccount;
    @ExcelProperty(value = "交易日期", index = 1)
    private Date tradeTime;
    @ExcelProperty(value = "扣款金额(元)", index = 2)
    private BigDecimal money;
    @ExcelProperty(value = "附言", index = 3)
    private String postscript;
    @ExcelProperty(value = "姓名", index = 4)
    private String customerName;
    @ExcelProperty(value = "卡号", index = 5)
    private String cardNo;
    @ExcelProperty(value = "用途", index = 6)
    private String usages;
}
