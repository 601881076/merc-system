package com.xhnj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
@EnableTransactionManagement
@MapperScan({"com.xhnj.mapper"})
public class ScenicApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScenicApplication.class, args);
    }

}
