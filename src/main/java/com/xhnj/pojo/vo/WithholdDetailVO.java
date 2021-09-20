package com.xhnj.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/*
 @Description
 *@author kang.li
 *@date 2021/9/9 11:15   
 */
@Data
public class WithholdDetailVO extends BaseRowModel {

    @ExcelProperty(value = "协议编号", index = 1)
    @NotBlank(message = "agreementid param cannot be empty")
    private String agreementId;

    @NotBlank(message = "orderNo param cannot be empty")
    private String orderNo;

    private String certType;

    private String certNo;

    @ExcelProperty(value = "姓名", index = 3)
    @NotBlank(message = "customerName param cannot be empty")
    private String customerName;

    private String mobileNo;

    private String cardType;

    @ExcelProperty(value = "银行卡号", index = 4)
    @NotBlank(message = "cardNo param cannot be empty")
    private String cardNo;

    private String summary;

    @ExcelProperty(value = "扣款金额", index = 5)
    @NotNull(message = "tranAmt param cannot be empty")
    private BigDecimal tranAmt;

    private String rem;

    @ExcelProperty(value = "用途", index = 6)
    @NotBlank(message = "usages param cannot be empty")
    private String usages;

    @ExcelProperty(value = "银行代码", index = 0)
    private String bankCode;

    private String batchNoStr;
}