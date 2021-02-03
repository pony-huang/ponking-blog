package com.ponking.pblog.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author peng
 * @since 2020-10-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("blog_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论的文章
     */
    private Long articleId;

    /**
     * 评论者
     */
    private String author;

    /**
     * 评论者的邮箱
     */
    private String email;

    /**
     * 邮箱MD5值，用于显示gravatar头像
     */
    private String emailMd5;

    /**
     * 评论者的浏览器
     */
    private String userAgent;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论者的ip地址
     */
    private String ip;

    /**
     * 引用的回复，0表示没有引用
     */
    private Long parentId;

    /**
     * 是否为博主评论  0：否  1：是
     */
    private Integer isAdmin;

    /**
     * 评论时间
     */
    private LocalDateTime createTime;

    /**
     * 评论状态 0：待审核 1：已发布 2：已删除
     */
    private Integer status;


}
