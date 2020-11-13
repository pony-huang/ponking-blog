package com.ponking.pblog.shiro;

import com.ponking.pblog.common.cache.Cache;
import com.ponking.pblog.common.constants.AuthConstants;
import com.ponking.pblog.common.util.JwtUtil;
import com.ponking.pblog.common.util.RedisUtil;
import com.ponking.pblog.model.entity.User;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


/**
 * @author Ponking
 * @ClassName JwtRealm
 * @see JwtFilter
 * @date 2020/3/14--22:22
 **/
@Slf4j
public class JwtRealm extends AuthorizingRealm {


    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 简单授权信息对象，对象中包含用户的角色和权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (Object principal : principals) {
            System.out.println(principal.toString());
        }
        info.addRole("system");
        info.addStringPermission("system");
        info.addStringPermission("");
        log.info("授权完成....");
        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 根据用户名和密码换取到的 token
        JwtToken jwtToken = (JwtToken) authenticationToken;

        String token = (String) jwtToken.getCredentials();

        String account = JwtUtil.getAccount(token);

        // 开始认证token合法性进行校验，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && RedisUtil.exists(AuthConstants.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = RedisUtil.getObject(AuthConstants.PREFIX_SHIRO_REFRESH_TOKEN + account).toString();
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, AuthConstants.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                log.info("认证成功(Authentication success)");
                return new SimpleAuthenticationInfo(token, token, "jwtRealm");
            }
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
    }

    /**
     * 配置该Realm只支持JWTToken
     *
     * @return
     */
    @Override
    public Class<?> getAuthenticationTokenClass() {
        return JwtToken.class;
    }

    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof JwtToken;
    }

}
