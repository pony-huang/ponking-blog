package com.ponking.pblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.ponking.pblog.mapper")
@EnableCaching
public class PBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(PBlogApplication.class, args);
    }

}
