package com.xhnj.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TBatDtlExcel extends BaseRowModel {
    @ExcelProperty(value = "数据来源", index = 0)
    private String sourceType;

    @ExcelProperty(value = "批次号", index = 1)
    private String batchNo;

    @ExcelProperty(value = "协议编号", index = 2)
    private String agreementId;

    @ExcelProperty(value = "银行编码", index = 3)
    private String bankCode;

    @ExcelProperty(value = "银行账户号", index = 4)
    private String cardNo;
    @ExcelProperty(value = "交易订单号", index = 5)
    private String orderNo;
    @ExcelProperty(value = "客户名称", index = 6)
    private String customerName;
    @ExcelProperty(value = "交易时间", index = 7)
    private Date tradeTime;
    @ExcelProperty(value = "交易金额", index = 8)
    private BigDecimal money;
    @ExcelProperty(value = "付款户名", index = 10)
    private String RecvAccountName;
    @ExcelProperty(value = "付款银行名称", index = 11)
    private String payBankName;
    @ExcelProperty(value = "用途", index = 12)
    private String usages;
    @ExcelProperty(value = "支付结果", index = 13)
    private String payResult;
    @ExcelProperty(value = "银行返回的状态码", index = 14)
    private String retCode;
    @ExcelProperty(value = "结果原因", index = 15)
    private String reason;
    @ExcelProperty(value = "对账状态", index = 16)
    private String recoState;
    @ExcelProperty(value = "对账结果", index = 17)
    private Integer recoResult;
}
