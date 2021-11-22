package com.xhnj.enums;

/**
* @Description:    身份证枚举
* @Author:         tan_yi
* @CreateDate:     2021/11/2 20:18
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/2 20:18
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public enum CertTypeEnum {

    ID_CARD("1", "身份证");


    public String value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    private String value;
    private String desc;

    CertTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
