package com.ponking.pblog.model.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 前台搜索view
 *
 * @author peng
 * @date 2020/10/27--15:34
 * @Des
 **/
@Data
public class SearchQueryVO {


    private List<Description> pages = new ArrayList<>();

    private List<Description> posts = new ArrayList<>();

    private List<TagWithCate> tags = new ArrayList<>();

    private List<TagWithCate> categories = new ArrayList<>();


    @Data
    public static class Description {
        private String title;
        private String text;
        private String link;
    }

    @Data
    public static class TagWithCate {
        private String name;
        private String slug;
        private String link;
    }
}
