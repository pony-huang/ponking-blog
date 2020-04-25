package com.ponking.pblog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.controller.BaseController;
import com.ponking.pblog.model.dto.ArticleInfoDto;
import com.ponking.pblog.model.dto.ArticleWithCategoryFrontDto;
import com.ponking.pblog.model.entity.Category;
import com.ponking.pblog.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ponking
 * @ClassName CategoryController
 * @date 2020/4/7--21:42
 **/
@Controller
public class CategoryController extends BaseController {

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping("/categories")
    public String index(Model model){
        getBlogInfoModel(model);
        return "category";
    }

    @RequestMapping("/categories/{categoryId}")
    public String list(Model model, @PathVariable Integer categoryId,@RequestParam(value = "page",defaultValue = "1") Integer page){
        IPage<ArticleInfoDto> iPage = new Page<>(page,4);
        QueryWrapper<ArticleInfoDto> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id",categoryId);
        IPage<ArticleInfoDto> articles = articleService.getArticleFrontPage(iPage,wrapper);
        Category category = categoryService.getById(categoryId);
        model.addAttribute("articles",articles);
        model.addAttribute("category",category);
        getBlogInfoModel(model);
        return "detail/front_category_article_list";
    }
}
