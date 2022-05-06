package com.mercsystem.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName AdminRequestParam
 * @Description 用户请求参数
 * @Auther tanyi
 * @Date 2022/5/6 12:55
 * @ProjectName merc-system
 * @Version 1.0
 */
@Data
public class AdminRequestParam {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户开始创建时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "用户状态;0->禁用；1->启用")
    private Integer status;

    @ApiModelProperty(value = "每页数量")
    @NotBlank(message = "每页数量不能为空")
    private Integer pageSize;

    @ApiModelProperty(value = "当前页")
    @NotBlank(message = "当前页不能为空")
    private Integer pageNum;


}
