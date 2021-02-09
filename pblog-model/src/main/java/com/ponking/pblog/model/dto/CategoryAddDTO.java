package com.ponking.pblog.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Peng
 * @date 2020/9/2--15:17
 **/
@Data
public class CategoryAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("分类名")
    private String name;

    @ApiModelProperty("路径")
    private String path;

}
