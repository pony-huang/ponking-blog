package com.ponking.pblog.common.cache;

/**
 * 自定义缓存
 * @author peng
 * @date 2020/9/28--0:33
 * @Des
 **/
public interface Cache {

    /**
     * 写入缓存
     * @param key
     * @param object
     */
    void put(String key,Object object);

    /**
     * 读取缓存
     * @param key
     */
    Object get(String key);

    /**
     * 移除缓存
     * @param key
     */
    void remove(String key);

    /**
     * 清除缓存
     */
    void clear();
}
