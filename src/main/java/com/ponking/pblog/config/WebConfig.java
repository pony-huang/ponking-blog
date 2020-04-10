package com.ponking.pblog.config;

import org.apache.shiro.io.ResourceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Ponking
 * @ClassName WebConfig
 * @date 2020/4/4--20:43
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.commons.uploadWindows}")
    private String filePathWindow;

    @Value("${file.commons.uploadLinux}")
    private String filePathLinux;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
//        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_PREFIX+"/static/");
        if(os.toLowerCase().startsWith("win")){
            registry.addResourceHandler("/test/image/**").addResourceLocations("file:"+filePathWindow);
        }else{
            registry.addResourceHandler("/test/image/**").addResourceLocations("file:"+filePathLinux);
        }

    }


    /**
     * 跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
