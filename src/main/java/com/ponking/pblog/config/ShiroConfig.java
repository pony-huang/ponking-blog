package com.ponking.pblog.config;

import com.ponking.pblog.common.cache.Cache;
import com.ponking.pblog.common.cache.MemoryCache;
import com.ponking.pblog.shiro.JwtFilter;
import com.ponking.pblog.shiro.JwtRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 * @author Ponking
 * @ClassName ShiroAuthConfig
 * @date 2020/3/14--22:19
 * @Des
 **/
@Configuration
public class ShiroConfig {


    @Bean
    public JwtRealm realm(@Qualifier("cache") Cache<String,Object> cache){
        return new JwtRealm(cache);
    }

    /**
     * 配置安全管理器
     * @return SecurityManager
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("realm")JwtRealm realm) {
        DefaultWebSecurityManager securityManager =
                new DefaultWebSecurityManager();
        // 注入JWTRealm
        securityManager.setRealm(realm);
        return securityManager;
    }

    /**
     * 配置权限过滤器
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager")SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean =
                new ShiroFilterFactoryBean();
        // 注入安全管理器
        factoryBean.setSecurityManager(securityManager);
        // 如果用户未登录,跳转到未认证接口
        factoryBean.setLoginUrl("/login");
        // 设置自定义过滤器
        // 因为过滤器优先配对，建议使用LinkedHashMap
        Map<String,Filter> filterMap = new LinkedHashMap<>();
        // jwtFilter 是过滤器的名称
        filterMap.put("jwtFilter",new JwtFilter());
        factoryBean.setFilters(filterMap);
        // 配置过滤器规则，同上述
        Map<String,String> chain = new LinkedHashMap<>();
        chain.put("/swagger-ui.html/**","anon");
        //验证码
        chain.put("/defaultKaptcha/**","anon");
        // 认证
        chain.put("/sys/**","noSessionCreation,jwtFilter");
        //测试专用
//        chain.put("/sys/**","anon");
        chain.put("/login","noSessionCreation");
        factoryBean.setFilterChainDefinitionMap(chain);
        return factoryBean;
    }

    /**
     * 启用Shiro注解
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager")SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        // 注入安全管理器
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 启用Shiro内部Bean生命周期管理
     * @return LifecycleBeanPostProcessor
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启aop对shiro的bean的动态代理
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        return creator;
    }
}
