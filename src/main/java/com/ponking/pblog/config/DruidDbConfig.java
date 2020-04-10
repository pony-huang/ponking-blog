package com.ponking.pblog.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Ponking
 * @ClassName DruidDbConfig
 * @date 2020/3/13--22:40
 **/
@Configuration
public class DruidDbConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource dataSource(){
        return new DruidDataSource();
    }
}
