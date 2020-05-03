package com.ponking.pblog.model.dto;

import com.ponking.pblog.model.entity.Category;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ponking
 * @ClassName CategoryListDto
 * @date 2020/3/20--16:46
 **/
@Data
public class CategoryListDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 总数
     */
    private Integer total;

    /**
     * 列表
     */
    private List<Category> items;

    public CategoryListDto(Integer total, List<Category> items) {
        this.total = total;
        this.items = items;
    }
}
