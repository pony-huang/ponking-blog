package com.ponking.pblog.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ponking.pblog.model.document.EsArticle;
import com.ponking.pblog.model.dto.ArticleAddDTO;
import com.ponking.pblog.model.dto.ArticleEditDTO;
import com.ponking.pblog.model.dto.ArticleQueryDTO;
import com.ponking.pblog.model.entity.Article;
import com.ponking.pblog.model.vo.ArchiveTableCartVO;
import com.ponking.pblog.model.vo.ArchiveVO;
import com.ponking.pblog.model.vo.ArticleTopTableCardVO;
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
    ArticleEditDTO getArticleEditInfo(Serializable id);

    /**
     * 根据id值查询博客文章,用于后台插入
     *
     * @param articleAddDTO
     * @return
     */
    void save(ArticleAddDTO articleAddDTO);

    /**
     * 根据id值查询博客文章,用于后台更新
     *
     * @param articleEditDto
     */
    void updateById(ArticleEditDTO articleEditDto);


    /**
     * 博客主页(分页)列表
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<ArticleAddDTO> getArticleFrontPage(IPage page, @Param(Constants.WRAPPER) Wrapper<ArticleAddDTO> queryWrapper);

    /**
     * 博客右侧栏归档列表
     *
     * @return
     */
    List<ArchiveTableCartVO> listArchiveColumnInfo();

    /**
     * 博客右侧栏最新（置顶）列表
     *
     * @return
     */
    List<ArticleTopTableCardVO> listArticleTopColumn();

    /**
     * 归档页列表
     *
     * @return
     */
    List<ArchiveVO> listArchiveFront();


    /**
     * 博客列表
     *
     * @param iPage
     * @param wrapper
     * @return
     */
    IPage<ArticleAddDTO> articleInfoOfTagDtoList(IPage<ArticleAddDTO> iPage, @Param(Constants.WRAPPER) QueryWrapper<ArticleAddDTO> wrapper);


    /**
     * 根据id博客
     *
     * @param id
     * @return
     */
    ArticleAddDTO getArticleInfoById(Long id);

    /**
     * 归档
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<ArchiveVO> pageArchiveYearMonthFront(IPage<ArchiveVO> page, QueryWrapper<ArchiveVO> queryWrapper);

    /**
     * 更新博客创作状态
     *
     * @param articleEditDto
     */
    void updateTransferStatusById(ArticleEditDTO articleEditDto);

    /**
     * 更新博客评论状态
     *
     * @param articleEditDto
     */
    void updateCommentstatusById(ArticleEditDTO articleEditDto);

    /**
     * 更新博客状态(发布,草稿,回收箱)状态
     *
     * @param articleEditDto
     */
    void updateArticleStatusById(ArticleEditDTO articleEditDto);


    /**
     * 获取全部博客
     *
     * @return
     */
    List<EsArticle> listEsArticleAll();

    /**
     * 获取分页
     *
     * @param objectPage
     * @param queryDTO
     * @return
     */
    IPage<Article> pageArticle(Page<Article> objectPage, ArticleQueryDTO queryDTO);
}
