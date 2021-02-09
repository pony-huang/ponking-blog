package com.ponking.pblog.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ponking.pblog.model.entity.Category;
import com.ponking.pblog.model.entity.Tag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class ArticleAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("文章id")
    private Long id;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章摘要")
    private String summary;

    @ApiModelProperty("文章的预览图片")
    private String image;

    @ApiModelProperty("文章内容")
    private String content;

    @ApiModelProperty("Markdown格式的文章内容")
    private String contentMd;

    @ApiModelProperty("分类ID")
    private Long categoryId;

    @ApiModelProperty("发表时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("文章是否置顶  0：否  1：是")
    private Integer top;

    @ApiModelProperty("是否开启评论 0：关闭 1：开启")
    private Integer commented;

    @ApiModelProperty("是否为原创文章 0：转载 1：原创")
    private Integer original;

    @ApiModelProperty("原文链接，转载文章才需填写")
    private String sourceUrl;

    @ApiModelProperty("访问量")
    private Integer visits;

    @ApiModelProperty("状态 (0：草稿 1：已发布 2：回收站 )")
    private Integer status;

    @ApiModelProperty("分类")
    private Category category;

    @ApiModelProperty("标签")
    private List<Tag> tags;

}
