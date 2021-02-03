package com.ponking.pblog.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ponking.pblog.model.entity.Category;
import com.ponking.pblog.model.entity.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author peng
 * @since 2020-03-20
 */
@Data
public class ArticleDto implements Serializable {

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

    /**
     * 分类
     */
    private Category category;

    /**
     * 标签
     */
    private List<Tag> tags;

}
