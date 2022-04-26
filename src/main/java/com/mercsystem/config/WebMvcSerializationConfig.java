package com.mercsystem.config;

import com.mercsystem.common.StringWithoutSpaceDeserializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @ClassName WebMvcSerializationConfig.java
 * @Description: 前端参数过器配置（空串和空格反序列化）
 * @ProjectName com.mercsystem.config
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:04
*/
@Configuration
@Slf4j
public class WebMvcSerializationConfig implements WebMvcConfigurer {

    // 请求url中的资源映射，如 /uploadFiles/**
    @Value("${uploadFile.resourceHandler}")
    private String resourceHandler;

    // 上传文件保存的本地目录，使用@Value获取全局配置文件中配置的属性值，如 E:/wmx/uploadFiles/
    @Value("${uploadFile.location}")
    private String location;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        //格式化前端日期为 yyyy-MM-dd HH:mm:ss 可选
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new StringWithoutSpaceDeserializer(String.class));
        mapper.registerModule(module);

        converter.setObjectMapper(mapper);
        return converter;
    }


    /**
     * 配置静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //就是说 url 中出现 resourceHandler 匹配时，则映射到 location 中去,location 相当于虚拟路径
        //映射本地文件时，开头必须是 file:/// 开头，表示协议
//        registry.addResourceHandler(resourceHandler).addResourceLocations(location);

        log.info("资源映射 = {}, 本地保存目录 = {}", resourceHandler, location);

        /** 本地文件上传路径 */
        registry.addResourceHandler(resourceHandler).addResourceLocations("file:" + location);

    }
}
