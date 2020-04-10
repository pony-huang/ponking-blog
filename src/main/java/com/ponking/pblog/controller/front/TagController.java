package com.ponking.pblog.controller.front;

import com.ponking.pblog.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ponking
 * @ClassName TagController
 * @date 2020/4/7--21:44
 **/
@Controller
public class TagController extends BaseController {

    @RequestMapping("/tags")
    public String index(Model model){
        getBlogInfoModel(model);
        return "tag";
    }

    @RequestMapping("/tags/{id}")
    public String list(Model model, @PathVariable Integer id){
        getBlogInfoModel(model);
        return "detail/tag_article_list";
    }
}
