package com.ponking.pblog.config;

import com.ponking.pblog.common.cache.Cache;
import com.ponking.pblog.common.cache.MemoryCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author peng
 * @date 2020/10/20--13:30
 * @Des
 **/
@Order(-1)
@Configuration
public class CacheConfig {

    @Bean
    public Cache<String,Object> cache(){
        return new MemoryCache<String,Object>();
    }
}
