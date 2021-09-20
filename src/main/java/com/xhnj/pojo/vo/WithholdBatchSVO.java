package com.xhnj.pojo.vo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/*
 @Description 批量代扣(一个银行)
 *@author kang.li
 *@date 2021/8/11 12:08   
 */
@Data
public class WithholdBatchSVO {

    @NotBlank(message = "batchNo param cannot be empty")
    private String batchNo;

    @NotBlank(message = "bankCode param cannot be empty")
    private String bankCode;

    @Max(value = 1,message = "isMapAccount param can only be 0 or 1")
    @Min(value = 0,message = "isMapAccount param can only be 0 or 1")
    @NotNull(message = "isMapAccount param cannot be empty")
    private Integer isMapAccount;

    private String batchDesc;

    @NotNull(message = "totalTrans param cannot be empty")
    private Integer totalTrans;

    @NotNull(message = "totalAmt param cannot be empty")
    private Integer totalAmt;

    @NotNull(message = "payList param cannot be empty")
    @Valid
    private List<WithholdDetailVO> payList;

    private String restcode;

    private String url;

    private String bankName;

}
