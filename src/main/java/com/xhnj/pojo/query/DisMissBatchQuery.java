package com.xhnj.pojo.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xhnj.model.TBatchCheck;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DisMissBatchQuery implements Serializable  {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 来源(0->DD、1->SDSP、2->MDD)
     */
    private Integer sourceType;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 系统批次号
     */
    private String systemBatch;

    /**
     * 批次说明
     */
    private String batchDesc;

    /** 总比数*/
    private Integer totalTrans;

    /**
     * 成功笔数
     */
    private Integer successTrans;

    /**
     * 失败笔数
     */
    private Integer failTrans;

    /** 状态*/
    private Integer status;

    private String createTime;

    private String updateTime;


    private List<TBatchCheck> batchCheckList;


}
