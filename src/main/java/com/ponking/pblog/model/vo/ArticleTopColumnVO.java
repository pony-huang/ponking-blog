package com.ponking.pblog.model.vo;

import com.ponking.pblog.model.entity.Category;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ponking
 * @ClassName ArticleTopColumnVO
 * @date 2020/4/8--13:08
 **/
@Data
public class ArticleTopColumnVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String title;
    private Date updateTime;
    private Category category;
    private String image;
}
