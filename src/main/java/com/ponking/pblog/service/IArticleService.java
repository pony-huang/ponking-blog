package com.ponking.pblog.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.model.dto.ArticleEditDto;
import com.ponking.pblog.model.dto.ArticleFrontListDto;
import com.ponking.pblog.model.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ponking.pblog.model.vo.ArchiveColumnVO;
import com.ponking.pblog.model.vo.ArchiveVO;
import com.ponking.pblog.model.vo.ArchivesFrontVO;
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
    IPage<ArticleFrontListDto> getArticleFrontPage(IPage page, @Param(Constants.WRAPPER) Wrapper<ArticleFrontListDto> queryWrapper);

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
}
