package com.ponking.pblog;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;

/**
 * @author ponking
 */
@Slf4j
@SpringBootApplication
@EnableScheduling
@MapperScan("com.ponking.pblog.dao")
public class Application implements CommandLineRunner {


//    @Autowired
//    private SchedulerManager manager;

    @Value("${server.port}")
    private String port;


    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("正在初始化PBlogProperties...");
        log.info("http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/swagger-ui.html");
        log.info("http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/doc.html");
        log.info("http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/home");
//        PBlogProperties config = new PBlogProperties();
//        List<BlogConfig> list = configService.list(null);
//        for (BlogConfig bc : list) {
//            String name = StringUtils.toLowerCamel(bc.getName());
//            try {
//                Field field = PBlogProperties.class.getDeclaredField(name);
//                field.setAccessible(true);
//                field.set(config, bc.getValue());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            String fileStorageJson = config.getFileStorage();
//            config.setAliyunOss(JSONObject.parseObject(fileStorageJson, AliyunOSS.class));
//            log.info("初始化PBlogProperties完成");
//        }
//        manager.pauseJob("elasticSearch","QuartzJobGroups");
//        manager.startJob("0/2 * * * * ?","elasticSearch","QuartzJobGroups", EsSearchJob.class);
    }
}
