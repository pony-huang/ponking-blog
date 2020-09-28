package com.ponking.pblog.config;

import com.ponking.pblog.job.factory.JobFactory;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * @author peng
 * @date 2020/9/18--20:05
 * @Des Quartz配置
 **/
@Configuration
public class QuartzConfig {

    @Autowired
    private JobFactory jobFactory;


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        schedulerFactoryBean.setJobFactory(jobFactory);
        return schedulerFactoryBean;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }

}
