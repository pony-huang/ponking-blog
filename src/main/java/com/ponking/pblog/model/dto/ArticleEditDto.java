package com.ponking.pblog.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ponking.pblog.model.vo.AuthorVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ponking
 * @ClassName ArticleEditDto
 * @date 2020/3/21--22:37
 **/
@Data
public class ArticleEditDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 作者
     */
    @JsonProperty("author")
    private AuthorVo authorVO;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章描述
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
     * 状态 (0：草稿 1：已发布 2：回收站 )
     */
    private Integer status;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 标签
     */
    private List<Long> tagIds;

}
