package com.ponking.pblog.common.cache;


import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * @author peng
 * @date 2020/9/28--0:36
 * @Des
 **/
public class MemoryCache<K,V> extends AbstractCache<K,V> {

    public MemoryCache() {
        this(50, 0);
    }

    /**
     * 构造，默认对象不过期
     *
     * @param capacity 容量
     */
    public MemoryCache(int capacity) {
        this(capacity, 0);
    }

    /**
     * 构造
     *
     * @param capacity 容量
     * @param timeout  过期时长
     */
    public MemoryCache(int capacity, long timeout) {
        this.capacity = capacity;
        this.timeout = timeout;
        cacheMap = new LinkedHashMap<>(Math.max(1 << 4, capacity >>> 7), 1.0f, false);
    }

    @Override
    protected int pruneCache() {
        int count = 0;
        Iterator<CacheObj<K, V>> values = cacheMap.values().iterator();
        CacheObj<K, V> co;
        while (values.hasNext()) {
            co = values.next();
            if (co.isExpired()) {
                values.remove();
                onRemove(co.key, co.obj);
                count++;
            }
        }
        return count;
    }
}
