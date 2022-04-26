package com.mercsystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *  消费者表
 * </p>
 *
 * @author tanyi
 * @since 2022-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名称
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名称 - 手机号
     */
    private String username;

    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户微信openID
     */
    private String openId;

    /**
     * 用户头像logoURL
     */
    private String logo;

    /**
     * 用户拥有金币的数量
     */
    private Integer goldNo;

    /**
     * 用户拥有的粮票数量
     */
    private Integer grainTicketNo;

    /**
     * 已提现金额
     */
    private BigDecimal currentAmount;

    /**
     * 用户状态: 0 -> 启用; 1 -> 关闭
     */
    private Integer status;

    /**
     * token令牌
     */
    private String token;

    /**
     * 用户创建日期
     */
    private LocalDateTime createTime;

    /**
     * 用户修改日期
     */
    private LocalDateTime updateTime;

    /**
     * 用户父级ID
     */
    private Integer parentId;

    /**
     * 微信登录接口会话秘钥
     */
    private String sessionKey;


}
