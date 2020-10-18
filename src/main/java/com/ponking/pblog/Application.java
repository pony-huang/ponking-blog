package com.ponking.pblog;

import com.google.common.base.CaseFormat;
import com.google.gson.Gson;
import com.ponking.pblog.config.PBlogConfig;
import com.ponking.pblog.mapper.ConfigMapper;
import com.ponking.pblog.model.entity.BlogConfig;
import com.ponking.pblog.model.params.AliyunOSS;
import com.ponking.pblog.service.ITaskService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.TemplateEngine;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author ponking
 */
@SpringBootApplication
@MapperScan("com.ponking.pblog.mapper")
public class Application {


    @Autowired
    private ConfigMapper configMapper;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PBlogConfig blogConfig() {
        List<BlogConfig> list = configMapper.selectList(null);
        PBlogConfig pBlogConfig = new PBlogConfig();
        for (BlogConfig bc : list) {
            String name = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, bc.getName());
            try {
                Field field = PBlogConfig.class.getDeclaredField(name);
                field.setAccessible(true);
                field.set(pBlogConfig, bc.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String fileStorageJson = pBlogConfig.getFileStorage();
        Gson gson = new Gson();
        pBlogConfig.setAliyunOSS(gson.fromJson(fileStorageJson, AliyunOSS.class));

        return pBlogConfig;
    }
}
