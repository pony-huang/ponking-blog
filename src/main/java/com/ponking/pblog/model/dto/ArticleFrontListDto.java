package com.ponking.pblog.model.dto;

import com.ponking.pblog.model.entity.Category;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Ponking
 * @ClassName ArticleFrontListDto
 * @date 2020/4/7--22:11
 **/
@Data
public class ArticleFrontListDto {

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
     * 访问量
     */
    private Integer visits;

    /**
     * 分类
     */
    private Category category;

    /**
     * 该文章分类链接
     */
    private String categoryUrl;

    /**
     * 该文章详情链接
     */
    private String articleUrl;

}
