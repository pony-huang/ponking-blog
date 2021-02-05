package com.ponking.pblog.shiro.base;

import com.ponking.pblog.dao.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Ponking
 * @ClassName JwtRealm
 * @date 2020/3/14--22:22
 * @see JWTFilter
 **/
@Slf4j
public class JWTRealm extends AuthorizingRealm {


    private final TokenProvider tokenProvider;

    public JWTRealm(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }


    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得account，用于和数据库进行对比
        tokenProvider.validateToken(token);
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
