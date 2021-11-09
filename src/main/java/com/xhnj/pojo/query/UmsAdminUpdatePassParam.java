package com.xhnj.pojo.query;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 后台用户更改密码参数
 */
@Data
public class UmsAdminUpdatePassParam {

    private String opassword;

    @NotEmpty(message = "新密码不能为空")
    private String password;
    /**
     * 0-更改密码，1-强制更改密码
     */
    @NotBlank(message = "更改类型不能为空")
    private Integer type;
}
