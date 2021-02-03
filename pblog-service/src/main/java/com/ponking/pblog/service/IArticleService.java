package com.ponking.pblog.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ponking.pblog.model.document.EsArticle;
import com.ponking.pblog.model.dto.ArticleDto;
import com.ponking.pblog.model.dto.ArticleEditDto;
import com.ponking.pblog.model.entity.Article;
import com.ponking.pblog.model.vo.ArchiveTableCartVo;
import com.ponking.pblog.model.vo.ArchiveVo;
import com.ponking.pblog.model.vo.ArticleTopTableCardVo;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author peng
 * @since 2020-03-20
 */
public interface IArticleService extends IService<Article> {

    /**
     * 根据id值查询博客文章
     *
     * @param id
     * @return
     */
    ArticleEditDto getArticleEditInfo(Serializable id);

    /**
     * 根据id值查询博客文章,用于后台插入
     *
     * @param articleEditDto
     * @return
     */
    void save(ArticleEditDto articleEditDto);

    /**
     * 根据id值查询博客文章,用于后台更新
     *
     * @param articleEditDto
     */
    void updateById(ArticleEditDto articleEditDto);


    /**
     * 博客主页(分页)列表
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<ArticleDto> getArticleFrontPage(IPage page, @Param(Constants.WRAPPER) Wrapper<ArticleDto> queryWrapper);

    /**
     * 博客右侧栏归档列表
     *
     * @return
     */
    List<ArchiveTableCartVo> listArchiveColumnInfo();

    /**
     * 博客右侧栏最新（置顶）列表
     * @return
     */
    List<ArticleTopTableCardVo> listArticleTopColumn();

    /**
     * 归档页列表
     * @return
     */
    List<ArchiveVo> listArchiveFront();


    /**
     * 博客列表
     * @param iPage
     * @param wrapper
     * @return
     */
    IPage<ArticleDto> articleInfoOfTagDtoList(IPage<ArticleDto> iPage,@Param(Constants.WRAPPER) QueryWrapper<ArticleDto> wrapper);


    /**
     * 根据id博客
     * @param id
     * @return
     */
    ArticleDto getArticleInfoById(Long id);

    /**
     * 归档
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<ArchiveVo> pageArchiveYearMonthFront(IPage<ArchiveVo> page, QueryWrapper<ArchiveVo> queryWrapper);

    /**
     * 更新博客创作状态
     * @param articleEditDto
     */
    void updateTransferStatusById(ArticleEditDto articleEditDto);

    /**
     * 更新博客评论状态
     * @param articleEditDto
     */
    void updateCommentstatusById(ArticleEditDto articleEditDto);

    /**
     * 更新博客状态(发布,草稿,回收箱)状态
     * @param articleEditDto
     */
    void updateArticleStatusById(ArticleEditDto articleEditDto);


    /**
     * 获取全部博客
     * @return
     */
    List<EsArticle> listEsArticleAll();
}
