package com.xhnj.pojo.query;

import lombok.Getter;
import lombok.Setter;

/*
 @Description
 *@author kang.li
 *@date 2021/7/13 17:03   
 */
@Getter
@Setter
public class MenuParam {
    private Long parentId;
    private String keyword;
    private Integer level;
    private String name;
}
