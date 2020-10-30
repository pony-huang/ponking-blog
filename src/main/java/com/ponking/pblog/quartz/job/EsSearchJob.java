package com.ponking.pblog.quartz.job;


import com.ponking.pblog.search.IEsArticleService;
import lombok.extern.java.Log;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


/**
 * @author peng
 * @date 2020/10/24--13:00
 * @Des
 **/
@Log
public class EsSearchJob implements Job {

    @Autowired
    private IEsArticleService service;

    @Value("${spring.profiles.active}")
    private String profile;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 为了方便测试，每次启动都删除在插入
        if ("prod".equals(profile) && service.isExitsIndex()) {
            log.info("delete index esArticle ...");
            service.deleteIndex();
        }

        if (!service.isExitsIndex()) {
            log.info("import data into es...");
            service.createMappings();
            service.importAll();
        }
    }
}
