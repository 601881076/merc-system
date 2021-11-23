package com.xhnj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代扣协议取消表
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TWithholdCancle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 系统来源类型(1->IDS; 2->COF; 3->PAS; 4->CEFT; 5->AMO)
     */
    private Integer systemType;

    /**
     * 数据来源类型(0->上游系统、1->界面操作)
     */
    private Integer sourceType;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 系统批次号
     */
    private String systemBatch;

    /**
     * 品牌(2000->Ford; 2001->Lincoln)
     */
    private String finLabelCd;

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 协议编号
     */
    private String agreementId;

    /**
     * 请求的唯一序列号
     */
    private String bizserialNo;

    /**
     * 手机号
     */
    private String mobileNo;

    /**
     * 银行卡号
     */
    private String cardNo;

    /**
     * 姓名
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
     * 动态属键值(json字符串)
     */
    private String ownSpec;

    /**
     * 银行返回结果动态属键值(json字符串)
     */
    private String reOwnSpec;

    /**
     * 状态(0->处理中;1->成功;2->失败)
     */
    private Integer status;

    /**
     * 是否发送到银行(0->未发送;1->已发送)
     */
    private Integer isSend;

    /**
     * 银行返回代码
     */
    private String bankRetCode;

    /**
     * 失败原因
     */
    private String reason;

    /**
     * 合同结束日期
     */
    private String contractDate;

    /**
     * 协议失效日
     */
    private String endDate;

    private Date createTime;

    private Date updateTime;

}
