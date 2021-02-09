package com.ponking.pblog.web.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.common.result.R;
import com.ponking.pblog.model.dto.ArticleAddDTO;
import com.ponking.pblog.model.dto.ArticleEditDTO;
import com.ponking.pblog.model.dto.ArticleQueryDTO;
import com.ponking.pblog.model.entity.Article;
import com.ponking.pblog.search.IEsArticleService;
import com.ponking.pblog.service.IArticleService;
import com.ponking.pblog.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author peng
 * @since 2020-03-20
 */

@Api(tags = {"文章功能模块"})
@RestController
@RequestMapping("/api/articles")
public class ApiArticleController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IEsArticleService esArticleService;

    /**
     * 博客列表数据
     *
     * @param page            当前页
     * @param limit           每页size
     * @param articleQueryDTO
     * @return
     */
    @GetMapping("page")
    @ApiOperation("分页查询")
    public R page(@RequestParam(value = "page", defaultValue = "1") Integer page
            , @RequestParam(value = "limit", defaultValue = "8") Integer limit
            , ArticleQueryDTO articleQueryDTO) {
        return R.success(PageUtil.getPage(articleService.pageArticle(new Page<>(page, limit), articleQueryDTO)));
    }

    /**
     * 根据id返回博客内容(用于后台管理编辑数据)
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("获取详情")
    public R getArticleById(@PathVariable Long id) {
        ArticleEditDTO articleEditDto = articleService.getArticleEditInfo(id);
        return R.success(articleEditDto);
    }

    /**
     * 插入博客文章
     *
     * @param addDTO
     * @return
     */
    @PostMapping
    @ApiOperation("添加文章")
    public R save(@RequestBody ArticleAddDTO addDTO) {
        articleService.save(addDTO);
        return R.success();
    }

    /**
     * 单个删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("单个删除")
    public R deleteById(@PathVariable Long id) {
        articleService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除 todo
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("批量删除")
    public R deleteByIds(@RequestParam @ApiParam("列表ID") Set<Integer> ids) {
        articleService.removeByIds(ids);
        return R.success();
    }

    /**
     * 更新博客文章
     *
     * @param articleEditDto
     * @return
     */
    @PutMapping
    @ApiOperation("更新文章")
    public R update(@RequestBody ArticleEditDTO articleEditDto) {
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
    @ApiOperation("更新博客状态")
    public R updateArticleStatus(@RequestBody ArticleEditDTO articleEditDto) {
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
    @ApiOperation("更新博客创作状态")
    public R updateArticleTransferStatus(@RequestBody ArticleEditDTO articleEditDto) {
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
    @ApiOperation("更新博客评论状态")
    public R updateArticleCommentStatus(@RequestBody ArticleEditDTO articleEditDto) {
        articleService.updateCommentstatusById(articleEditDto);
        return R.success();
    }

}
