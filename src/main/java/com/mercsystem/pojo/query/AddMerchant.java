package com.mercsystem.pojo.query;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * 商户注册信息
 */
@Getter
@Setter
public class AddMerchant {
    @ApiModelProperty(value = "商户名称", required = true)
    @NotEmpty(message = "mercName")
    private String mercName;
    @ApiModelProperty(value = "商户地址")
    private String address;
    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;
    @ApiModelProperty(value = "联系人姓名")
    private String contactName;
    @ApiModelProperty(value = "商家logo URL")
    private String logo;
    @ApiModelProperty(value = "经营开始时间")
    private String manageStartTime;
    @ApiModelProperty(value = "经营开始时间")
    private String manageEndTime;
    @ApiModelProperty(value = "经营状态")
    private Integer manageStatus;
    @ApiModelProperty(value = "平台类型")
    private String manageType;
    @ApiModelProperty(value = "省编码")
    private String Province;
    @ApiModelProperty(value = "市编码")
    private String city;
    @ApiModelProperty(value = "区编码")
    private String area;
    @ApiModelProperty(value = "经度")
    private Double longitude;
    @ApiModelProperty(value = "纬度")
    private Double latitude;
    @ApiModelProperty(value = "得分")
    private Integer score;
    @ApiModelProperty(value = "审核状态")
    private Integer checkStatus;
    @ApiModelProperty(value = "商户创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "创建人")
    private String createPerson;
    @ApiModelProperty(value = "商户状态")
    private Integer status;
    @ApiModelProperty(value = "上层商户id")
    private Integer racmerchantId;
}
