package com.xhnj.enums;

/**
* @Description:    授权报告结果枚举类
* @Author:         tan_yi
* @CreateDate:     2021/11/2 20:18
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/2 20:18
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public enum AuthorizationReportRseultEnum {
    SMS_SUCCESS("0", "短信发送成功"),

    SMS_FAIL("1", "短信发送失败"),

    AUTHORIZATION_SUCCESS("2", "授权成功"),

    AUTHORIZATION_FAIL("3","授权失败"),

    AUTH_CANCEL_SUCCESS("4","授权取消成功"),

    AUTH_CANCEL_FAIL("5","授权取消失败"),

    AUTH_CANCEL_WAIT("6","授权取消待处理"),;


    public String value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    private String value;
    private String desc;

    AuthorizationReportRseultEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
