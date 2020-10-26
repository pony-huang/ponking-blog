package com.ponking.pblog.quartz.job;


import com.ponking.pblog.search.IEsArticleService;
import lombok.extern.java.Log;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author peng
 * @date 2020/10/24--13:00
 * @Des
 **/
@Log
public class EsSearchJob implements Job {

    @Autowired
    private IEsArticleService service;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (!service.isExitsIndex()) {
            log.info("import data into es...");
            service.createMappings();
            service.importAll();
        }
    }
}
