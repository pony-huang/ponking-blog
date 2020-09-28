package com.ponking.pblog.controller.front;

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
public class BaseController {
    @Autowired
    protected IArticleService articleService;

    @Autowired
    protected ICategoryService categoryService;

    @Autowired
    protected ITagService tagService;

    @Autowired
    protected ILinkService linkService;


    @Value("${pblog.author-name}")
    private String authorName;

    @Value("${pblog.author-location}")
    private String authorLocation;


    @Value("${pblog.author-title}")
    private String authorTitle;

    @Value("${pblog.author-avatar}")
    private String authorAvatar;


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

        String name = authorName;
        String city = authorLocation;
        String title = authorTitle;
        String avatar = authorAvatar;
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
