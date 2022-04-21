package com.mercsystem.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName MD5Util
 * @Description md5加密工具类
 * @Auther tanyi
 * @Date 2022/4/19 10:05
 * @ProjectName dz-system
 * @Version 1.0
 */
@Component
public class MD5Util {

    @Value("${md5.key}")
    private static String md5Key;

    /**
     *
     * 使用springBoot自带的Md5加密
     * @param s 待加密字符串
     * @return
     */
    public String encryptMd5(String s) {
        s += md5Key;

        // 加密
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(s.getBytes(StandardCharsets.UTF_8));
        return md5DigestAsHex;
    }
}
