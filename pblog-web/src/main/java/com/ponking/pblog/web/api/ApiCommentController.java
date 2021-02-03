package com.ponking.pblog.web.api;


import com.ponking.pblog.common.result.R;
import com.ponking.pblog.model.dto.CommentDto;
import com.ponking.pblog.model.vo.ArticleCommentsVo;
import com.ponking.pblog.service.ICommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author peng
 * @since 2020-10-04
 */
@Api(tags = "评论功能模块")
@RestController
@RequestMapping("/comment")
public class ApiCommentController {


    @Autowired
    private ICommentService commentService;


    /**
     * 获取评论
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @ApiModelProperty("获取评论")
    public R getOneByArticleId(@PathVariable("id") Long id) {
        List<ArticleCommentsVo> comments = commentService.getCommentByArticleId(id);
        return R.success(comments);
    }


    /**
     * @param commentDto
     * @return
     */
    @PostMapping
    @ApiModelProperty("添加评论")
    public R commentArticle(@RequestBody CommentDto commentDto, HttpServletRequest request) {
        try {
            commentService.addArticleComment(commentDto, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.success();
    }
}
