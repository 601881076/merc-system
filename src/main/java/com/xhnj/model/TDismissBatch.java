package com.xhnj.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
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
public class TDismissBatch extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 来源(0->DD、1->SDSP、2->MDD)
     */
    @ExcelProperty(value = "数据来源", index = 0)
    private Integer sourceType;

    /**
     * 银行编码
     */
    @ExcelProperty(value = "银行编码", index = 3)
    private String bankCode;

    /**
     * 系统批次号
     */
    @ExcelProperty(value = "批次号", index = 4)
    private String systemBatch;

    /**
     * 批次说明
     */
    @ExcelProperty(value = "批次说明", index = 5)
    private String batchDesc;

    @ExcelProperty(value = "总比数", index = 6)
    private Integer totalTrans;

    /**
     * 成功笔数
     */
    @ExcelProperty(value = "成功笔数", index = 8)
    private Integer successTrans;

    /**
     * 失败笔数
     */
    @ExcelProperty(value = "失败笔数", index = 9)
    private Integer failTrans;

    private Integer status;

    @TableField(exist = false)
    @ExcelProperty(value = "授权处理状态", index = 7)
    private String statusString;

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

    @ExcelProperty(value = "审核结果", index = 1)
    @TableField(exist = false)
    private String checkResultString;


    /** 审核状态*/
    @TableField(exist = false)
    private Integer checkStatus;

    @TableField(exist = false)
    @ExcelProperty(value = "审核状态", index = 2)
    private String checkStatusString;


}
