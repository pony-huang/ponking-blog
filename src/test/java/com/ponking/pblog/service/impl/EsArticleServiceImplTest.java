package com.ponking.pblog.service.impl;

import com.alibaba.fastjson.JSON;
import com.ponking.pblog.common.constants.EsConstant;
import com.ponking.pblog.model.document.EsArticle;
import com.ponking.pblog.model.entity.Category;
import com.ponking.pblog.search.IEsArticleService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * @author peng
 * @date 2020/10/23--17:39
 * @Des
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class EsArticleServiceImplTest {


    @Autowired
    private IEsArticleService esArticleService;

    @Autowired
    private RestHighLevelClient client;

    @Test
    public void createData() {
//        System.out.println(esArticleService.isExitsIndex());
        esArticleService.createMappings();
        esArticleService.importAll();
    }

    @Test
    public void testImportAll() {
        int i = esArticleService.importAll();
        System.out.println("success:" + i);
    }

    @Test
    public void testCreateMappings() {
        esArticleService.createMappings();
    }

    @Test
    public void testSearch() {
        SearchRequest searchRequest = new SearchRequest(EsConstant.ES_ARTICLE_INDEX);
        SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
        searchRequestBuilder.query(QueryBuilders.termQuery("title", "PAT".toLowerCase()));
        searchRequestBuilder.from(0);
        searchRequestBuilder.size(5);
        searchRequest.source(searchRequestBuilder);
        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                Map<String, Object> source = hit.getSourceAsMap();
                EsArticle object = (EsArticle) mapToObject(source, EsArticle.class);
                System.out.println(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object mapToObject(Map<String, Object> map, Class<?> clazz) throws IllegalAccessException, InstantiationException {
        Object obj = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            if (map.containsKey(field.getName())) {
                Object value = map.get(field.getName());
                if (field.getType() == Long.class) {
                    field.set(obj, Long.parseLong(value + ""));
                } else if (field.getType() == LocalDateTime.class) {
                    field.set(obj, LocalDateTime.parse(value.toString()));
                } else if (field.getType() == Category.class || field.getType() == List.class || field.getName() == "contentMd") {
                    continue;
                } else {
                    field.set(obj, value);
                }
            }
        }
        return obj;
    }

    @Test
    public void testIsExitsMappings() throws IOException {
        GetIndexRequest request = new GetIndexRequest(EsConstant.ES_ARTICLE_INDEX);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }


    @Test
    public void testPutMapping() throws IOException {
        PutMappingRequest request = new PutMappingRequest(EsConstant.ES_ARTICLE_INDEX);
        request.source(
                "{\n" +
                        "  \"properties\": {\n" +
                        "    \"message_ponking\": {\n" +
                        "      \"type\": \"text\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}",
                XContentType.JSON);

        AcknowledgedResponse acknowledgedResponse = client.indices().putMapping(request, RequestOptions.DEFAULT);
    }

    @Test
    public void testCreateIndex() {
        CreateIndexRequest request = new CreateIndexRequest(EsConstant.ES_ARTICLE_INDEX);
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject()
                    .startObject("properties")
                    .startObject("id").field("type", "long").endObject()
                    .startObject("category")
                    .field("type", "nested")
                    .startObject("properties")
                    .startObject("id").field("type", "long").endObject()
                    .startObject("path").field("type", "text").endObject()
                    .startObject("name").field("type", "text").endObject()
                    .endObject()
                    .endObject()
                    .startObject("categoryId").field("type", "long").endObject()
                    .startObject("commented").field("type", "long").endObject()
                    .startObject("content").field("type", "text").endObject()
                    .startObject("contentMd").field("type", "text").endObject()
                    .startObject("image").field("type", "text").endObject()
                    .startObject("original").field("type", "long").endObject()
                    .startObject("sourceUrl").field("type", "text").endObject()
                    .startObject("status").field("type", "long").endObject()
                    .startObject("summary").field("type", "text").endObject()
                    .startObject("tags").field("type", "text").endObject()
                    .startObject("title").field("type", "text").endObject()
                    .startObject("top").field("type", "long").endObject()
                    .startObject("createTime").field("type", "date").endObject()
                    .startObject("updateTime").field("type", "date").endObject()
                    .startObject("visits").field("type", "integer").endObject()
                    .endObject()
                    .endObject();
            request.mapping(builder);

            client.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testPutMappingIndex() {
        PutMappingRequest request = new PutMappingRequest(EsConstant.ES_ARTICLE_INDEX);
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject()
                    .startObject("properties")
                    .startObject("id").field("type", "long").endObject()
                    .startObject("category")
                    .field("type", "nested")
                    .startObject("properties")
                    .startObject("id").field("type", "long").endObject()
                    .startObject("path").field("type", "text").endObject()
                    .startObject("name").field("type", "text").endObject()
                    .endObject()
                    .endObject()
                    .startObject("categoryId").field("type", "long").endObject()
                    .startObject("commented").field("type", "long").endObject()
                    .startObject("content").field("type", "text").endObject()
                    .startObject("contentMd").field("type", "text").endObject()
                    .startObject("image").field("type", "text").endObject()
                    .startObject("original").field("type", "long").endObject()
                    .startObject("sourceUrl").field("type", "text").endObject()
                    .startObject("status").field("type", "long").endObject()
                    .startObject("summary").field("type", "text").endObject()
                    .startObject("tags").field("type", "text").endObject()
                    .startObject("title").field("type", "text").endObject()
                    .startObject("top").field("type", "long").endObject()
                    .startObject("createTime").field("type", "date").endObject()
                    .startObject("updateTime").field("type", "date").endObject()
                    .startObject("visits").field("type", "long").endObject()
                    .endObject()
                    .endObject();
            request.source(builder);

            client.indices().putMapping(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}