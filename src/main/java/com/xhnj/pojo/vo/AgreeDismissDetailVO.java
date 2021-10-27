package com.xhnj.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.xhnj.annotation.DateTimeAnnotation;
import com.xhnj.annotation.Phone;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/*
 @Description
 *@author kang.li
 *@date 2021/9/22 18:50   
 */
@Data
public class AgreeDismissDetailVO extends BaseRowModel {

    @ExcelProperty(value = "银行代码", index = 0)
    @NotBlank(message = "bankCode param cannot be empty")
    private String bankCode;

    @ExcelProperty(value = "协议编号", index = 1)
    @NotBlank(message = "agreementid param cannot be empty")
    private String agreementid;

    @ExcelProperty(value = "手机号", index = 3)
    @Phone
    private String mobileNo;
    @ExcelProperty(value = "姓名", index = 2)
    @NotBlank(message = "customerName param cannot be empty")
    private String customerName;

    @ExcelProperty(value = "银行卡号", index = 4)
    @NotBlank(message = "cardNo param cannot be empty")
    private String cardNo;

    @ExcelProperty(value = "证件类型", index = 5)
    @NotBlank(message = "certType param cannot be empty")
    private String certType;

    @ExcelProperty(value = "证件号", index = 6)
    @NotBlank(message = "certNo param cannot be empty")
    private String certNo;

    @ExcelProperty(value = "合同结束日期", index = 7)
    @NotBlank(message = "endDate param cannot be empty")
    @DateTimeAnnotation(format = "yyyymmdd", message = "Invalid format，The correct format is：yyyymmdd")
    private String endDate;

    @ExcelProperty(value = "品牌", index = 8)
    @NotBlank(message = "finLabelCd param cannot be empty")
    private String finLabelCd;

}
