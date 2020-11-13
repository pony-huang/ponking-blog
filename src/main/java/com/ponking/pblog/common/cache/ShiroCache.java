package com.ponking.pblog.common.cache;

import com.ponking.pblog.common.constants.AuthConstants;
import com.ponking.pblog.common.util.JwtUtil;
import com.ponking.pblog.common.util.RedisUtil;
import com.ponking.pblog.common.util.SerializableUtil;
import org.apache.shiro.cache.CacheException;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author peng
 * @date 2020/11/12--20:33
 * @Des
 **/
public class ShiroCache<K,V> implements org.apache.shiro.cache.Cache<K,V> {
    /**
     * 缓存的key名称获取为shiro:cache:[account]
     * @param key
     * @return java.lang.String
     */
    private String getKey(Object key) {
        return AuthConstants.PREFIX_SHIRO_CACHE + JwtUtil.getClaim(key.toString(), AuthConstants.ACCOUNT);
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        if(Boolean.FALSE.equals(RedisUtil.exists(this.getKey(key)))){
            return null;
        }
        return RedisUtil.getObject(this.getKey(key));
    }

    /**
     * 保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        // 设置Redis的Shiro缓存
        return RedisUtil.setObject(this.getKey(key), value, AuthConstants.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if(Boolean.FALSE.equals(RedisUtil.exists(this.getKey(key)))){
            return null;
        }
        RedisUtil.del(this.getKey(key));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        RedisUtil.flushDB();
    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
        return Math.toIntExact(RedisUtil.dbSize());
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set keys() {
        Set<byte[]> keys = RedisUtil.getKeys("*");
        Set<Object> set = new HashSet<Object>();
        for (byte[] bs : keys) {
            set.add(SerializableUtil.unserializable(bs));
        }
        return set;
    }

    /**
     * 获取所有的value
     */
    @Override
    public Collection values() {
        Set keys = this.keys();
        List<Object> values = new ArrayList<Object>();
        for (Object key : keys) {
            values.add(RedisUtil.getObject(this.getKey(key)));
        }
        return values;
    }
}
