package com.xhnj.pojo.vo;

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

    @NotBlank(message = "bankCode param cannot be empty")
    private String bankCode;

    @NotBlank(message = "agreementid param cannot be empty")
    private String agreementid;

    @Phone
    private String mobileNo;

    @NotBlank(message = "customerName param cannot be empty")
    private String customerName;

    @NotBlank(message = "cardNo param cannot be empty")
    private String cardNo;

    @NotBlank(message = "certType param cannot be empty")
    private String certType;

    @NotBlank(message = "certNo param cannot be empty")
    private String certNo;

    @NotBlank(message = "endDate param cannot be empty")
    @DateTimeAnnotation(format = "yyyymmdd", message = "Invalid format，The correct format is：yyyymmdd")
    private String endDate;

}
