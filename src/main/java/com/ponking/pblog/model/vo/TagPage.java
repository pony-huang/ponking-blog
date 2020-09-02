package com.ponking.pblog.model.vo;

import com.ponking.pblog.model.entity.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ponking
 * @ClassName TagPage
 * @date 2020/3/16--22:02
 **/
@Data
public class TagPage implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 总数
     */
    private Integer total;

    /**
     * 列表
     */
    private List<Tag> items;

    public TagPage(Integer total, List<Tag> items) {
        this.total = total;
        this.items = items;
    }
}
