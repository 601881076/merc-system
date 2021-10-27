package com.xhnj.pojo.query;

import lombok.Data;

import java.util.Date;

/*
 @Description 授权查询条件
 *@author kang.li
 *@date 2021/9/25 19:56   
 */
@Data
public class WithholdAgreeParam {
    private Date startDate;

    private Date endDate;

    private String mobileNo;

    private String customerName;

    private String cardNo;

    private String certNo;

    private Integer status;
}
