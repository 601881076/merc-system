package com.xhnj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 平台流水表
 * </p>
 *
 * @author lk
 * @since 2021-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TBatchDtl implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String batchNo;

    private String agreementId;

    private Integer systemType;

    /**
     * 来源(DD、SDSP、MDD)
     */
    private Integer sourceType;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 交易订单号
     */
    private String orderNo;

    /**
     * 公私类型
     */
    private Integer payType;

    /**
     * 交易时间
     */
    private Date tradeTime;

    /**
     * 收付方向(0收款；1付款)
     */
    private Integer payRecv;

    /**
     * 币种
     */
    private String currency;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 收款账号
     */
    private String recvAccount;

    /**
     * 收款户名
     */
    private String recvAccountName;

    /**
     * 收款联行号或银行编码
     */
    private String recvBankNo;

    /**
     * 收款银行名称
     */
    private String recvBankName;

    /**
     * 付款账号
     */
    private String cardNo;

    /**
     * 付款户名
     */
    private String customerName;

    /**
     * 付款联行号
     */
    private String payBankNo;

    /**
     * 付款银行名称
     */
    private String payBankName;

    /**
     * 用途
     */
    private String usages;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 附言
     */
    private String postscript;

    /**
     * 支付结果(待提交; 0正在处理；1成功；2银行返回失败;3连不上银行服务导致失败;4收不到银行响应导致失败；5管理员手工设置失败)
     */
    private Integer payResult;

    /**
     * 银行返回的状态码
     */
    private String retCode;

    /**
     * 结果原因
     */
    private String reason;

    /**
     * 对账状态
     */
    @TableField("recoState")
    private Integer recoState;

    /**
     * 对账结果
     */
    @TableField("recoResult")
    private Integer recoResult;

    /**
     * 对账备注
     */
    @TableField("recoRemark")
    private String recoRemark;

    /**
     * 动态属键值(json字符串)
     */
    private String ownSpec;

    private Date createTime;

    private Date updateTime;

    /**
     * 合同编号
     */
    private String contractNo;

    @TableField(exist = false)
    private String systemBatch;

}
