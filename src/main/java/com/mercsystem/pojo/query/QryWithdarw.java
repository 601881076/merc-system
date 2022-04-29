package com.mercsystem.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 根据指定条件查询提现
 */
@Getter
@Setter
public class QryWithdarw {

    @ApiModelProperty(value = "提现金额")
    private BigDecimal withdrawAmonut;

    @ApiModelProperty(value = "提现申请人id")
    private Integer userId;
    @ApiModelProperty(value = "提现发起时间")
    private String startTime;
    @ApiModelProperty(value = "提现状态")
    private Integer status;
    @ApiModelProperty(value = "每页显示的数据")
    private Integer size;
    @ApiModelProperty(value = "当前页码")
    private Integer pageindex;
}
