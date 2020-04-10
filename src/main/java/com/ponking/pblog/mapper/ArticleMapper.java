package com.ponking.pblog.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.model.dto.ArticleEditDto;
import com.ponking.pblog.model.dto.ArticleFrontListDto;
import com.ponking.pblog.model.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ponking.pblog.model.vo.ArchiveColumnVO;
import com.ponking.pblog.model.vo.ArchiveVO;
import com.ponking.pblog.model.vo.ArchivesFrontVO;
import com.ponking.pblog.model.vo.ArticleTopColumnVO;
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
    ArticleEditDto selectOneDTO(@Param("id") Serializable id);

    /**
     * 博客主页列表
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<ArticleFrontListDto> selectArticleFrontList(IPage<ArticleFrontListDto> page, @Param(Constants.WRAPPER) Wrapper<ArticleFrontListDto> queryWrapper);

    /**
     * 博客右侧栏归档列表
     * @return
     */
    List<ArchiveColumnVO> selectArchiveColumnInfo();

    /**
     * 博客右侧栏最新（置顶）列表
     * @return
     */
    List<ArticleTopColumnVO> selectListArticleTopColumn();

    /**
     * 归档页列表
     * @return
     */
    List<ArchiveVO> selectArchiveFrontAll();
}
