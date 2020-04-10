package com.ponking.pblog.controller.front;

import com.ponking.pblog.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ponking
 * @ClassName CategoryController
 * @date 2020/4/7--21:42
 **/
@Controller
public class CategoryController extends BaseController {

    @RequestMapping("/categories")
    public String index(Model model){
        getBlogInfoModel(model);
        return "category";
    }

    @RequestMapping("/categories/{categoryName}")
    public String list(Model model, @PathVariable String categoryName){
        System.out.println(categoryName);
        getBlogInfoModel(model);
        return "detail/category_article_list";
    }
}
