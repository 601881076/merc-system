package com.xhnj.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/*
 @Description 失败报告
 *@author kang.li
 *@date 2021/9/20 16:43   
 */
@Data
public class WithholdFailExcel extends BaseRowModel {
    @ExcelProperty(value = "银行账号", index = 0)
    private String recvAccount;
    @ExcelProperty(value = "客户合同号", index = 1)
    private String projectNo;
    @ExcelProperty(value = "姓名", index = 2)
    private String customerName;
    @ExcelProperty(value = "扣款金额(元)", index = 3)
    private BigDecimal money;
    @ExcelProperty(value = "扣款失败原因", index = 4)
    private String reason;
}
