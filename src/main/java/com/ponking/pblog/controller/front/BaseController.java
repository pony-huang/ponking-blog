package com.ponking.pblog.controller.front;

import com.ponking.pblog.config.PBlogConfig;
import com.ponking.pblog.model.entity.Link;
import com.ponking.pblog.model.vo.*;
import com.ponking.pblog.service.IArticleService;
import com.ponking.pblog.service.ICategoryService;
import com.ponking.pblog.service.ILinkService;
import com.ponking.pblog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @author Ponking
 * @ClassName BaseController
 * @date 2020/4/8--14:24
 **/
public abstract class BaseController {
    @Autowired
    protected IArticleService articleService;

    @Autowired
    protected ICategoryService categoryService;

    @Autowired
    protected ITagService tagService;

    @Autowired
    protected ILinkService linkService;

    @Autowired
    private PBlogConfig config;

    /**
     * 左右侧栏信息
     * @param model
     * @return
     */
    protected Model getBlogTableCardInfo(Model model){
        List<CategoryTableCardVo> categoryTableCardVoList = categoryService.listCategoryColumnInfo();
        List<TagTableCardVo> tagTableCardVoList = tagService.listTagColumnInfo();
        List<ArchiveTableCartVo> archiveTableCartVoList = articleService.listArchiveColumnInfo();
        List<ArticleTopTableCardVo> articleTopTableCardVoList = articleService.listArticleTopColumn();
        List<Link> links = linkService.list();

        String name = config.getBlogAuthor();
        String city = config.getAuthorCity();
        String title = config.getBlogTitle();
        String avatar = config.getBlogAvatar();
        /**
         * 统计信息
         */
        int articleCount = articleService.count();
        int tagCount = tagService.count();
        int cateCount = categoryService.count();
        AuthorInfoTableCardVo person = new AuthorInfoTableCardVo(name,city,avatar,title,articleCount,tagCount,cateCount);

        model.addAttribute("info",person);
        model.addAttribute("categories", categoryTableCardVoList);
        model.addAttribute("tags", tagTableCardVoList);
        model.addAttribute("archives", archiveTableCartVoList);
        model.addAttribute("newArticles", articleTopTableCardVoList);
        model.addAttribute("links",links);
        return model;
    }
}
