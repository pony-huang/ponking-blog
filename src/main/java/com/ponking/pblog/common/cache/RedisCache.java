package com.ponking.pblog.common.cache;

import com.alibaba.fastjson.JSON;
import com.ponking.pblog.common.util.RedisUtil;

/**
 * @author peng
 * @date 2020/9/28--0:40
 * @Des
 **/
public class RedisCache implements Cache {

    @Override
    public void put(String key, Object object) {
        RedisUtil.set(key, JSON.toJSONString(object));
    }

    @Override
    public Object get(String key) {
        return RedisUtil.getValue(key);
    }

    @Override
    public void remove(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
