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
    systemINTERFACE("0", "INTERFACE"),
    systemIDS("1", "IDS"),
    systemCIF("2", "COF"),
    systemPAS("3", "PAS"),
    systemCEFT("4", "CEFT"),
    systemAMO("5", "AMO"),
    systemUpload("6", "后台管理页面上传"),
    DEFAULT("", "INTERFACE");

    public static SystemTypeEnum of(String systemType){
        for(SystemTypeEnum e: SystemTypeEnum.values()){
            if(e.getValue().equals(systemType)){
                return e;
            }
        }
        return SystemTypeEnum.DEFAULT;
    }


    public String value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String value;
    private String desc;

    SystemTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
