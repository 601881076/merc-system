package com.xhnj.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class TWithholdAgreeExcel extends BaseRowModel {

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
    @ExcelProperty(value = "银行名称", index = 6)
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
    @ExcelProperty(value = "缴费编号", index = 2)
    private String agreementId;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号", index = 4)
    private String mobileNo;

    /**
     * 银行账户号
     */
    @ExcelProperty(value = "银行卡号", index = 5)
    private String cardNo;

    /**
     * 银行账户名称
     */
    @ExcelProperty(value = "客户名称", index = 7)
    private String customerName;

    /**
     * 证件类型
     */
    @ExcelProperty(value = "证件类型", index = 8)
    private String certType;

    /**
     * 证件号码
     */
    @ExcelProperty(value = "证件号码", index = 9)
    private String certNo;

    /**
     * 短信验证码
     */
    @ExcelProperty(value = "短信验证码", index = 10)
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
    @ExcelProperty(value = "授权结果", index = 3)
    private String status;

    @ExcelProperty(value = "是否发送到银行", index = 12)
    private String isSend;

    /**
     * 银行返回代码
     */
    @ExcelProperty(value = "银行返回代码", index = 13)
    private String bankRetCode;

    /**
     * 失败原因
     */
    @ExcelProperty(value = "失败原因", index = 11)
    private String reason;

    @ExcelProperty(value = "授权金额(元)", index = 14)
    private BigDecimal singleMax;

    @ExcelProperty(value = "当天累计(元)", index = 15)
    private BigDecimal dayMax;

    @ExcelProperty(value = "当月累计(元)", index = 16)
    private BigDecimal monthMax;

    @ExcelProperty(value = "累计限额(元)", index = 17)
    private BigDecimal sumMax;

    @ExcelProperty(value = "协议生效日", index = 18)
    private String startDate;

    @ExcelProperty(value = "协议结束日", index = 19)
    private String endDate;

    @ExcelProperty(value = "日期", index = 0)
    private Date createTime;

    @ExcelProperty(value = "上传日期", index = 20)
    private String uploadTime;

    @ExcelProperty(value = "系统来源", index = 1)
    private String systemType;

    private Date updateTime;
}
