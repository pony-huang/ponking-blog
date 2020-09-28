package com.ponking.pblog.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author peng
 * @date 2020/9/18--20:15
 * @Des
 **/
@Data
public class TaskDO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    // 任务名
    private String jobName;
    // 任务描述
    private String description;
    // cron表达式
    private String cronExpression;
    // 任务执行时调用哪个类的方法 包名+类名
    private String beanClass;
    // 任务状态
    private String jobStatus;
    // 任务分组
    private String jobGroup;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;
}