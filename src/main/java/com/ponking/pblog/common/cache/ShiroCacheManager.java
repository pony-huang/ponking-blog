package com.ponking.pblog.common.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * 自定义缓存管理器
 * @author peng
 * @date 2020/11/12--20:48
 * @Des
 **/
public class ShiroCacheManager implements CacheManager {

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new ShiroCache<>();
    }
}
