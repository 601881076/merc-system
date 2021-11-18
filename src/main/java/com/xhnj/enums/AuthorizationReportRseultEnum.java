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

    SUCCESS("0", "授权成功"),
    FAIL("1", "未完成授权失败"),
    SMS_SUCCESS_AUTH_FAIL("2", "短信已发送未完成授权"),
    AUTH_CANCEL_SUCCESS("3","授权取消成功"),
    AUTH_CANCEL_FAIL("4","授权取消失败");


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
