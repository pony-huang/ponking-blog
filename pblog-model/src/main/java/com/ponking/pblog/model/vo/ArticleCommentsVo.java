package com.ponking.pblog.model.vo;

import com.ponking.pblog.model.entity.Article;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peng
 * @date 2020/10/4--10:54
 * @Des 文章评论
 **/
@Data
public class ArticleCommentsVo {

    private Long id;

    /**
     * 父评论（若是0，即本评论为父评论）
     */
    private Long parentId;

    /**
     * 评论者
     */
    private String author;

    private String content;

    private List<ArticleCommentsVo> children = new ArrayList<>();
}
