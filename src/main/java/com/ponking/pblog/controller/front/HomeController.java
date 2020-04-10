package com.ponking.pblog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.controller.BaseController;
import com.ponking.pblog.model.dto.ArticleFrontListDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ponking
 * @ClassName HomeController
 * @date 2020/4/7--21:17
 **/
@Controller
public class HomeController extends BaseController {

    @RequestMapping("/")
    public String index(Model model){
        IPage<ArticleFrontListDto> iPage = new Page<>(1,3);
        IPage<ArticleFrontListDto> articles = articleService.getArticleFrontPage(iPage,new QueryWrapper<>());
        model.addAttribute("articles",articles);
        getBlogInfoModel(model);
        return "index";
    }


    @RequestMapping("/home")
    public String home(Model model, @RequestParam(value = "page",defaultValue = "1")int page){
        IPage<ArticleFrontListDto> iPage = new Page<>(page,3);
        IPage<ArticleFrontListDto> articles = articleService.getArticleFrontPage(iPage,new QueryWrapper<>());
        model.addAttribute("articles",articles);
        getBlogInfoModel(model);
        return "index";
    }


}
