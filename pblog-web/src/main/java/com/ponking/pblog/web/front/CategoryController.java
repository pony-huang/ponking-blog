package com.ponking.pblog.web.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.common.params.PBlogProperties;
import com.ponking.pblog.model.dto.ArticleDTO;
import com.ponking.pblog.model.entity.Category;
import com.ponking.pblog.service.IArticleService;
import com.ponking.pblog.service.ICategoryService;
import com.ponking.pblog.service.ILinkService;
import com.ponking.pblog.service.ITagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ponking
 * @ClassName CategoryControllerAbstract
 * @date 2020/4/7--21:42
 **/
@Controller
public class CategoryController extends AbstractBaseController {


    public CategoryController(IArticleService articleService, ICategoryService categoryService, ITagService tagService, ILinkService linkService, PBlogProperties config) {
        super(articleService, categoryService, tagService, linkService, config);
    }

    @RequestMapping("/categories")
    public String index(Model model) {
        getBlogTableCardInfo(model);
        return "blog/category";
    }

    @RequestMapping("/categories/{categoryId}")
    public String content(Model model, @PathVariable Integer categoryId, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        IPage<ArticleDTO> iPage = new Page<>(page, 4);
        QueryWrapper<ArticleDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id", categoryId);
        IPage<ArticleDTO> articles = articleService.getArticleFrontPage(iPage, wrapper);
        Category category = categoryService.getById(categoryId);
        model.addAttribute("articles", articles);
        model.addAttribute("category", category);
        getBlogTableCardInfo(model);
        return "blog/detail/front_category_article_list";
    }
}
