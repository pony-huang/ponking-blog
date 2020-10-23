package com.ponking.pblog.controller.front;

import com.ponking.pblog.model.params.PBlogProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ponking
 * @ClassName AboutController
 * @date 2020/4/7--21:40
 **/
@Controller
public class AboutController extends BaseController {

    @Autowired
    private PBlogProperties config;

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
