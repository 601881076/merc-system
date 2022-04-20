package com.mercsystem.pojo.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName MenuParam.java
 * @Description: 类描述
 * @ProjectName com.mercsystem.pojo.query
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:07
*/
@Getter
@Setter
public class MenuParam {
    private Long parentId;
    private String keyword;
    private Integer level;
    private String name;
}
