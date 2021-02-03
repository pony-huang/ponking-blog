package com.ponking.pblog.quartz.manager;

import com.ponking.pblog.quartz.listener.SchedulerListener;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author peng
 * @date 2020/10/20--21:46
 * @Des
 **/
@Component
public class SchedulerManager {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private JobListener scheduleListener;


    /**
     * 开启定时任务
     *
     * @param cron     cron表达式
     * @param jobName  任务名称
     * @param jobGroup 任务工作组
     * @param jobClass 该任务类
     * @throws SchedulerException
     */
    public void startJob(String cron, String jobName,
                         String jobGroup, Class<? extends Job> jobClass) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        if (scheduleListener == null) {
            scheduleListener = new SchedulerListener();
            try {
                scheduler.getListenerManager().addJobListener(scheduleListener);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (!scheduler.checkExists(jobKey)) {
            scheduleJob(cron, scheduler, jobName, jobGroup, jobClass);
        }
    }

    public void startJobWithContextValue(String cron, String jobName,
                                         String jobGroup, Class<? extends Job> jobClass,
                                         Map<String, Object> data) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        if (scheduleListener == null) {
            scheduleListener = new SchedulerListener();
            try {
                scheduler.getListenerManager().addJobListener(scheduleListener);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (!scheduler.checkExists(jobKey)) {
            scheduleJobWithContextValue(cron, scheduler, jobName, jobGroup, jobClass, data);
        }
    }

    /**
     * 移除定时任务
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public void deleteJob(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = new JobKey(jobName, jobGroup);
        scheduler.deleteJob(jobKey);
    }

    /**
     * 暂停定时任务
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = new JobKey(jobName, jobGroup);
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复定时任务
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public void resumeJob(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey triggerKey = new JobKey(jobName, jobGroup);
        scheduler.resumeJob(triggerKey);
    }

    /**
     * 清空所有当前scheduler对象下的定时任务【目前只有全局一个scheduler对象】
     *
     * @throws SchedulerException
     */
    public void clearAll() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.clear();
    }

    /**
     * 动态创建Job
     *
     * @param cron
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @param jobClass
     * @throws SchedulerException
     */
    private void scheduleJob(String cron, Scheduler scheduler,
                             String jobName, String jobGroup,
                             Class<? extends Job> jobClass)
            throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(jobClass).
                withIdentity(jobName, jobGroup)
                .build();

        CronScheduleBuilder csb = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger cronTrigger = TriggerBuilder.
                newTrigger().
                withIdentity(jobName, jobGroup).
                withSchedule(csb).
                build();

        scheduler.scheduleJob(jobDetail, cronTrigger);

    }

    /**
     * 动态创建Job
     *
     * @param cron
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @param jobClass
     * @throws SchedulerException
     */
    private void scheduleJobWithContextValue(String cron, Scheduler scheduler,
                                             String jobName, String jobGroup,
                                             Class<? extends Job> jobClass, Map<String, Object> data)
            throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(jobClass).
                withIdentity(jobName, jobGroup)
                .build();

        CronScheduleBuilder csb = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger cronTrigger = TriggerBuilder.
                newTrigger().
                withIdentity(jobName, jobGroup).
                withSchedule(csb).
                build();

        // 注入kv
        for (String key : data.keySet()) {
            scheduler.getContext().put(key, data.get(key));
        }


        scheduler.scheduleJob(jobDetail, cronTrigger);

    }
}
