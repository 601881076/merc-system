package com.mercsystem;

import com.mercsystem.pojo.bo.AdminUserDetails;
import com.mercsystem.util.JwtTokenUtil;
import com.mercsystem.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
public class ScenicApplicationTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MD5Util md5Util;

    @Test
    public void md5Encrypt() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzU3NDE1OTQwMCIsImNyZWF0ZWQiOjE2NTA5Njc3ODc3ODksImV4cCI6MTY1MzU1OTc4N30.ZV3u7JOSVmBiThuQy13RNCsugi7rsKRIebKtYSV-89rPmYF9hwU6PQ8mMW1UaBuGQ6tzLpx2I0CCYKF0IXZsEA";
        token += "123456";
        System.out.println(md5Util.encryptMd5(token));
    }

//    @Test
//    public void getToken() {
//        TAdmin admin = new TAdmin();
//        admin.setUsername("13574159400");
//        UserDetails userDetails = new AdminUserDetails(admin);
//        String token = jwtTokenUtil.generateToken(userDetails);
//
//        System.out.println(token);
//    }

    @Test
    public void getPassword() {
        System.out.println(passwordEncoder.encode("123456"));
    }
}
