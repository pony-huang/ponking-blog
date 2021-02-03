package com.ponking.pblog.shiro.base;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author ponking
 * @Date 2021/2/3 11:21
 */
@Data
@ConfigurationProperties("demo.redis")
public class ShiroProperties {
}
