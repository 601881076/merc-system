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
    @ExcelProperty(value = "FAFC收款银行账号", index = 0)
    private String recvAccount;
    @ExcelProperty(value = "扣款日期", index = 1)
    private Date tradeTime;
    @ExcelProperty(value = "扣款金额(元)", index = 2)
    private BigDecimal money;
    @ExcelProperty(value = "银行卡户名", index = 3)
    private String customerName;
    @ExcelProperty(value = "合同号", index = 4)
    private String contractNo;
    @ExcelProperty(value = "Belong to", index = 5)
    final private String Belong="TA";
    @ExcelProperty(value = "Comments", index = 6)
    private String comments;

}
