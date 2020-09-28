package com.ponking.pblog.common.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author peng
 * @date 2020/9/28--0:36
 * @Des
 **/
public class MemoryCache implements Cache {

    private final Map<Object,Object> data = new HashMap<>();

    @Override
    public void put(String key, Object object) {
        data.put(key,object);
    }

    @Override
    public Object get(String key) {
        return data.getOrDefault(key,null);
    }

    @Override
    public void remove(String key) {
        data.remove(key);
    }

    @Override
    public void clear() {
        data.clear();
    }
}
