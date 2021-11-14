package com.xhnj.pojo.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xhnj.model.TDismissBatchCheck;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DisMissBatchQuery implements Serializable  {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer systemType;

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

    /**
     * 状态(审核状态(0->待提交;1->已提交;2->审核通过;3->审核拒绝))
     */
    private String status;

    /**
     * 状态(-1->hold ;0->等待运行;1->已提交银行;2->已从银行获取结果;3->已通知上游)
     */
    private String isAudit;

    private Date createTime;

    private Date updateTime;

    /** 查询开始时间*/
    private String startTime;

    /** 查询开始时间*/
    private String endTime;

    /** 上传用户*/
    private String upUserName;

    private TDismissBatchCheck batchCheckList;

    private List<String> systemBatchList;


}
