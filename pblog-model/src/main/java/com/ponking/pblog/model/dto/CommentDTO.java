package com.ponking.pblog.model.dto;

import lombok.Data;

/**
 * @author peng
 * @date 2020/10/4--10:48
 * @Des
 **/
@Data
public class CommentDTO {

    /**
     * 父评论（若是0，即本评论为父评论）
     */
    private Long parentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论文章id
     */
    private Long articleId;

    /**
     * 评论者邮箱
     */
    private String email;

    /**
     * 评论者
     */
    private String author;

}
