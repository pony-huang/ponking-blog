package com.ponking.pblog.web.front;

import com.alibaba.fastjson.JSON;
import com.ponking.pblog.model.document.EsArticle;
import com.ponking.pblog.model.vo.SearchQueryVo;
import com.ponking.pblog.search.IEsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author peng
 * @date 2020/10/25--23:19
 * @Des
 **/
//@Controller
public class SearchController {

    @Autowired
    private IEsArticleService esArticleService;

    @Value("${server.port}")
    private String port;

    @Value("${pblog.web.domain}")
    private String host;

    @GetMapping("/websocket/index")
    public String index() {
        return "blog/websocket";
    }

    @GetMapping("/content.json")
    @ResponseBody
    public String contentJson() {
        // todo 暂时模拟部分数据，之后结合websocket
        List<EsArticle> articles = esArticleService.queryMatchAll();
        List<SearchQueryVo.TagWithCate> categories = new ArrayList<>();
        List<SearchQueryVo.TagWithCate> tags = new ArrayList<>();
        SearchQueryVo result = new SearchQueryVo();
        List<SearchQueryVo.Description> posts = articles.stream().map(item -> {
            SearchQueryVo.Description desc = new SearchQueryVo.Description();
            SearchQueryVo.TagWithCate cate = new SearchQueryVo.TagWithCate();
            try {
                if(host == null || "".equals(host)){
                    InetAddress addr = InetAddress.getLocalHost();
                    host = addr.getHostAddress();
                }
                // posts
                desc.setLink("http://" + host + ":" + port + "/articles/" + item.getId());
                desc.setTitle(item.getTitle());
                desc.setText(item.getSummary());

                // cate
                cate.setSlug(item.getTitle());
                cate.setName(item.getCategory().getName());
                cate.setLink("http://" + host + ":" + port + "/articles/" + item.getId());
                categories.add(cate);

                // tag "ACM;C++;C#;"
                String[] split = item.getTags().split(";");
                for (int i = 0; i < split.length; i++) {
                    if (!"".equals(split[i])) {
                        SearchQueryVo.TagWithCate tag = new SearchQueryVo.TagWithCate();
                        tag.setSlug(item.getTitle());
                        tag.setName(split[i]);
                        tag.setLink("http://" + host + ":" + port + "/articles/" + item.getId());
                        tags.add(tag);
                    }
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return desc;
        }).collect(Collectors.toList());

        result.setPosts(posts);
        result.setTags(tags);
        result.setCategories(categories);
        return JSON.toJSONString(result);
    }


}
