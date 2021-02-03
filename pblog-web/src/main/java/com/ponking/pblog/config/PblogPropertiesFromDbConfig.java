package com.ponking.pblog.config;

import com.ponking.pblog.common.params.PBlogProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author peng
 * @date 2020/12/9--23:03
 * @Des
 **/
@Component
@Slf4j
public class PblogPropertiesFromDbConfig implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        RootBeanDefinition bean = new RootBeanDefinition(PBlogProperties.class);
        beanFactory.registerBeanDefinition("pBlogProperties", bean);
    }

}
