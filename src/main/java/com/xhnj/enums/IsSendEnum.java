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
public enum IsSendEnum {

    SEND_SUCCESS("0", "未发送"),
    SEND_FAIL("1", "已发送");


    public String value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    private String value;
    private String desc;

    IsSendEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
