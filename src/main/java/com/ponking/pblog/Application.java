package com.ponking.pblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ponking
 */
@SpringBootApplication
@MapperScan("com.ponking.pblog.mapper")
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

}
