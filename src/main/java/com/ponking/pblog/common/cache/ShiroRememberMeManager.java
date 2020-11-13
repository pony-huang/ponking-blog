package com.ponking.pblog.common.cache;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;

/**
 * @author peng
 * @date 2020/11/13--11:10
 * @Des
 **/
public class ShiroRememberMeManager implements RememberMeManager {

    @Override
    public PrincipalCollection getRememberedPrincipals(SubjectContext subjectContext) {
        return null;
    }

    @Override
    public void forgetIdentity(SubjectContext subjectContext) {

    }

    @Override
    public void onSuccessfulLogin(Subject subject, AuthenticationToken token, AuthenticationInfo info) {

    }

    @Override
    public void onFailedLogin(Subject subject, AuthenticationToken token, AuthenticationException ae) {

    }

    @Override
    public void onLogout(Subject subject) {

    }
}
