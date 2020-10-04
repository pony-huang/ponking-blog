package com.ponking.pblog.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ponking
 * @ClassName AboutController
 * @date 2020/4/7--21:40
 **/
@Controller
public class AboutController extends BaseController {

    @RequestMapping("/about")
    public String content(Model model){
        getBlogTableCardInfo(model);
        return "blog/about";
    }
}
