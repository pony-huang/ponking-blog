package com.ponking.pblog.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.model.dto.ArticleDto;
import com.ponking.pblog.model.dto.ArticleEditDto;
import com.ponking.pblog.model.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ponking.pblog.model.vo.ArchiveTableCartVo;
import com.ponking.pblog.model.vo.ArchiveVo;
import com.ponking.pblog.model.vo.ArticleTopTableCardVo;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author peng
 * @since 2020-03-20
 */
public interface ArticleMapper extends BaseMapper<Article> {



    /**
     * 该数据用于后台管理编辑
     * @param id
     * @return
     */
    ArticleEditDto selectArticleEditInfo(@Param("id") Serializable id);

    /**
     * 博客右侧栏归档列表
     * @return
     */
    List<ArchiveTableCartVo> selectArchiveColumnInfo();

    /**
     * 博客右侧栏最新（置顶）列表
     * @return
     */
    List<ArticleTopTableCardVo> selectListArticleTopColumn();

    /**
     * 归档页列表
     * @return
     */
    List<ArchiveVo> selectArchiveFrontAll();

    /**
     * 博客主页列表
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<ArticleDto> selectArticleDtoList(IPage<ArticleDto> page, @Param(Constants.WRAPPER) Wrapper<ArticleDto> queryWrapper);


    /**
     * 博客主页列表()
     * @param page
     * @param wrapper
     * @return
     */
    Page<ArchiveVo>  selectArticleByYearMonthDto(IPage<ArchiveVo> page, @Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 博客主页列表(标签)
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<ArticleDto> selectArticleInfoOfTagDtoList(IPage<ArticleDto> page, @Param(Constants.WRAPPER) Wrapper<ArticleDto> queryWrapper);


    /**
     * 根据id博客
     * @param id
     * @return
     */
    ArticleDto selectArticleInfoDtoOne(@Param("id") long id);
}
