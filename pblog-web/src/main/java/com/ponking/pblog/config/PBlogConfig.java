package com.ponking.pblog.config;

/**
 * @author peng
 * @date 2020/10/20--21:43
 * @Des
 **/
//@Configuration
public class PBlogConfig {

//    @Autowired
//    private ConfigMapper configMapper;
//
//    @Bean
//    public PBlogProperties pBlogProperties(){
//        List<BlogConfig> list = configMapper.selectList(null);
//        PBlogProperties pBlogProperties = new PBlogProperties();
//        for (BlogConfig bc : list) {
//            String name = StringUtils.toLowerCamel(bc.getName());
//            try {
//                Field field = PBlogProperties.class.getDeclaredField(name);
//                field.setAccessible(true);
//                field.set(pBlogProperties, bc.getValue());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        String fileStorageJson = pBlogProperties.getFileStorage();
//        pBlogProperties.setAliyunOss(JSONObject.parseObject(fileStorageJson,AliyunOSS.class));
//        return pBlogProperties;
//    }
}
