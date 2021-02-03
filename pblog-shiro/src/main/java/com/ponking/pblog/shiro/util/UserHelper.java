package com.ponking.pblog.shiro.util;

import com.ponking.pblog.common.constants.AuthConstants;
import com.ponking.pblog.common.util.RedisUtils;
import com.ponking.pblog.shiro.base.JwtToken;
import io.jsonwebtoken.lang.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;

/**
 * @author peng
 * @date 2020年12月8日
 * @Des
 **/
public class UserHelper {

    private UserHelper() {

    }

    public static boolean login(String token) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new JwtToken(token));
            return true;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void logout(String token) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
            boolean exists = RedisUtils.exists(AuthConstants.PREFIX_SHIRO_REFRESH_TOKEN + JwtUtils.getAccount(token));
            Assert.isTrue(exists, "");
            RedisUtils.del(AuthConstants.PREFIX_SHIRO_REFRESH_TOKEN + JwtUtils.getAccount(token));
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

}
