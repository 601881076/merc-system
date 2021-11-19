package com.xhnj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 代扣协议短信表
 * </p>
 *
 * @author tanyi
 * @since 2021-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TWithholdAgreeSms对象", description="代扣协议短信表")
public class TWithholdAgreeSms implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer systemType;

    @ApiModelProperty(value = "数据类型")
    private Integer sourceType;

    @ApiModelProperty(value = "银行编码")
    private String bankCode;

    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "请求唯一序列号")
    private String bizserialNo;

    private String isMobileChk;

    @ApiModelProperty(value = "手机号")
    private String mobileNo;

    @ApiModelProperty(value = "银行卡号")
    private String cardNo;

    private String cardType;

    @ApiModelProperty(value = "姓名")
    private String customerName;

    @ApiModelProperty(value = "证件类型")
    private String certType;

    @ApiModelProperty(value = "证件号码")
    private String certNo;

    @ApiModelProperty(value = "协议编号")
    private String agreementId;

    @ApiModelProperty(value = "项目编号")
    private String projectNo;

    private Long singleMax;

    private Long dayMax;

    private Long monthMax;

    private Long sumMax;

    private Date startDate;

    private Date endDate;

    @ApiModelProperty(value = "短信验证码")
    private String checkNo;

    @ApiModelProperty(value = "0->处理中;1->成功;2->失败")
    private Integer status;

    @ApiModelProperty(value = "是否发送到银行(0->未发送;1->已发送)")
    private Integer isSend;

    @ApiModelProperty(value = "银行返回代码")
    private String bankRetCode;

    @ApiModelProperty(value = "失败原因")
    private String reason;

    @ApiModelProperty(value = "动态属键值(json字符串)")
    private String ownSpec;

    private Date createTime;

    private Date updateTime;

    /** 上传时间*/
    @TableField(exist = false)
    private String uploadTime;

    /** 协议生效日*/
    @TableField(exist = false)
    private String startDateString;

    /** 协议结束日*/
    @TableField(exist = false)
    private String endDateString;




}
