package com.mercsystem.pojo.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 商户导出条件
 */
@Getter
@Setter
public class ExlInputMerchant {

    private Integer mercId;

    private String mercName;

    private String manageStartTime;

    private String manageEndTime;

}
