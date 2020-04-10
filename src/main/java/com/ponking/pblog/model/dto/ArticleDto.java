package com.ponking.pblog.model.dto;

import com.ponking.pblog.model.entity.Article;
import lombok.Data;

import java.util.List;

/**
 * @author Ponking
 * @ClassName ArticleDto
 * @date 2020/3/20--17:49
 **/
@Data
public class ArticleDto {
    /**
     * 总数
     */
    private Integer total;

    /**
     * 列表
     */
    private List<Article> items;

    public ArticleDto(Integer total, List<Article> items) {
        this.total = total;
        this.items = items;
    }
}
