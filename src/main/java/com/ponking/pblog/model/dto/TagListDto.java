package com.ponking.pblog.model.dto;

import com.ponking.pblog.model.entity.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ponking
 * @ClassName TagListDto
 * @date 2020/3/16--22:02
 **/
@Data
public class TagListDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 总数
     */
    private Integer total;

    /**
     * 列表
     */
    private List<Tag> items;

    public TagListDto(Integer total, List<Tag> items) {
        this.total = total;
        this.items = items;
    }
}