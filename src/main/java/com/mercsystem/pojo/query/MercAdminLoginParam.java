package com.mercsystem.pojo.query;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 后台用户登录参数
 */
@Data
public class MercAdminLoginParam {
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;

}
