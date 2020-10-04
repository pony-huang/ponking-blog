package com.ponking.pblog.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ponking
 * @ClassName JwtFilter
 * @date 2020/3/14--22:32
 **/
@Slf4j
public class JwtFilter extends AccessControlFilter {

    /**
     * 发起请求后响应的方法
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    /**
     * 自定义token 认证(权限检查)
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.info("on access denied...");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("text/html;charset=UTF-8");
        try{
            // 1. 检查请求头中是否存在token
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String token = request.getHeader("Authorization");
            // 2. 对该token进行验证
            JwtToken jwtToken = new JwtToken(token);
            // 3. 获取要主体认证
            Subject subject = SecurityUtils.getSubject();
            // 4. 发起对主体的认证
            subject.login(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        log.info("on access denied success...");
        return true;
    }
}
