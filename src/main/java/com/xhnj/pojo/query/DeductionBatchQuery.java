package com.xhnj.pojo.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xhnj.model.TBatchCheck;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeductionBatchQuery implements Serializable  {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer type;

    private Integer isTimming;

    private String systemType;
    /**
     * 来源(DD、SDSP、MDD)
     */
    private Integer sourceType;

    private String bankCode;

    /**
     * 批次号
     */
    private String batchNo;

    private String systemBatch;

    /**
     * 批次说明
     */
    private String batchDesc;

    /**
     * 总笔数
     */
    private Integer totalTrans;

    /**
     * 总金额
     */
    private BigDecimal totalAmt;

    /**
     * 状态(审核状态(0->待提交;1->已提交;2->审核通过;3->审核拒绝))
     */
    private int status;

    /**
     * 状态(-1->hold ;0->等待运行;1->已提交银行;2->已从银行获取结果;3->已通知上游)
     */
    private String isAudit;

    /**
     * 成功笔数
     */
    private Integer successTrans;

    /**
     * 失败笔数
     */
    private Integer failTrans;

    /**
     * 处理中笔数
     */
    private Integer processTrans;

    private String serialNo;

    private String retOwnSpec;

    private String createTime;

    private String updateTime;

    /** 查询开始时间*/
    private String startTime;

    /** 查询开始时间*/
    private String endTime;

    /** 上传用户*/
    private String upUserName;

    private TBatchCheck batchCheck;

}
