package com.ponking.pblog.job.listener;

import com.ponking.pblog.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author peng
 * @date 2020/9/18--20:11
 * @Des 监听器。
 **/
@Component
@Order(value = 1)
public class ScheduleJobInitListener implements CommandLineRunner {

    @Autowired
    private ITaskService scheduleJobService;

    @Override
    public void run(String... args) throws Exception {
        try{
            scheduleJobService.initSchedule();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
