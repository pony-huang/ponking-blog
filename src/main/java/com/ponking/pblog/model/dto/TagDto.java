package com.ponking.pblog.model.dto;

import com.ponking.pblog.model.entity.Tag;
import lombok.Data;

import java.util.List;

/**
 * @author Ponking
 * @ClassName TagDto
 * @date 2020/3/16--22:02
 **/
@Data
public class TagDto {
    /**
     * 总数
     */
    private Integer total;

    /**
     * 列表
     */
    private List<Tag> items;

    public TagDto(Integer total, List<Tag> items) {
        this.total = total;
        this.items = items;
    }
}
