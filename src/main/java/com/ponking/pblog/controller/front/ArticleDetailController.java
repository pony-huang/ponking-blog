package com.ponking.pblog.controller.front;

import com.ponking.pblog.controller.BaseController;
import com.ponking.pblog.model.dto.ArticleInfoDto;
import com.ponking.pblog.model.entity.Article;
import com.ponking.pblog.service.IArticleService;
import com.ponking.pblog.service.ICategoryService;
import com.ponking.pblog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ponking
 * @ClassName ArticleDetailController
 * @date 2020/4/8--14:58
 **/
@Controller
public class ArticleDetailController  extends BaseController {

    @Autowired
    private IArticleService articleService;


    @RequestMapping("/articles/{id}")
    public String articleDetail(Model model, @PathVariable("id") Long id) {
        ArticleInfoDto article = articleService.getArticleInfoById(id);
        model.addAttribute("article", article);
        getBlogInfoModel(model);
        return "detail/front_article_detail";
    }
}
