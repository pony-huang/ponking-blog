package com.ponking.pblog.common.cache;

import com.alibaba.fastjson.JSON;
import com.ponking.pblog.common.util.RedisUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author peng
 * @date 2020/9/28--0:40
 * @Des
 **/
public class RedisCache extends AbstractCache<String, String> {

    /**
     * pblog:cache:[key]
     */
    private static final String CACHE_NAME_PREFIX = "pblog:cache:";

    public RedisCache() {
        this(50, -1);
    }

    /**
     * 构造，默认对象不过期
     *
     * @param capacity 容量
     */
    private RedisCache(int capacity) {
        this(capacity, -1);
    }

    /**
     * 构造
     *
     * @param capacity 容量
     * @param timeout  过期时长
     */
    private RedisCache(int capacity, long timeout) {
        this.capacity = capacity;
        this.timeout = timeout;
    }

    @Override
    protected int pruneCache() {
        return 0;
    }

    @Override
    public void put(String key, String value, long timeout) {
        RedisUtil.set(key,value,timeout, TimeUnit.SECONDS);
    }

    @Override
    public void put(String key, String value) {
        RedisUtil.set(key,value,timeout, TimeUnit.SECONDS);
    }

    @Override
    public boolean containsKey(String key) {
        return RedisUtil.exists(key);
    }

    @Override
    public int getHitCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMissCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String get(String key) {
        return RedisUtil.getValue(key);
    }

    @Override
    public String get(String key, boolean isUpdateLastAccess) {
        return RedisUtil.getValue(key);
    }

    @Override
    public Iterator<String> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<CacheObj<String, String>> cacheObjIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int capacity() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long timeout() {
        return super.timeout();
    }

    @Override
    protected boolean isPruneExpiredActive() {
        return super.isPruneExpiredActive();
    }

    @Override
    public boolean isFull() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(String key) {
        super.remove(key);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void onRemove(String key, String cachedObject) {
        throw new UnsupportedOperationException();
    }
}
