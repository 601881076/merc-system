package com.mercsystem.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 活动信息表
 * </p>
 *
 * @author tanyi
 * @since 2022-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TActivityInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    @TableId(value = "activity_id", type = IdType.AUTO)
    private Integer activityId;

    private Integer mercId;

    /**
     * 订单完成要求：0 → 字数超过20; 1 → 上传三张图片;
     */
    private Integer conditions;

    /**
     * 当如试吃总份数。该值每日刷新
     */
    @TableField("try_Eat_Total")
    private Integer tryEatTotal;

    /**
     * 当日试吃剩余分数
     */
    private Integer tryEatRemainderNo;

    private Integer column6;

    /**
     * 该活动返佣金额
     */
    private BigDecimal rebateAmount;

    /**
     * 活动创建时间
     */
    private LocalDateTime createTime;

    /**
     * 商家优惠信息，多条已,分割。例如: 满30减10, 满50减20
     */
    private String discountMessage;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    private String updatePerson;

    /**
     * 返现金额，用户每完成一笔订单，发放该字段 * 100的金币
     */
    private BigDecimal cashbackAmount;


}
