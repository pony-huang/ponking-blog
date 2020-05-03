package com.ponking.pblog.model.vo;

import com.ponking.pblog.model.entity.Category;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author Ponking
 * @ClassName ArchiveVO
 * @date 2020/4/10--0:49
 **/
@Data
public class ArchiveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    private Integer id;
    /**
     * 更新时间
     */
    private Date date;
    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章分类
     */
    private Category category;
}
