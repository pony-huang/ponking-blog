package com.ponking.pblog.controller;

import com.ponking.pblog.model.entity.Link;
import com.ponking.pblog.model.vo.ArchiveColumnVO;
import com.ponking.pblog.model.vo.ArticleTopColumnVO;
import com.ponking.pblog.model.vo.CategoryColumnVO;
import com.ponking.pblog.model.vo.TagColumnVO;
import com.ponking.pblog.service.IArticleService;
import com.ponking.pblog.service.ICategoryService;
import com.ponking.pblog.service.ILinkService;
import com.ponking.pblog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
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

    protected Model getBlogInfoModel(Model model){
        List<CategoryColumnVO> categoryColumnVOS = categoryService.listCategoryColumnInfo();
        List<TagColumnVO> tagColumnVOS = tagService.listTagColumnInfo();
        List<ArchiveColumnVO> archiveColumnVOS = articleService.listArchiveColumnInfo();
        List<ArticleTopColumnVO> articleTopColumnVOS = articleService.listArticleTopColumn();
        List<Link> links = linkService.list();
        model.addAttribute("categories",categoryColumnVOS);
        model.addAttribute("tags",tagColumnVOS);
        model.addAttribute("archives",archiveColumnVOS);
        model.addAttribute("newArticles",articleTopColumnVOS);
        model.addAttribute("links",links);
        return model;
    }
}
