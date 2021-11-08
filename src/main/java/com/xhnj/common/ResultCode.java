package com.xhnj.common;

/**
 * 枚举了一些常用操作码
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    PASSWOR_DOVERRUN(501,"输入密码次数超过3次，用户已被禁用"),
    USER_DISSABLE(502,"用户已被锁，无法登录"),
    PLEASE_SELECT(0001, "请选择一条数据进行操作");

    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
