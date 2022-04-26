package com.mercsystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 后台管理用户表
 * </p>
 *
 * @author tanyi
 * @since 2022-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    /**
     * 头像url
     */
    private String icon;

    /**
     * token登录令牌
     */
    private String token;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 备注信息
     */
    private String note;

    /**
     * 帐号启用状态(0->禁用；1->启用)
     */
    private Boolean status;

    /**
     * 首次登录时间
     */
    private LocalDateTime firstLoginTime;

    /**
     * 是否已过期(0->未过期;1->已过期)
     */
    private Boolean isOverdue;

    private LocalDateTime createTime;

    /**
     * 最后登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 最近一次登录IP地址
     */
    private String lastLoginIp;

    private LocalDateTime updateTime;


}
