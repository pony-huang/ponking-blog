package com.ponking.pblog.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Ponking
 * @ClassName LoginVo
 * @date 2020/3/15--10:28
 **/
@Data
public class LoginVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 密码
     */
    private String username;
    /**
     * 账号
     */
    private String password;
    /**
     * 验证码
     */
    private String code;
}
