package com.ponking.pblog.model.dto;

import com.ponking.pblog.model.entity.Article;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ponking
 * @ClassName CategoryDto
 * @date 2020/4/24--21:22
 **/
@Data
public class CategoryDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 标签名
     */
    private String name;


    private List<Article> articles;
}
