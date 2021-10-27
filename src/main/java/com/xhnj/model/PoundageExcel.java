package com.xhnj.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class PoundageExcel extends BaseRowModel {
    @ExcelProperty(value = "日期/日期范围", index = 0)
    private String dateRange;

    @ExcelProperty(value = "扣款成功交易笔数", index = 1)
    private Integer successTrans;

    @ExcelProperty(value = "扣款银行", index = 2)
    private String bankCode;

    @ExcelProperty(value = "信息服务费（元）", index = 3)
    private Double charge;
}
