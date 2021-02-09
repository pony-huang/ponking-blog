package com.ponking.pblog.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ponking
 * @Date 2021/2/8 20:45
 */
@Data
public class CategoryQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("分类名")
    private String name;

    @ApiModelProperty("路径")
    private String path;

}