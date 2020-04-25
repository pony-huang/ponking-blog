package com.ponking.pblog.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ponking.pblog.model.dto.ArticleEditDto;
import com.ponking.pblog.model.dto.ArticleInfoDto;
import com.ponking.pblog.model.dto.ArticleWithCategoryFrontDto;
import com.ponking.pblog.model.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ponking.pblog.model.vo.ArchiveColumnVO;
import com.ponking.pblog.model.vo.ArchiveVO;
import com.ponking.pblog.model.vo.ArticleTopColumnVO;
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
    ArticleEditDto getArticleDTO(Serializable id);

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
    IPage<ArticleInfoDto> getArticleFrontPage(IPage page, @Param(Constants.WRAPPER) Wrapper<ArticleInfoDto> queryWrapper);

    /**
     * 博客右侧栏归档列表
     *
     * @return
     */
    List<ArchiveColumnVO> listArchiveColumnInfo();

    /**
     * 博客右侧栏最新（置顶）列表
     * @return
     */
    List<ArticleTopColumnVO> listArticleTopColumn();

    /**
     * 归档页列表
     * @return
     */
    List<ArchiveVO> listArchiveFront();


    /**
     * 归档页分页
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<ArchiveVO> pageArchiveFront(IPage page, @Param(Constants.WRAPPER) Wrapper<ArchiveVO> queryWrapper);


    /**
     * 博客列表
     * @param iPage
     * @param wrapper
     * @return
     */
    IPage<ArticleInfoDto> articleInfoOfTagDtoList(IPage<ArticleInfoDto> iPage, QueryWrapper<ArticleInfoDto> wrapper);


    /**
     * 根据id博客
     * @param id
     * @return
     */
    ArticleInfoDto getArticleInfoById(Long id);
}
