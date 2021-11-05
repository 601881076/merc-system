package com.xhnj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 代扣协议表
 * </p>
 *
 * @author lk
 * @since 2021-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TWithholdAgree implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer dealFlag;

    private Integer sourceType;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 银行名称
     */
    private String bankName;

    private String systemBatch;

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 请求的唯一序列号
     */
    private String bizserialNo;

    /**
     * 协议编号
     */
    private String agreementId;

    /**
     * 手机号
     */
    private String mobileNo;

    /**
     * 银行账户号
     */
    private String cardNo;

    /**
     * 银行账户名称
     */
    private String customerName;

    /**
     * 证件类型
     */
    private String certType;

    /**
     * 证件号码
     */
    private String certNo;

    /**
     * 短信验证码
     */
    private String checkNo;

    /**
     * 短信发送编号
     */
    private String sendNo;

    /**
     * 动态属键值(json字符串)
     */
    private String ownSpec;

    private String reOwnSpec;

    /**
     * 签约状态(0->处理中;1->成功;2->失败)
     */
    private Integer status;

    private Integer isSend;

    /**
     * 银行返回代码
     */
    private String bankRetCode;

    /**
     * 失败原因
     */
    private String reason;

    private BigDecimal singleMax;

    private BigDecimal dayMax;

    private BigDecimal monthMax;

    private BigDecimal sumMax;

    private String startDate;

    private String endDate;

    private Date createTime;

    private Date updateTime;
}
