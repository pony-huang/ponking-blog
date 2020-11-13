package com.ponking.pblog.model.result;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 统一API对象返回
 * </p>
 *
 * @author ponking
 */
@Data
@NoArgsConstructor
public class R<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;


    /**
     * 返回数据
     */
    private T data;

    public R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public R(IResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public R(IResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = null;
    }

    public static <T> R success() {
        return new R<>(ResultCode.OK, true);
    }

    public static <T> R success(T data) {
        return new R<>(ResultCode.OK, data);
    }
    public static <T> R success(IResultCode resultCode, T data) {
        return new R<>(resultCode, data);
    }

    public static <T> R success(Integer code, String message, T data) {
        return new R<>(code, message, data);
    }

    public static <T> R failed() {
        return new R<>(ResultCode.ERROR, false);
    }

    public static <T> R failed(IResultCode resultCode) {
        return new R<>(resultCode, false);
    }

    public static <T> R failed(Integer code, String message) {
        return new R<>(code, message, null);
    }

    public static <T> R failed(IResultCode resultCode, T data) {
        return new R<>(resultCode, data);
    }

    public static <T> R failed(Integer code, String message, T data) {
        return new R<>(code, message, data);
    }

    public <T> R message(String message) {
        this.setMessage(message);
        return this;
    }

    public <T> R code(Integer code) {
        this.setCode(code);
        return this;
    }
}
