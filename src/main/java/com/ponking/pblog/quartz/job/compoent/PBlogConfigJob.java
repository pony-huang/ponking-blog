package com.ponking.pblog.quartz.job.compoent;

import com.google.common.base.CaseFormat;
import com.google.gson.Gson;
import com.ponking.pblog.model.params.PBlogProperties;
import com.ponking.pblog.mapper.ConfigMapper;
import com.ponking.pblog.model.entity.BlogConfig;
import com.ponking.pblog.model.params.AliyunOSS;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 更新博客配置信息任务
 *
 * @author peng
 * @date 2020/10/20--21:29
 * @Des 重新从db获取数据
 **/

@Log
@Component(value = "pBlogConfigJob")
public class PBlogConfigJob {

    @Autowired
    private PBlogProperties config;


    @Autowired
    private ConfigMapper configMapper;


    /**
     * 方法名在quartz定义
     */
    public void execute() {
        if (config == null){
            config = new PBlogProperties();
        }
        log.info(this.getClass().getName()+" start job ");
        List<BlogConfig> list = configMapper.selectList(null);
        PBlogProperties pBlogProperties = config;
        for (BlogConfig bc : list) {
            String name = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, bc.getName());
            try {
                Field field = PBlogProperties.class.getDeclaredField(name);
                field.setAccessible(true);
                field.set(pBlogProperties, bc.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String fileStorageJson = pBlogProperties.getFileStorage();
        Gson gson = new Gson();
        pBlogProperties.setAliyunOSS(gson.fromJson(fileStorageJson, AliyunOSS.class));
        log.info(this.getClass().getName()+" finished job ");
    }

}
