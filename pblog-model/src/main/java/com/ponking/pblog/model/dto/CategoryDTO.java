package com.ponking.pblog.model.dto;

import lombok.Data;

/**
 * @author Peng
 * @date 2020/9/2--15:17
 **/
@Data
public class CategoryDTO {

    private Long id;

    /**
     * 分类名
     */
    private String name;

    /**
     * 路径
     */
    private String path;

}
