package com.ponking.pblog.quartz.listener;

/**
 * @author peng
 * @date 2020/10/20--21:35
 * @Des
 **/
import lombok.extern.java.Log;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Log
public class SchedulerListener implements JobListener {

    public static final String LISTENER_NAME = "QuartSchedulerListener";

    @Override
    public String getName() {
        return LISTENER_NAME;
    }

    /**
     * 任务被调度前
     * @param context
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {

        String jobName = context.getJobDetail().getKey().toString();
        log.info("Job : " + jobName + " is going to start...");

    }

    /**
     * 任务调度被拒了
     * @param context
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        //可以做一些日志记录原因
    }

    /**
     * 任务被调度后
     * @param context
     * @param jobException
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context,
                               JobExecutionException jobException) {

        String jobName = context.getJobDetail().getKey().toString();
        log.info("Job : " + jobName + " is finished...");

        if (jobException!=null&&!jobException.getMessage().equals("")) {
            log.info("Exception thrown by: " + jobName
                    + " Exception: " + jobException.getMessage());
        }

    }
}
