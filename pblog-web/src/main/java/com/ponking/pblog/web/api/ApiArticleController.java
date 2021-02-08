package com.ponking.pblog.web.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.common.result.R;
import com.ponking.pblog.model.dto.ArticleEditDto;
import com.ponking.pblog.model.dto.ArticleQueryDTO;
import com.ponking.pblog.model.entity.Article;
import com.ponking.pblog.search.IEsArticleService;
import com.ponking.pblog.service.IArticleService;
import com.ponking.pblog.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
        IPage<Article> articlePage = articleService.pageArticle(new Page<>(page, limit), articleQueryDTO);
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
    @ApiOperation("获取详情")
    public R getArticleById(@PathVariable Long id) {
        ArticleEditDto articleEditDto = articleService.getArticleEditInfo(id);
        return R.success(articleEditDto);
    }

    /**
     * 插入博客文章
     *
     * @param articleEditDto
     * @return
     */
    @PostMapping
    @ApiOperation("添加文章")
    public R save(@RequestBody ArticleEditDto articleEditDto) {
        articleService.save(articleEditDto);
        // 插入索引
        esArticleService.create(articleEditDto.getId() + "");
        return R.success();
    }

    /**
     * 单个删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiModelProperty("单个删除")
    public R deleteById(@PathVariable Long id) {
        articleService.removeById(id);
        // 删除索引
        esArticleService.delete(id + "");
        return R.success();
    }

    /**
     * 批量删除 todo
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiModelProperty("批量删除")
    public R deleteByIds(@RequestParam @ApiParam("列表ID") Set<Integer> ids) {
        articleService.removeByIds(ids);
        // 删除索引
        esArticleService.delete(ids.stream().map(String::valueOf).collect(Collectors.toList()));
        return R.success();
    }

    /**
     * 更新博客文章
     *
     * @param articleEditDto
     * @return
     */
    @PutMapping
    @ApiModelProperty("更新文章")
    public R update(@RequestBody ArticleEditDto articleEditDto) {
        articleService.updateById(articleEditDto);
        esArticleService.updatePutIndex(articleEditDto.getId() + "");
        return R.success();
    }

    /**
     * 更新博客状态(发布,草稿,回收箱)状态
     *
     * @param articleEditDto
     * @return
     */
    @PutMapping("/status")
    @ApiModelProperty("更新博客状态")
    public R updateArticleStatus(@RequestBody ArticleEditDto articleEditDto) {
        articleService.updateArticleStatusById(articleEditDto);
        esArticleService.updatePutIndex(articleEditDto.getId() + "");
        return R.success();
    }

    /**
     * 更新博客创作状态
     *
     * @param articleEditDto
     * @return
     */
    @PutMapping("/transfer/status")
    @ApiModelProperty("更新博客创作状态")
    public R updateArticleTransferStatus(@RequestBody ArticleEditDto articleEditDto) {
        articleService.updateTransferStatusById(articleEditDto);
        esArticleService.updatePutIndex(articleEditDto.getId() + "");
        return R.success();
    }

    /**
     * 更新博客评论状态
     *
     * @param articleEditDto
     * @return
     */
    @PutMapping("/comment/status")
    @ApiModelProperty("更新博客评论状态")
    public R updateArticleCommentStatus(@RequestBody ArticleEditDto articleEditDto) {
        articleService.updateCommentstatusById(articleEditDto);
        esArticleService.updatePutIndex(articleEditDto.getId() + "");
        return R.success();
    }

}
