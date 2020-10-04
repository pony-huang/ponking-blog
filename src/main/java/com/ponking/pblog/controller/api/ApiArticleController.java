package com.ponking.pblog.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.common.util.PageUtil;
import com.ponking.pblog.model.result.R;
import com.ponking.pblog.model.dto.ArticleEditDto;
import com.ponking.pblog.model.entity.Article;
import com.ponking.pblog.service.IArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

@Api(value="文章controller",tags={"文章操作接口"})
@RestController
@RequestMapping("/sys/articles")
public class ApiArticleController {

    @Autowired
    private IArticleService articleService;

    /**
     * 博客列表数据
     *
     * @param page  当前页
     * @param limit 每页size
     * @param title 标题查询条件
     * @return
     */
    @GetMapping("")
    public R getPage(@RequestParam(value = "page", defaultValue = "1") Integer page
            , @RequestParam(value = "limit", defaultValue = "8") Integer limit
            , String title, String category) {

        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            // 模糊搜索
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(category)) {
            // 模糊搜索
            wrapper.like("category_id", category);
        }
        IPage<Article> articlePage = articleService.page(new Page<>(page, limit), wrapper);
        PageUtil.BlogSysPage sysPage = PageUtil.getPage(articlePage);
        return R.success(sysPage);
    }

    /**
     * 根据id返回博客内容(用于后台管理编辑数据)
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getArticleById(@PathVariable Integer id) {
        ArticleEditDto articleEditDto = articleService.getArticleEditInfo(id);
        return R.success(articleEditDto);
    }

    /**
     * 插入博客文章
     *
     * @param articleEditDto
     * @return
     */
    @PostMapping("")
    public R save(@RequestBody ArticleEditDto articleEditDto) {
        articleService.save(articleEditDto);
        return R.success();
    }

    /**
     * 更新博客文章
     *
     * @param articleEditDto
     * @return
     */
    @PutMapping("")
    public R update(@RequestBody ArticleEditDto articleEditDto) {
        articleService.updateById(articleEditDto);
        return R.success();
    }

    /**
     * 更新博客状态(发布,草稿,回收箱)状态
     *
     * @param articleEditDto
     * @return
     */
    @PutMapping("/status")
    public R updateArticleStatus(@RequestBody ArticleEditDto articleEditDto) {
        articleService.updateArticleStatusById(articleEditDto);
        return R.success();
    }

    /**
     * 更新博客创作状态
     *
     * @param articleEditDto
     * @return
     */
    @PutMapping("/transfer/status")
    public R updateArticleTransferStatus(@RequestBody ArticleEditDto articleEditDto) {
        articleService.updateTransferStatusById(articleEditDto);
        return R.success();
    }

    /**
     * 更新博客评论状态
     *
     * @param articleEditDto
     * @return
     */
    @PutMapping("/comment/status")
    public R updateArticleCommentStatus(@RequestBody ArticleEditDto articleEditDto) {
        articleService.updateCommentstatusById(articleEditDto);
        return R.success();
    }


    /**
     * 单个删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable Integer id) {
        articleService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除 todo
     * @param ids
     * @return
     */
    @DeleteMapping("")
    public R deleteByIds(@RequestParam("ids") String ids) {
        String[] temp = ids.split(",");
        List<String> list = Arrays.asList(temp);
        articleService.removeByIds(list);
        return R.success();
    }
}
