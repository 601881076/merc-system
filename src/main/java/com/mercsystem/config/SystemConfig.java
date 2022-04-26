package com.mercsystem.config;

import com.mercsystem.util.ServletUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName SystemConfig
 * @Description 系统配置类
 * @Auther tanyi
 * @Date 2022/4/24 16:50
 * @ProjectName merc-admin
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "system")
public class SystemConfig {

    /** 上传路径 */
    private static String profile;

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        SystemConfig.profile = profile;
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }

    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     *
     * @return 服务地址
     */
    public String getUrl()
    {
        HttpServletRequest request = ServletUtils.getRequest();
        return getDomain(request);
    }

    public static String getDomain(HttpServletRequest request)
    {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }
}
