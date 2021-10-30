package com.xhnj.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.math.BigDecimal;

/*
 @Description
 *@author kang.li
 *@date 2021/9/23 14:49   
 */
@Data
public class ErrorCodeExcel extends BaseRowModel {

    @ExcelProperty(value = "bank", index = 0)
    private String bankName;
    @ExcelProperty(value = "bankRetCode", index = 1)
    private String bankRetCode;
    @ExcelProperty(value = "bankRetMessage", index = 2)
    private String bankRetMessage;
    @ExcelProperty(value = "umsCode", index = 3)
    private String umsCode;
    @ExcelProperty(value = "umsMessage", index = 4)
    private String umsMessage;
}
