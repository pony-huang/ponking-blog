package com.ponking.pblog.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ponking
 * @Date 2021/2/8 20:45
 */
@Data
public class CategoryQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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