package com.ponking.pblog.model.dto;

import com.ponking.pblog.model.entity.Article;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ponking
 * @ClassName ArticleListDto
 * @date 2020/3/20--17:49
 **/
@Data
public class ArticleListDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 总数
     */
    private Integer total;

    /**
     * 列表
     */
    private List<Article> items;

    public ArticleListDto(Integer total, List<Article> items) {
        this.total = total;
        this.items = items;
    }
}
