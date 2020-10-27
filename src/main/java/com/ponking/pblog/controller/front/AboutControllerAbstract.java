package com.ponking.pblog.controller.front;

import com.ponking.pblog.model.params.PBlogProperties;
import com.ponking.pblog.service.IArticleService;
import com.ponking.pblog.service.ICategoryService;
import com.ponking.pblog.service.ILinkService;
import com.ponking.pblog.service.ITagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ponking
 * @ClassName AboutControllerAbstract
 * @date 2020/4/7--21:40
 **/
@Controller
public class AboutControllerAbstract extends AbstractBaseController {


    public AboutControllerAbstract(IArticleService articleService, ICategoryService categoryService, ITagService tagService, ILinkService linkService, PBlogProperties config) {
        super(articleService, categoryService, tagService, linkService, config);
    }

    @RequestMapping("/about")
    public String content(Model model){
        getBlogTableCardInfo(model);
        Map<String,Object> about = new HashMap<>();
        about.put("contentMd",config.getBlogAbout());
        about.put("updateTime", LocalDateTime.now());
        model.addAttribute("about",about);
        return "blog/about";
    }
}
