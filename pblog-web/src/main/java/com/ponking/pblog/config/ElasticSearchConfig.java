package com.ponking.pblog.config;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author peng
 * @date 2020/10/22--22:09
 * @Des
 **/
//@Component
//@ConfigurationProperties(prefix = "pblog.elasticsearch")
@Configuration
public class ElasticSearchConfig  {


    @Value("${pblog.elasticsearch.host}")
    private String host;

    @Value("${pblog.elasticsearch.port}")
    private int port;

    @Value("${pblog.elasticsearch.scheme}")
    private String scheme;

    @Bean
    public RestHighLevelClient restClient() {
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost(host, port, scheme));
        return new RestHighLevelClient(restClientBuilder);
    }
}
