package com.xhnj.enums;

/**
* @Description:    disMiss_batch 表状态
* @Author:         tan_yi
* @CreateDate:     2021/11/2 20:18
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/2 20:18
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public enum CheckResultEnum {

    CHECK_RESULT_WATI("0","待审核"),
    CHECK_RESULT_PASS("1","通过"),
    CHECK_RESULT_REFUSE("2","拒绝");



    public String value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    private String value;
    private String desc;

    CheckResultEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
