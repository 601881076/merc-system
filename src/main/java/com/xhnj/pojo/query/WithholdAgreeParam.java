package com.xhnj.pojo.query;

import lombok.Data;

/*
 @Description 授权查询条件
 *@author kang.li
 *@date 2021/9/25 19:56   
 */
@Data
public class WithholdAgreeParam {
    private String successValue;
    private String failValue;
    private String unfinished;
    private String latest;
}
