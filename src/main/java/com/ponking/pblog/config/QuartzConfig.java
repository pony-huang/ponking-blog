package com.ponking.pblog.config;

import com.ponking.pblog.quartz.factory.InvokingJobDetailFactory;
import com.ponking.pblog.quartz.factory.JobFactory;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author peng
 * @date 2020/10/20--15:03
 * @Des
 **/
@Configuration
public class QuartzConfig {

    /**
     * 1.通过name+group获取唯一的jobKey;2.通过groupname来获取其下的所有jobkey
     */
    final static String GROUP_NAME = "QuartzJobGroups";


    @Autowired
    private JobFactory jobFactory;

    /**
     * 设置属性
     *
     * @return
     * @throws IOException
     */
    private Properties quartzProperties() throws IOException {
        InputStream is = QuartzConfig.class.getClassLoader().getResourceAsStream("quartz.properties");
        Properties properties = new Properties();
        properties.load(is);
        return properties;
    }


    /**
     * 调度工厂
     * 此处配置需要调度的触发器 例如 executeJobTrigger
     *
     * @param executeJobTrigger
     * @return
     * @throws IOException
     * @throws PropertyVetoException
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("executeJobTrigger") Trigger executeJobTrigger,
                                                     @Qualifier("dataSource") DataSource dataSource)
            throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        //factory.setStartupDelay(10);//应用启动完10秒后 QuartzScheduler 再启动
        //用于quartz集群,加载quartz数据源配置
        factory.setAutoStartup(true);
        factory.setQuartzProperties(quartzProperties());
        factory.setJobFactory(jobFactory);
        factory.setApplicationContextSchedulerContextKey("applicationContext");
        //用于quartz集群,加载quartz数据源
        factory.setDataSource(dataSource);
        //注册触发器
        Trigger[] triggers = {executeJobTrigger};
        factory.setTriggers(triggers);

        return factory;
    }


    /**
     * 加载触发器
     * <p>
     * 新建触发器进行job 的调度  例如 executeJobDetail
     *
     * @param jobDetail
     * @return
     */
    @Bean(name = "executeJobTrigger")
    public CronTriggerFactoryBean executeJobTrigger(@Qualifier("executeJobDetail") JobDetail jobDetail) {
        //每天凌晨3点执行
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression("0/2 * * * * ?");
        return factoryBean;
    }


    /**
     * 加载job
     * 新建job 类用来代理
     *
     * @return
     */
    @Bean
    public JobDetailFactoryBean executeJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(InvokingJobDetailFactory.class);
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);
        factoryBean.setGroup(GROUP_NAME);
        Map<String, String> map = new HashMap<>();
        map.put("targetMethod", "execute");
        map.put("targetObject", "pBlogConfigJob");
        factoryBean.setJobDataAsMap(map);
        return factoryBean;
    }

}
