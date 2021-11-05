package com.xhnj.pojo.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xhnj.model.TBatchCheck;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeductionDetailQuery implements Serializable  {
    private static final long serialVersionUID = 1L;

    private Long id;


    /** 查询开始时间*/
    private String startTime;

    /** 查询开始时间*/
    private String endTime;


    private int status;
}
