package com.ponking.pblog.shiro;

import lombok.Getter;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Ponking
 * @ClassName JwtToken
 * @date 2020/3/14--22:44
 **/
public class JwtToken implements AuthenticationToken {

    /**
     *  根据用户名和密码换取到的 token
     */
    private String jwtToken;


    public JwtToken(String token) {
        this.jwtToken = token;
    }

    @Override
    public Object getPrincipal() {
        return jwtToken;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }
}
