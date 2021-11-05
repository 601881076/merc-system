package com.xhnj.enums;

/**
* @Description:    系统来源类型枚举
* @Author:         tan_yi
* @CreateDate:     2021/11/2 20:18
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/2 20:18
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public enum SystemTypeEnum {

    systemIDS("1", "IDS"),
    systemCIF("2", "CIF"),
    systemPAS("3", "CEFT"),
    systemAMO("4", "AMO"),
    systemUpload("6", "后台管理页面上传");


    public String value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    private String value;
    private String desc;

    SystemTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}