package com.ponking.pblog.model.vo;

import com.ponking.pblog.model.entity.Category;
import lombok.Data;

import java.util.Date;

/**
 * @author Ponking
 * @ClassName ArticleTopColumnVO
 * @date 2020/4/8--13:08
 **/
@Data
public class ArticleTopColumnVO {
    private Integer id;
    private String title;
    private Date updateTime;
    private Category category;
    private String image;
}
