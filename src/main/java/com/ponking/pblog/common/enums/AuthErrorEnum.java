package com.ponking.pblog.common.enums;

/**
 * @author peng
 * @date 2020/10/20--13:39
 * @Des
 **/
public enum AuthErrorEnum {

    CAPTCHA_ERROR(30000,"验证码错误"),
    ACCOUNT_NOT_EXIST(30001,"账号不存在"),
    PASSWORD_ERROR(30002,"密码错误"),
    NOT_AUTH(30002,"密码错误");

    private Integer code;
    private String message;

    AuthErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
