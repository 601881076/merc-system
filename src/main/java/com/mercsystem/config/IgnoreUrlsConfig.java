package com.mercsystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName IgnoreUrlsConfig.java
 * @Description: 用于配置不需要保护的资源路径
 * @ProjectName com.mercsystem.config
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:03
*/
@Getter
@Setter
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();

}
