package com.xhnj.pojo.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeductionDetailQuery implements Serializable  {
    private static final long serialVersionUID = 1L;

    private Long id;


    /** 查询开始时间*/
    private String startTime;

    /** 查询开始时间*/
    private String endTime;

    private String batchNo;
    private String agreementId;

    private String bankCode;

    private String customerName;
}
