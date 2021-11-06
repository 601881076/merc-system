package com.xhnj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 授权取消批次表
 * </p>
 *
 * @author lk
 * @since 2021-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TDismissBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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

    private Integer totalTrans;

    /**
     * 成功笔数
     */
    private Integer successTrans;

    /**
     * 失败笔数
     */
    private Integer failTrans;

    private Integer status;

    private String createTime;

    private String updateTime;

    /** 查询条件 > 开始时间*/
    @TableField(exist = false)
    private String startTime;

    /** 查询条件 > 结束时间*/
    @TableField(exist = false)
    private String endTime;

    /** 审核结果*/
    @TableField(exist = false)
    private Integer checkResult;

    /** 审核状态*/
    @TableField(exist = false)
    private Integer checkStatus;


}
