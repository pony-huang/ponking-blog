package com.ponking.pblog;

import com.ponking.pblog.quartz.job.compoent.EsSearchJob;
import com.ponking.pblog.quartz.manager.SchedulerManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ponking
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.ponking.pblog.mapper")
public class Application implements CommandLineRunner {


    @Autowired
    private SchedulerManager manager;


    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        manager.startJob("0/2 * * * * ?","elasticSearch","QuartzJobGroups", EsSearchJob.class);
    }
}
