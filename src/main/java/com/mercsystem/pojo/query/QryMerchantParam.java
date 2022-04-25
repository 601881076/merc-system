package com.mercsystem.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 根据指定条件查询商户信息
 */
@Getter
@Setter
public class QryMerchantParam {
    @ApiModelProperty(value = "商户号")
    private Integer merc_id;  //商户号
    @ApiModelProperty(value = "商户名称")
    private String mercName;  //商户名称

    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;
    @ApiModelProperty(value = "联系人姓名")
    private String contactName;
    @ApiModelProperty(value = "经营开始时间")
    private String manageStartTime;
    @ApiModelProperty(value = "经营截至时间")
    private String manageEndTime;
    @ApiModelProperty(value = "创建人")
    private String createPerson;
    @ApiModelProperty(value = "经营状态")
    private Integer manageStatus;
    @ApiModelProperty(value = "审核状态")
    private Integer checkStatus;
    @ApiModelProperty(value = "商户状态")
    private Integer status;
    @ApiModelProperty(value = "每页显示的数据")
    private Integer size;
    @ApiModelProperty(value = "当前页码")
    private Integer pageindex;
}
