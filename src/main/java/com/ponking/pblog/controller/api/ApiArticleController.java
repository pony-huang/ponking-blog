package com.ponking.pblog.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.common.util.JwtUtil;
import com.ponking.pblog.model.R;
import com.ponking.pblog.model.dto.ArticleListDto;
import com.ponking.pblog.model.dto.ArticleEditDto;
import com.ponking.pblog.model.entity.Article;
import com.ponking.pblog.model.vo.AuthorVO;
import com.ponking.pblog.service.IArticleService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author peng
 * @since 2020-03-20
 */
@RestController
@RequestMapping("/sys/articles")
public class ApiArticleController {

    @Autowired
    private IArticleService articleService;

    /**
     * 博客列表数据
     * @param page 当前页
     * @param limit 每页size
     * @param title 标题查询条件
     * @return
     */
    @GetMapping("")
    public R getData(@RequestParam(value = "page",defaultValue = "1")Integer page
            ,@RequestParam(value = "limit",defaultValue = "8")Integer limit
            ,String title,String category){
        List<Article> articleList;
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)){
            // 模糊搜索
            wrapper.like("title",title);
        }if(!StringUtils.isEmpty(category)){
            // 模糊搜索
            wrapper.like("category_id",category);
        }
        articleList =
                articleService.page(new Page<>(page,limit),wrapper).getRecords();
        return R.success(new ArticleListDto(articleList.size(),articleList));
    }

    /**
     * 根据id返回博客内容(用于后台管理编辑数据)
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getArticleById(@PathVariable Integer id, HttpServletRequest request){
        String token = request.getHeader("X-Token");
        Claims claims = JwtUtil.parseJWT(token);
        AuthorVO authorVO = new AuthorVO();
        authorVO.setName(claims.getSubject());
        ArticleEditDto articleEditDto = articleService.getArticleDTO(id);
        articleEditDto.setAuthorVO(authorVO);
        return R.success(articleEditDto);
    }

    /**
     * 插入博客文章
     * @param articleEditDto
     * @return
     */
    @PostMapping("")
    public R save(@RequestBody ArticleEditDto articleEditDto){
        articleService.save(articleEditDto);
        return R.success();
    }

    /**
     * 更新博客文章
     * @param articleEditDto
     * @return
     */
    @PutMapping("")
    public R update(@RequestBody ArticleEditDto articleEditDto){
        articleService.updateById(articleEditDto);
        return R.success();
    }

    /**
     * 单个删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable Integer id){
        articleService.removeById(id);
        return R.success();
    }

    @DeleteMapping("")
    public R deleteByIds(@RequestParam("ids") String ids){
        String[] temp = ids.split(",");
        List<String> list = Arrays.asList(temp);
        articleService.removeByIds(list);
        return R.success();
    }
}
