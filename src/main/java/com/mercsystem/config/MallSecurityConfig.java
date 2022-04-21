package com.mercsystem.config;
import com.mercsystem.component.DynamicSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MallSecurityConfig.java
 * @Description: mall-security模块相关配置
 * @ProjectName com.mercsystem.config
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:03
*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MallSecurityConfig extends SecurityConfig {

//    @Autowired
//    private TAdminService adminService;
//
//    @Override
//    @Bean
//    public UserDetailsService userDetailsService() {
//        //获取后台登录用户信息
//        return username -> adminService.loadUserByUsername(username);
//    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                /*List<UmsResource> resourceList = resourceService.listAll();
                for (com.mercsystem.mymail.model.UmsResource resource : resourceList) {
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
                }*/
                return map;
            }
        };
    }
}
