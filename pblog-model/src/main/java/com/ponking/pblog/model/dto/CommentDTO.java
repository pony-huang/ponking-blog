package com.ponking.pblog.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author peng
 * @date 2020/10/4--10:48
 * @Des
 **/
@Data
public class CommentDTO {


    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("评论的文章")
    private Long articleId;

    @ApiModelProperty("评论者")
    private String author;

    @ApiModelProperty("评论者的邮箱")
    private String email;

    @ApiModelProperty("邮箱MD5值，用于显示gravatar头像")
    private String emailMd5;

    @ApiModelProperty("评论者的浏览器")
    private String userAgent;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论者的ip地址")
    private String ip;

    @ApiModelProperty("引用的回复，0表示没有引用")
    private Long parentId;

    @ApiModelProperty("是否为博主评论  0：否  1：是")
    private Integer isAdmin;

    @ApiModelProperty("评论时间")
    private LocalDateTime createTime;

    @ApiModelProperty("评论状态 0：待审核 1：已发布 2：已删除")
    private Integer status;

}
