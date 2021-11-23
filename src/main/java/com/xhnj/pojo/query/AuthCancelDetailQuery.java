package com.xhnj.pojo.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthCancelDetailQuery {
    private static final long serialVersionUID = 1L;

    private Long id;


    /** 查询开始时间*/
    private String startTime;

    /** 查询开始时间*/
    private String endTime;


    private String status;

    private String agreementId;

    private String customerName;
    private String bankCode;
    private String cardNo;
    private String systemBatch;
}
