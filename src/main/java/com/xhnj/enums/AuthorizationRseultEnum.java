package com.xhnj.enums;

/**
* @Description:    是否发送到银行 枚举类
* @Author:         tan_yi
* @CreateDate:     2021/11/2 20:18
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/2 20:18
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public enum AuthorizationRseultEnum {

    PROCESSING("0", "处理中"),
    SUCCESS("1", "成功"),
    FAIL("2", "失败");


    public String value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    private String value;
    private String desc;

    AuthorizationRseultEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
