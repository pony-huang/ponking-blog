package com.ponking.pblog.model.document;

import com.alibaba.fastjson.annotation.JSONField;
import com.ponking.pblog.model.entity.Category;
import com.ponking.pblog.model.entity.Tag;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author peng
 * @date 2020/10/23--17:15
 * @Des
 **/
@Data
public class EsArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 文章的预览图片
     */
    private String image;

    /**
     * 文章内容
     */
    private String content;

    /**
     * Markdown格式的文章内容
     */
    private String contentMd;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 文章是否置顶  0：否  1：是
     */
    private Integer top;

    /**
     * 是否开启评论 0：关闭 1：开启
     */
    private Integer commented;

    /**
     * 是否为原创文章 0：转载 1：原创
     */
    private Integer original;

    /**
     * 原文链接，转载文章才需填写
     */
    private String sourceUrl;

    /**
     * 访问量
     */
    private Integer visits;

    /**
     * 状态 (0：草稿 1：已发布 2：回收站 )
     */
    private Integer status;


    private Category category;


    @JSONField(serialize = false)
    private List<Tag> tagArray;

    /**
     * acm;C#;...;...
     */
    private String tags;
}
