package com.ponking.pblog.controller.front;


import com.ponking.pblog.mapper.CommentMapper;
import com.ponking.pblog.model.dto.CommentDto;
import com.ponking.pblog.model.result.R;
import com.ponking.pblog.model.vo.ArticleCommentsVo;
import com.ponking.pblog.service.ICommentService;
import io.swagger.annotations.Api;
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
@Api(value = "评论前端控制器",tags = "评论前端控制器")
@RestController
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    private ICommentService commentService;


    /**
     * 获取评论
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public R getCommentByArticleId(@PathVariable("id")Long id){
        List<ArticleCommentsVo> comments =  commentService.getCommentByArticleId(id);
        return R.success(comments);
    }


    /**
     *
     * @param commentDto
     * @return
     */
    @PostMapping("")
    public R commentArticle(@RequestBody CommentDto commentDto, HttpServletRequest request){
        try {
            commentService.addArticleComment(commentDto,request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.success();
    }
}
