package com.ponking.pblog.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ponking.pblog.common.enums.JobStatusEnum;
import com.ponking.pblog.job.util.QuartzManager;
import com.ponking.pblog.mapper.TaskMapper;
import com.ponking.pblog.model.entity.Task;
import com.ponking.pblog.service.ITaskService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author peng
 * @since 2020-09-18
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {


    @Autowired
    private QuartzManager quartzManager;

    @Autowired
    private TaskMapper taskMapper;


    @Override
    public void initSchedule() throws SchedulerException {
        // 这里获取任务信息数据
        List<Task> jobList = taskMapper.selectList(null);
        for (Task task : jobList) {
            if (JobStatusEnum.RUNNING.getCode().equals(task.getJobStatus())) {
                quartzManager.addJob(task);
            }
        }
    }

    @Override
    public Task get(Long id) {
        return taskMapper.selectById(id);
    }


    @Override
    public void changeStatus(Long jobId, String jobStatus) throws SchedulerException {
        Task task = get(jobId);
        if (task == null) {
            return;
        }
        if (JobStatusEnum.STOP.getCode().equals(jobStatus)) {
            quartzManager.deleteJob(task);
            task.setJobStatus(JobStatusEnum.STOP.getCode());
        } else {
            task.setJobStatus(JobStatusEnum.RUNNING.getCode());
            quartzManager.addJob(task);
        }
//        update(task);
    }

    @Override
    public void updateCron(Long jobId) throws SchedulerException {
        Task task = get(jobId);
        if (task == null) {
            return;
        }
        if (JobStatusEnum.RUNNING.getCode().equals(task.getJobStatus())) {
            quartzManager.updateJobCron(task);
        }
//        update(task);
    }

    @Override
    public void run(Task task) throws SchedulerException {
        quartzManager.runJobNow(task);
    }

}


