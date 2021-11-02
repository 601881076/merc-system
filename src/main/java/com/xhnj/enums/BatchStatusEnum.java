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
public enum BatchStatusEnum {

    STATUS_HOLD("-1","hold"),
    STATUS_WATI("0","等待运行"),
    STATUS_COMMIT("1","已提交银行"),
    STATUS_REQUEST("2","已从银行获取结果"),
    STATUS_MSG("3","已通知上游");


    public String value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    private String value;
    private String desc;

    BatchStatusEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
