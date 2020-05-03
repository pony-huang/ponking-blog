package com.ponking.pblog.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Ponking
 * @ClassName LoginVO
 * @date 2020/3/15--10:28
 **/
@Data
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
}
