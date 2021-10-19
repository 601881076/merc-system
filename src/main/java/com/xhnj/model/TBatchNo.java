package com.xhnj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 批次表
 * </p>
 *
 * @author lk
 * @since 2021-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TBatchNo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer type;

    private Integer isTimming;

    /**
     * 来源(DD、SDSP、MDD)
     */
    private Integer fromType;

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
     * 状态(0->待提交;1->已提交;2->已完成)
     */
    private Integer status;

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

    private Integer isFinish;

    private Date createTime;

    private Date updateTime;


}
