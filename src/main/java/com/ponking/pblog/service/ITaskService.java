package com.ponking.pblog.service;

import com.ponking.pblog.model.dto.TaskDO;
import com.ponking.pblog.model.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;
import org.quartz.SchedulerException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author peng
 * @since 2020-09-18
 */
public interface ITaskService extends IService<Task> {

    /**
     * 读取数据库，加载scheduler调度器。
     *
     * @throws SchedulerException
     */
    void initSchedule() throws SchedulerException;

    Task get(Long id);

    void changeStatus(Long jobId, String jobStatus) throws SchedulerException;

    void updateCron(Long jobId) throws SchedulerException;

    void run(Task scheduleJob) throws SchedulerException;
}
