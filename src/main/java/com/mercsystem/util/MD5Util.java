package com.mercsystem.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @ClassName MD5Util
 * @Description md5加密工具类
 * @Auther tanyi
 * @Date 2022/4/19 10:05
 * @ProjectName dz-system
 * @Version 1.0
 */
@Component
@Slf4j
public class MD5Util {

    @Value("${md5.key}")
    private String md5Key;

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

    public static byte[] md5(String s)
    {
        MessageDigest algorithm;
        try
        {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes("UTF-8"));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        }
        catch (Exception e)
        {
            log.error("MD5 Error...", e);
        }
        return null;
    }

    private static final String toHex(byte hash[])
    {
        if (hash == null)
        {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++)
        {
            if ((hash[i] & 0xff) < 0x10)
            {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(String s)
    {
        try
        {
            return new String(toHex(md5(s)).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        }
        catch (Exception e)
        {
            log.error("not supported charset...{}", e);
            return s;
        }
    }
}
