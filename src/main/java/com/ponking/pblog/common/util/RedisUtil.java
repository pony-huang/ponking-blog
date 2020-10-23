package com.ponking.pblog.common.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
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
            FileInputStream in = new FileInputStream("src\\main\\resources\\redis.properties");
            Properties properties = new Properties();
            properties.load(in);
            String host = properties.getProperty("redis.host");
            String password = properties.getProperty("redis.password");
            String port = properties.getProperty("redis.port");
            /**
             * (final GenericObjectPoolConfig poolConfig, final String host, int port,
             *       int timeout, final String password)
             */
            JedisPool pool = new JedisPool(new GenericObjectPoolConfig(), host, Integer.valueOf(port), 3600, password);
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
        params.nx();
        return jedis.set(key, value, params);
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
     * 按keys删除
     *
     * @param keys
     * @return
     */
    public static Long del(String... keys) {
        return jedis.del(keys);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public static boolean exists(String key) {
        return jedis.exists(key);
    }


    public static void main(String[] args) {
        String set = RedisUtil.set("Hello", "World");
        System.out.println(set);
    }
}
