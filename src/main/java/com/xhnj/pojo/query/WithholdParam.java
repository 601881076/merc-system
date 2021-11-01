package com.xhnj.pojo.query;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
 @Description
 *@author kang.li
 *@date 2021/9/20 16:19   
 */
@Getter
@Setter
public class WithholdParam {
    private Integer payResult;
    private Integer fromType;
    private List<String> batchNo;
}
