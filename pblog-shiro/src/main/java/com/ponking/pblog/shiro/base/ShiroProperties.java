package com.ponking.pblog.shiro.base;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ponking
 * @Date 2021/2/3 11:21
 */
@Data
@Slf4j
@ConfigurationProperties("pblog.shiro")
@Configuration
public class ShiroProperties {

    @PostConstruct
    public void init() {
        log.info(this.getClass().getName() + " init...");
    }


    private List<String> anonUrls = new ArrayList();

    private List<String> authUrls = new ArrayList();
}
