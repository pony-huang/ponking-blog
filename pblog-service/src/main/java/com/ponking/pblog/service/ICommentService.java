package com.ponking.pblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ponking.pblog.model.dto.CommentDTO;
import com.ponking.pblog.model.entity.Comment;
import com.ponking.pblog.model.vo.ArticleCommentsVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author peng
 * @since 2020-10-04
 */
public interface ICommentService extends IService<Comment> {

    /**
     * 根据文章id查询该评论列表
     *
     * @param id
     * @return
     */
    List<ArticleCommentsVO> getCommentByArticleId(Long id);

    /**
     * 添加评论
     *
     * @param commentDto
     * @param request
     */
    void addArticleComment(CommentDTO commentDto, HttpServletRequest request);
}
