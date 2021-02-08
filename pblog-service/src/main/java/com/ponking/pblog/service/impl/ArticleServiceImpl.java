package com.ponking.pblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ponking.pblog.dao.ArticleMapper;
import com.ponking.pblog.common.exception.GlobalException;
import com.ponking.pblog.model.document.EsArticle;
import com.ponking.pblog.model.dto.ArticleDTO;
import com.ponking.pblog.model.dto.ArticleEditDto;
import com.ponking.pblog.model.dto.AuthorDTO;
import com.ponking.pblog.model.entity.Article;
import com.ponking.pblog.model.entity.ArticleTag;
import com.ponking.pblog.common.params.PBlogProperties;
import com.ponking.pblog.model.vo.ArchiveTableCartVO;
import com.ponking.pblog.model.vo.ArchiveVO;
import com.ponking.pblog.model.vo.ArticleTopTableCardVO;
import com.ponking.pblog.service.IArticleService;
import com.ponking.pblog.service.IArticleTagService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author peng
 * @since 2020-03-20
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {


    @Autowired
    private IArticleTagService articleTagService;

    @Autowired
    private PBlogProperties config;

    @Override
    public boolean save(Article article) {
        //1. 是否已存在该标题
        String title = article.getTitle();
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GlobalException("已存在" + article.getTitle() + "标题");
        }
        return super.save(article);
    }

    /**
     * 编辑界面传输数据
     *
     * @param articleEditDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ArticleEditDto articleEditDto) {
        // 1. 是否已存在该标题
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        String title = articleEditDto.getTitle();
        wrapper.eq("title", title);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GlobalException("已存在" + articleEditDto.getTitle() + "标题");
        }
        // 2. 插入文章
        Article article = new Article();
        // 若图片为空,插入默认图片
        if (articleEditDto.getImage() == null) {
            articleEditDto.setImage(config.getDefaultImage());
        }
        BeanUtils.copyProperties(articleEditDto, article);
        // 获取新插入记录的主键
        baseMapper.insert(article);
        Long articleId = article.getId();
        // 为新文章更新新的标签
        List<ArticleTag> articleTags = articleEditDto.getTagIds().stream()
                .map(e -> new ArticleTag(articleId, e)).distinct().collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
    }

    /**
     * 编辑文章
     *
     * @param articleEditDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(ArticleEditDto articleEditDto) {
        // 1. 是否已存在该标题（若没有修改标题视为已修改标题）
        QueryWrapper<Article> wrapper1 = new QueryWrapper<>();
        // 修改后的title
        String titleEdit = articleEditDto.getTitle();
        // 该id文章目前存在数据库的title
        String titleDb = baseMapper.selectById(articleEditDto.getId()).getTitle();
        wrapper1.eq("title", titleEdit);
        Integer count = baseMapper.selectCount(wrapper1);
        if (count > 0 && !titleDb.equals(titleEdit)) {
            throw new GlobalException("已存在" + articleEditDto.getTitle() + "该标题");
        }
        // 2. article 复制属性，且更新文章
        Article article = new Article();
        BeanUtils.copyProperties(articleEditDto, article);
        baseMapper.updateById(article);

        // 3. 为新文章更新新的标签(先删除原来，再更新最新的)
        List<ArticleTag> articleTags = articleEditDto.getTagIds().stream()
                .map(e -> new ArticleTag(articleEditDto.getId(), e)).distinct().collect(Collectors.toList());
        QueryWrapper<ArticleTag> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("article_id", articleEditDto.getId());
        articleTagService.remove(wrapper2);
        articleTagService.saveBatch(articleTags);
    }


    /**
     * 博客文章分页
     *
     * @param page
     * @param wrapper
     * @return
     */
    @Override
    public Page getArticleFrontPage(IPage page, @Param(Constants.WRAPPER) Wrapper<ArticleDTO> wrapper) {
        return baseMapper.selectArticleDtoList(page, wrapper);
    }

    /**
     * 博客右侧栏归档列表
     *
     * @return
     */
    @Override
    public List<ArchiveTableCartVO> listArchiveColumnInfo() {
        return baseMapper.selectArchiveColumnInfo();
    }

    /**
     * 博客右侧栏最新（置顶）列表
     *
     * @return
     */
    @Override
    public List<ArticleTopTableCardVO> listArticleTopColumn() {
        return baseMapper.selectListArticleTopColumn();
    }

    @Override
    public List<ArchiveVO> listArchiveFront() {
        return baseMapper.selectArchiveFrontAll();
    }

    @Override
    public IPage<ArticleDTO> articleInfoOfTagDtoList(IPage<ArticleDTO> iPage, QueryWrapper<ArticleDTO> wrapper) {
        return baseMapper.selectArticleInfoOfTagDtoList(iPage, wrapper);
    }

    @Override
    public ArticleDTO getArticleInfoById(Long id) {
        return baseMapper.selectArticleInfoDtoOne(id);
    }

    @Override
    public IPage<ArchiveVO> pageArchiveYearMonthFront(IPage<ArchiveVO> page, QueryWrapper<ArchiveVO> wrapper) {
        return baseMapper.selectArticleByYearMonthDto(page, wrapper);
    }

    @Override
    public void updateTransferStatusById(ArticleEditDto articleEditDto) {
        Article article = new Article();
        article.setOriginal(articleEditDto.getOriginal());
        article.setId(articleEditDto.getId());
        baseMapper.updateById(article);
    }

    @Override
    public void updateCommentstatusById(ArticleEditDto articleEditDto) {
        Article article = new Article();
        article.setCommented(articleEditDto.getCommented());
        article.setId(articleEditDto.getId());
        baseMapper.updateById(article);
    }

    /**
     * 更新博客状态(发布,草稿,回收箱)状态
     *
     * @param articleEditDto
     */
    @Override
    public void updateArticleStatusById(ArticleEditDto articleEditDto) {
        Article article = new Article();
        article.setId(articleEditDto.getId());
        article.setStatus(articleEditDto.getStatus());
        baseMapper.updateById(article);
    }

    @Override
    public List<EsArticle> listEsArticleAll() {
        return baseMapper.selectEsArticleList();
    }

    @Override
    public ArticleEditDto getArticleEditInfo(Serializable id) {
        // todo 动态变化作者信息
        AuthorDTO authorDto = new AuthorDTO();
        authorDto.setId(1);
        authorDto.setName(config.getBlogAuthor());

        ArticleEditDto article = baseMapper.selectArticleEditInfo(id);
        List<Long> tagIds = articleTagService.list(new QueryWrapper<ArticleTag>().eq("article_id", id))
                .stream()
                .map(ArticleTag::getTagId).collect(Collectors.toList());
        article.setAuthor(authorDto);
        article.setTagIds(tagIds);
        return article;
    }

}
