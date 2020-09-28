package com.ponking.pblog.model.result;

import lombok.Getter;

/**
 * <p>
 * 通用状态枚举
 * </p>
 * @author ponking
 */
@Getter
public enum ResultCode implements IResultCode {
    /**
     * 成功
     */
    OK(200, "请求成功"),
    /**
     * 失败
     */
    ERROR(500, "请求失败"),
    /**
     * 认证
     */
    Unauthorized(401,"认证错误"),

    /**
     * 授权未通过
     */
    Forbidden(403,"授权错误")
    ;


    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
