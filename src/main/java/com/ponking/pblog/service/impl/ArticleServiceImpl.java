package com.ponking.pblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.common.exception.GlobalException;
import com.ponking.pblog.model.dto.ArticleEditDto;
import com.ponking.pblog.model.dto.ArticleFrontListDto;
import com.ponking.pblog.model.entity.Article;
import com.ponking.pblog.mapper.ArticleMapper;
import com.ponking.pblog.model.entity.ArticleTag;
import com.ponking.pblog.model.vo.ArchiveColumnVO;
import com.ponking.pblog.model.vo.ArchiveVO;
import com.ponking.pblog.model.vo.ArchivesFrontVO;
import com.ponking.pblog.model.vo.ArticleTopColumnVO;
import com.ponking.pblog.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    private ArticleMapper articleMapper;

    @Autowired
    private IArticleTagService articleTagService;

    @Override
    public boolean save(Article article) {
        //1. 是否已存在该标题
        String title = article.getTitle();
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("title",title);
        Integer count = articleMapper.selectCount(wrapper);
        if(count>0){
            throw new GlobalException("已存在【"+article.getTitle()+"】标题");
        }
        return super.save(article);
    }

    /**
     * 编辑界面传输数据
     * @param articleEditDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ArticleEditDto articleEditDto) {
        // 1. 是否已存在该标题
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        String title = articleEditDto.getTitle();
        wrapper.eq("title",title);
        Integer count = articleMapper.selectCount(wrapper);
        if(count>0){
            throw new GlobalException("已存在【"+ articleEditDto.getTitle()+"】标题");
        }
        // 2. 插入文章
        Article article = new Article();
        BeanUtils.copyProperties(articleEditDto,article);
        // 获取新插入记录的主键
        articleMapper.insert(article);
        Long articleId = article.getId();
        // 为新文章更新新的标签
        List<ArticleTag> articleTags = articleEditDto.getTagIds().stream()
                .map(e->new ArticleTag(articleId,e)).distinct().collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
    }

    /**
     * 编辑文章
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
        String titleDb = articleMapper.selectById(articleEditDto.getId()).getTitle();
        wrapper1.eq("title",titleEdit);
        Integer count = articleMapper.selectCount(wrapper1);
        if(count>0&&!titleDb.equals(titleEdit)){
            throw new GlobalException("已存在【"+ articleEditDto.getTitle()+"】该标题");
        }
        // 2. article 复制属性，且更新文章
        Article article = new Article();
        BeanUtils.copyProperties(articleEditDto,article);
        articleMapper.updateById(article);

        // 3. 为新文章更新新的标签(先删除原来，再更新最新的)
        List<ArticleTag> articleTags = articleEditDto.getTagIds().stream()
                .map(e->new ArticleTag(articleEditDto.getId(),e)).distinct().collect(Collectors.toList());
        QueryWrapper<ArticleTag> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("article_id", articleEditDto.getId());
        articleTagService.remove(wrapper2);
        articleTagService.saveBatch(articleTags);
    }


    /**
     * 博客文章分页
     * @param page
     * @param queryWrapper
     * @return
     */
    @Override
    public Page<ArticleFrontListDto> getArticleFrontPage(IPage page,@Param(Constants.WRAPPER) Wrapper<ArticleFrontListDto> queryWrapper) {
        return articleMapper.selectArticleFrontList(page,queryWrapper);
    }

    /**
     * 博客右侧栏归档列表
     *
     * @return
     */
    @Override
    public List<ArchiveColumnVO> listArchiveColumnInfo() {
        return articleMapper.selectArchiveColumnInfo();
    }

    /**
     * 博客右侧栏最新（置顶）列表
     * @return
     */
    @Override
    public List<ArticleTopColumnVO> listArticleTopColumn() {
        return articleMapper.selectListArticleTopColumn();
    }

    /**
     * 归档页列表
     *
     * @return
     */
    @Override
    public List<ArchiveVO> listArchiveFront() {
        return articleMapper.selectArchiveFrontAll();
    }

    @Override
    public ArticleEditDto getArticleDTO(Serializable id) {
        return articleMapper.selectOneDTO(id);
    }
}
