package com.ponking.pblog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.controller.BaseController;
import com.ponking.pblog.model.dto.ArticleInfoDto;
import com.ponking.pblog.model.dto.ArticleWithCategoryFrontDto;
import com.ponking.pblog.model.vo.PersonInfoVO;
import org.springframework.beans.factory.annotation.Value;
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
        IPage<ArticleWithCategoryFrontDto> iPage = new Page<>(1,3);
        IPage<ArticleInfoDto> articles = articleService.getArticleFrontPage(iPage,new QueryWrapper<>());
        model.addAttribute("articles",articles);
        getBlogInfoModel(model);
        return "index";
    }


    @RequestMapping("/home")
    public String home(Model model, @RequestParam(value = "page",defaultValue = "1")int page){
        IPage<ArticleWithCategoryFrontDto> iPage = new Page<>(page,3);
        IPage<ArticleInfoDto> articles = articleService.getArticleFrontPage(iPage,new QueryWrapper<>());
        model.addAttribute("articles",articles);
        getBlogInfoModel(model);
        return "index";
    }
}
