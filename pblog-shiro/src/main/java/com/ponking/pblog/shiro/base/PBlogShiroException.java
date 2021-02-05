package com.ponking.pblog.shiro.base;

/**
 * @Author ponking
 * @Date 2021/2/5 11:02
 */
public class PBlogShiroException extends RuntimeException {

    public PBlogShiroException() {
    }

    public PBlogShiroException(String message) {
        super(message);
    }

    public PBlogShiroException(String message, Throwable cause) {
        super(message, cause);
    }

    public PBlogShiroException(Throwable cause) {
        super(cause);
    }

    public PBlogShiroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
