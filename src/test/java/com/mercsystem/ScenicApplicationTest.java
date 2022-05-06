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
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzU3NDE1OTQwMCIsImNyZWF0ZWQiOjE2NTE4MjI4ODE0MjUsImV4cCI6MTY1NDQxNDg4MX0.d0FwqprYbmkWTK8YXMvdtpd-ydsJKFmrpkIBmfncow9VGJUaBrOVHOpCtaw4YtgzbGUifIKTlWavn6Ugc0MtHw";

        String requestId = "123456";

        token = token + requestId;


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
