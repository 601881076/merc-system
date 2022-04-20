package com.mercsystem.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * @ClassName DynamicSecurityService.java
 * @Description: 动态权限相关业务类
 * @ProjectName com.mercsystem.component
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:02
*/
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP
     */
    Map<String, ConfigAttribute> loadDataSource();
}
