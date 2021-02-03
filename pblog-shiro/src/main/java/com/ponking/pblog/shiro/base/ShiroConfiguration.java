package com.ponking.pblog.shiro.base;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 *
 * @author Ponking
 * @ClassName ShiroAuthConfig
 * @date 2020/3/14--22:19
 * @Des
 **/
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroConfiguration {

    /**
     * 配置安全管理器
     *
     * @return SecurityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager =
                new DefaultWebSecurityManager();
        // 设置自定义JRealm
        securityManager.setRealm(new JwtRealm());
        // 设置自定义Cache缓存
//        securityManager.setCacheManager(new ShiroCacheManager());
        // 关闭Shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 配置权限过滤器
     *
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean =
                new ShiroFilterFactoryBean();
        // 注入安全管理器
        factoryBean.setSecurityManager(securityManager);
        // 如果用户未登录,跳转到未认证接口
        factoryBean.setLoginUrl("/login");
        // 设置自定义过滤器
        // 因为过滤器优先配对，建议使用LinkedHashMap
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        // jwtFilter 是过滤器的名称
        filterMap.put("jwtFilter", new JwtFilter());
        factoryBean.setFilters(filterMap);
        // 配置过滤器规则，同上述
        Map<String, String> chain = new LinkedHashMap<>();
        chain.put("/swagger-ui.html/**", "anon");
        //验证码
        chain.put("/defaultKaptcha/**", "anon");
        chain.put("/captcha/**", "anon");
        // 登录
        chain.put("/login", "anon");
        // 认证,其余拦截
        chain.put("/sys/**", "jwtFilter");
        factoryBean.setFilterChainDefinitionMap(chain);
        return factoryBean;
    }


    /**
     * 下面的代码是添加注解支持
     */

    /**
     * 启用Shiro注解
     *
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        // 注入安全管理器
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 启用Shiro内部Bean生命周期管理
     *
     * @return LifecycleBeanPostProcessor
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启aop对shiro的bean的动态代理
     *
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题，https://zhuanlan.zhihu.com/p/29161098
        creator.setProxyTargetClass(true);
        return creator;
    }
}
