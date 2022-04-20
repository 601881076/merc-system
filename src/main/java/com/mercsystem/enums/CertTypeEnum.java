package com.mercsystem.enums;

/**
 * @ClassName CertTypeEnum.java
 * @Description: 身份证枚举
 * @ProjectName com.mercsystem.enums
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:05
*/
public enum CertTypeEnum {

    ID_CARD("1", "身份证"),
    ID_CARD2("01", "身份证"),
    PASSPORT("02", "护照"),
    OFFICER_CARD("03", "军官证"),
    SOLDIER_CARD("04", "士兵证"),
    PASS_PERMIT("05", "港澳台居民往来通行证"),
    INTERIM_CARD("06", "临时身份证"),
    HOUSEHOLD_REGISTER("07", "户口本"),
    OTHER("08", "其他"),
    POLICE_CARD("10", "警官证"),
    FOREIGNER_RESIDENCE("13", "外国人永久居留证"),
    DEFAULT("", "无效证件类型");

    public static CertTypeEnum of(String certType){
        for(CertTypeEnum e: CertTypeEnum.values()){
            if(e.getValue().equals(certType)){
                return e;
            }
        }
        return CertTypeEnum.DEFAULT;
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

    CertTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
