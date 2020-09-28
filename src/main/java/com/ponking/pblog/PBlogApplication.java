package com.ponking.pblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ponking.pblog.mapper")
public class PBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(PBlogApplication.class, args);
    }

}
