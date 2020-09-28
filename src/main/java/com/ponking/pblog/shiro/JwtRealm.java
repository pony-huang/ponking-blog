package com.ponking.pblog.shiro;

import com.ponking.pblog.common.cache.Cache;
import com.ponking.pblog.common.constants.AuthConstants;
import com.ponking.pblog.common.util.JwtUtil;
import com.ponking.pblog.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;

/**
 * @author Ponking
 * @ClassName JwtRealm
 * @date 2020/3/14--22:22
 **/
@Slf4j
public class JwtRealm extends AuthorizingRealm {

    private Cache cache;

    public JwtRealm(Cache cache) {
        this.cache = cache;
    }

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
        info.addRole("system");
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
        User user = new User();
        // 对 token 解析，对 token 合法性进行校验
        Claims claims = JwtUtil.parseJWT(token);
        String username = claims.getSubject();
        Object tokenOfCache = cache.get(AuthConstants.JWT_TOKEN_CACHE_PREFIX + username);
        if (!token.equals(tokenOfCache)) {
            throw new UnsupportedTokenException("token已注销...");
        }
        user.setUsername(claims.getSubject());
        log.info("认证成功...");
        return new SimpleAuthenticationInfo(user, token, getName());
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
}
