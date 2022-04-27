package com.mercsystem.pojo.query;

import lombok.Data;

/**
 * @ClassName ActivityRequestParam
 * @Description 活动管理请求参数
 * @Auther tanyi
 * @Date 2022/4/27 22:22
 * @ProjectName merc-system
 * @Version 1.0
 */

@Data
public class ActivityRequestParam {

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 活动id
     */
    private Integer activityId;


    /**
     * 开始时间
     */
    private String startTime;


    /**
     * 结束时间
     */
    private String endTime;
}
