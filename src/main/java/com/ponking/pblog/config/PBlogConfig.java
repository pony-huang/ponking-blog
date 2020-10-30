package com.ponking.pblog.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;
import com.google.gson.Gson;
import com.ponking.pblog.common.util.StringUtil;
import com.ponking.pblog.model.params.PBlogProperties;
import com.ponking.pblog.mapper.ConfigMapper;
import com.ponking.pblog.model.entity.BlogConfig;
import com.ponking.pblog.model.params.AliyunOSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author peng
 * @date 2020/10/20--21:43
 * @Des
 **/
@Configuration
public class PBlogConfig {

    @Autowired
    private ConfigMapper configMapper;

    @Bean
    public PBlogProperties pBlogProperties(){
        List<BlogConfig> list = configMapper.selectList(null);
        PBlogProperties pBlogProperties = new PBlogProperties();
        for (BlogConfig bc : list) {
            String name = StringUtil.toLowerCamel(bc.getName());
            try {
                Field field = PBlogProperties.class.getDeclaredField(name);
                field.setAccessible(true);
                field.set(pBlogProperties, bc.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String fileStorageJson = pBlogProperties.getFileStorage();
        pBlogProperties.setAliyunOSS(JSONObject.parseObject(fileStorageJson,AliyunOSS.class));
        return pBlogProperties;
    }
}
