package com.ponking.pblog.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ponking
 * @Date 2021/2/8 16:13
 */
@Data
public class ArticleQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("分类名称")
    private String category;
}
