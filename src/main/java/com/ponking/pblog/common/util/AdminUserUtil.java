package com.ponking.pblog.common.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ponking.pblog.common.exception.GlobalException;
import com.ponking.pblog.model.entity.User;
import com.ponking.pblog.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author peng
 * @date 2020/11/13--11:26
 * @Des
 **/
@Component
public class AdminUserUtil {


    @Autowired
    private IUserService userService;

    /**
     * 获取当前用户
     * @return
     */
    public User getUser(){
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        String account = JwtUtil.getAccount(token);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", account);
        User one = userService.getOne(wrapper);
        if (one == null) {
            throw new GlobalException("该帐号不存在(The account does not exist.)");
        }
        return one;
    }

    /**
     * 获取当前登录用户Id
     *
     * @param
     * @return com.wang.model.UserDto
     */
    public Long getUserId() {
        return getUser().getId();
    }


    /**
     * 获取当前登录用户Token
     *
     * @param
     * @return java.lang.String;
     */
    public String getToken() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

    /**
     * 获取当前登录用户Account
     * @return java.lang.String;
     */
    public String getAccount() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        // 解密获得Account
        return JwtUtil.getAccount(token);
    }
}
