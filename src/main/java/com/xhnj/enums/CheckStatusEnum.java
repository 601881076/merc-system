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
 * `status` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '审核状态(0->待提交;1->已提交;2->审核通过;3->审核拒绝)'
*/
public enum CheckStatusEnum {

    CHECK_STATUS_WAIT("0","待提交"),
    CHECK_STATUS_COMMIT("1","已提交"),
    CHECK_STATUS_PASS("2","审核通过"),
    CHECK_STATUS_REFUSE("3","审核拒绝");



    public String value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    private String value;
    private String desc;

    CheckStatusEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
