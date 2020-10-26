package com.ponking.pblog.search.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.common.constants.EsConstant;
import com.ponking.pblog.model.document.EsArticle;
import com.ponking.pblog.model.entity.Article;
import com.ponking.pblog.model.entity.ArticleTag;
import com.ponking.pblog.model.entity.Category;
import com.ponking.pblog.model.entity.Tag;
import com.ponking.pblog.service.IArticleService;
import com.ponking.pblog.search.IEsArticleService;
import com.ponking.pblog.service.IArticleTagService;
import com.ponking.pblog.service.ICategoryService;
import com.ponking.pblog.service.ITagService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author peng
 * @date 2020/10/23--17:16
 * @Des
 **/
@Service
public class EsArticleServiceImpl implements IEsArticleService {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IArticleTagService articleTagService;

    @Autowired
    private ITagService tagService;

    @Override
    public int importAll() {
        BulkRequest request = new BulkRequest();
        articleService.listEsArticleAll().stream().forEach(item -> {
            IndexRequest indexRequest = new IndexRequest(EsConstant.ES_ARTICLE_INDEX);
            StringBuilder sb = new StringBuilder();
            for (Tag tag : item.getTagArray()) {
                sb.append(tag.getName()).append(";");
            }
            item.setTags(sb.toString());

            String json = JSON.toJSONString(item);
            indexRequest.id(item.getId() + "");
            indexRequest.source(json, XContentType.JSON);
            request.add(indexRequest);
        });
        BulkResponse responses;
        try {
            responses = client.bulk(request, RequestOptions.DEFAULT);
            return responses.getItems().length;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void createMappings() {
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
                    .startObject("commented").field("type", "integer").endObject()
                    .startObject("content").field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_max_word").endObject()
                    .startObject("contentMd").field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_max_word").endObject()
                    .startObject("image").field("type", "text").endObject()
                    .startObject("original").field("type", "integer").endObject()
                    .startObject("sourceUrl").field("type", "text").endObject()
                    .startObject("status").field("type", "integer").endObject()
                    .startObject("summary").field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_max_word").endObject()
                    .startObject("tags").field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_max_word").endObject()
                    .startObject("title").field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart").endObject()
                    .startObject("top").field("type", "integer").endObject()
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

    @Override
    public void delete(Long id) {
        DeleteRequest request = new DeleteRequest();
        request.index(EsConstant.ES_ARTICLE_INDEX).id(id + "");
        try {
            client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EsArticle create(Long id) {
        // todo 这里可以优化成一句sql语句 start
        Article article = articleService.getById(id);
        Category category = categoryService.getById(article.getCategoryId());
        QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", article.getId());
        List<Long> tagsId = articleTagService.list(wrapper).stream().map(ArticleTag::getTagId).collect(Collectors.toList());
        Collection<Tag> tags = tagService.listByIds(tagsId);
        EsArticle esArticle = new EsArticle();
        BeanUtils.copyProperties(article, esArticle);
        esArticle.setCategory(category);


        IndexRequest request = new IndexRequest(EsConstant.ES_ARTICLE_INDEX);
        StringBuilder sb = new StringBuilder();
        for (Tag tag : tags) {
            sb.append(tag.getName()).append(";");
        }
        esArticle.setTags(sb.toString());
        // todo 这里可以优化成一句sql语句 end


        String json = JSON.toJSONString(esArticle);
        request.id(esArticle.getId() + "");
        request.source(json, XContentType.JSON);
        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return esArticle;
    }

    @Override
    public void delete(List<Long> ids) {
        BulkRequest bulkRequest = new BulkRequest(EsConstant.ES_ARTICLE_INDEX);
        for (Long id : ids) {
            DeleteRequest request = new DeleteRequest();
            request.index(EsConstant.ES_ARTICLE_INDEX).id(id + "");
            bulkRequest.add(request);
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param keyword  文章标题
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<EsArticle> search(String keyword, Integer pageNum, Integer pageSize) {
        SearchRequest searchRequest = new SearchRequest(EsConstant.ES_ARTICLE_INDEX);
        SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
        // todo 默认title
        searchRequestBuilder.query(QueryBuilders.termQuery("title", keyword.toLowerCase()));
        searchRequestBuilder.from(pageNum);
        searchRequestBuilder.size(pageSize);
        searchRequest.source(searchRequestBuilder);
        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            List<EsArticle> articles = new ArrayList<>();
            for (SearchHit hit : hits) {
                Map<String, Object> source = hit.getSourceAsMap();
                // map 转 Obj
                EsArticle obj = (EsArticle) mapToObject(source, EsArticle.class);
                articles.add(obj);
            }
            Page<EsArticle> page = new Page<>();
            page.setSize(articles.size());
            page.setCurrent(pageNum);
            page.setRecords(articles);
            return page;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
                } else if (field.getType() == Category.class || field.getType() == List.class || field.getName().equals("contentMd")) {
                    // 排除不必要的信息
                    continue;
                } else {
                    field.set(obj, value);
                }
            }
        }
        return obj;
    }


    @Override
    public boolean isExitsIndex() {
        GetIndexRequest getIndexRequest = new GetIndexRequest(EsConstant.ES_ARTICLE_INDEX);
        boolean result = false;
        try {
            result = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
