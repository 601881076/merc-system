package com.mercsystem;

import com.mercsystem.model.TAdmin;
import com.mercsystem.pojo.bo.AdminUserDetails;
import com.mercsystem.util.JwtTokenUtil;
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

    @Test
    public void getToken() {
        TAdmin admin = new TAdmin();
        admin.setUsername("13574159400");
        UserDetails userDetails = new AdminUserDetails(admin);
        String token = jwtTokenUtil.generateToken(userDetails);

        System.out.println(token);
    }

    @Test
    public void getPassword() {
        System.out.println(passwordEncoder.encode("123456"));
    }
}
