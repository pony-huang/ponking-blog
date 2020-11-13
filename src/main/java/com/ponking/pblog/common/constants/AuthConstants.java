package com.ponking.pblog.common.constants;

/**
 * @author peng
 * @date 2020/9/28--0:45
 * @Des
 **/
public class AuthConstants {

    private AuthConstants(){

    }

    public static final String JWT_TOKEN_HEARER_NAME = "Authorization";

    public static final String JWT_TOKEN_CACHE_PREFIX = "pblog:user:";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public static final int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public static final int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public static final int EXRP_DAY = 60 * 60 * 24;

    /**
     * refreshTokenExpireTime过期时间 15
     */
    public static final int REFRESH_TOKEN_EXPIRE_TIME = 15 * 60;

    /**
     * redis-key-前缀-shiro:cache:
     */
    public static final String PREFIX_SHIRO_CACHE = "pblog:cache:";

    /**
     * redis-key-前缀-shiro:access_token:
     */
    public static final String PREFIX_SHIRO_ACCESS_TOKEN = "pblog:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public static final String PREFIX_SHIRO_REFRESH_TOKEN = "pblog:refresh_token:";

    /**
     * JWT-account:
     */
    public static final String ACCOUNT = "account";

    /**
     * JWT-currentTimeMillis:
     */
    public static final String CURRENT_TIME_MILLIS = "currentTimeMillis";
}
