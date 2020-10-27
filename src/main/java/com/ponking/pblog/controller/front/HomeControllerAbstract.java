package com.ponking.pblog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.model.dto.ArticleDto;
import com.ponking.pblog.model.params.PBlogProperties;
import com.ponking.pblog.service.IArticleService;
import com.ponking.pblog.service.ICategoryService;
import com.ponking.pblog.service.ILinkService;
import com.ponking.pblog.service.ITagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ponking
 * @ClassName HomeControllerAbstract
 * @date 2020/4/7--21:17
 **/
@Controller
public class HomeControllerAbstract extends AbstractBaseController {


    public HomeControllerAbstract(IArticleService articleService,
                                  ICategoryService categoryService,
                                  ITagService tagService,
                                  ILinkService linkService,
                                  PBlogProperties config) {
        super(articleService, categoryService, tagService, linkService, config);
    }

    @RequestMapping("/")
    public String index(){
        return "redirect:/home";
    }


    @RequestMapping("/admin/index")
    public String admin(){
        return "admin/index";
    }


    @RequestMapping("/home")
    public String content(Model model, @RequestParam(value = "page",defaultValue = "1")int page){
        IPage<ArticleDto> iPage = new Page<>(page,3);
        IPage<ArticleDto> articles = articleService.getArticleFrontPage(iPage,new QueryWrapper<>());
        model.addAttribute("articles",articles);
        getBlogTableCardInfo(model);
        return "blog/index";
    }
}
