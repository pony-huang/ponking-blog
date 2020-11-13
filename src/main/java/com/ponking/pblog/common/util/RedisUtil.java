package com.ponking.pblog.common.util;

import com.aliyun.oss.common.utils.StringUtils;
import com.ponking.pblog.common.exception.GlobalException;
import com.ponking.pblog.config.QuartzConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author peng
 * @date 2020/9/3--10:57
 * @Des
 **/
public class RedisUtil {

    private static Jedis jedis;

    static {
        try {
            InputStream in = RedisUtil.class.getClassLoader().getResourceAsStream("redis.properties");
            Properties properties = new Properties();
            properties.load(in);
            String host = properties.getProperty("redis.host");
            String password = properties.getProperty("redis.password");
            int port = Integer.parseInt(properties.getProperty("redis.port"));
            JedisPool pool = new JedisPool(new JedisPoolConfig(), host, port, 3600, password);
            jedis = pool.getResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置key的值为value
     *
     * @param key
     * @param value
     * @param time  时间
     * @param unit  仅仅支持MILLISECONDS，SECONDS
     * @return
     */
    public static String set(String key, String value, long time, TimeUnit unit) {
        SetParams params = SetParams.setParams();
        if (unit == TimeUnit.SECONDS) {
            params.ex((int) time);
        } else {
            params.px(time);
        }
        return jedis.set(key, value, params);
    }

    /**
     * 根据key的值获取值
     *
     * @param key
     * @return java.lang.String
     */
    public static String get(String key) {
        return jedis.get(key);
    }

    /**
     * 根据key的值获取Object
     *
     * @param key
     * @return java.lang.Object
     */
    public static Object getObject(String key) {
        try {
            byte[] bytes = jedis.get(key.getBytes());
            if (!StringUtils.isNullOrEmpty(new String(bytes))) {
                return SerializableUtil.unserializable(bytes);
            }
        } catch (Exception e) {
            throw new GlobalException("获取Redis键值getObject方法异常:key=" + key + " cause=" + e.getMessage());
        }
        return null;
    }

    /**
     * 设置key的值为value
     *
     * @param key
     * @param value
     * @param time  时间
     * @param unit  仅仅支持MILLISECONDS，SECONDS
     * @return
     */
    public static Object setObject(String key, Object value, long time, TimeUnit unit) {
        SetParams params = SetParams.setParams();
        if (unit == TimeUnit.SECONDS) {
            params.ex((int) time);
        } else {
            params.px(time);
        }
        return jedis.set(key.getBytes(), SerializableUtil.serializable(value), params);
    }


    /**
     * 设置key的值为value
     *
     * @param key
     * @param value
     * @return
     */
    public static String set(String key, String value) {
        return jedis.set(key, value);
    }

    /**
     * 获取key的值
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        return jedis.get(key);
    }

    /**
     * 获取key的值
     *
     * @param key
     * @return
     */
    public static Set<byte[]> getKeys(String key) {
        Set<byte[]> keys = jedis.keys("*".getBytes());
        return keys;
    }

    /**
     * 按keys删除
     *
     * @param keys
     * @return
     */
    public static Long del(String... keys) {
        return jedis.del(keys);
    }

    /**
     * 清空所有缓存
     */
    public static void flushDB() {
        if (jedis != null) {
            jedis.flushDB();
        }
    }

    public static boolean exists(String key) {
        return jedis.exists(key);
    }

    public static Long dbSize() {
        return jedis.dbSize();
    }


    /**
     * 自增
     *
     * @param key
     */
    public static void increment(String key) {
        jedis.incr(key);
    }

    public static void decrement(String key) {
        jedis.decr(key);
    }

    /******************************************** 列表操作 ********************************************/

}
