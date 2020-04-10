package com.ponking.pblog.controller.front;

import com.ponking.pblog.controller.BaseController;
import com.ponking.pblog.model.entity.Article;
import com.ponking.pblog.model.vo.ArchiveColumnVO;
import com.ponking.pblog.model.vo.ArticleTopColumnVO;
import com.ponking.pblog.model.vo.CategoryColumnVO;
import com.ponking.pblog.model.vo.TagColumnVO;
import com.ponking.pblog.service.IArticleService;
import com.ponking.pblog.service.ICategoryService;
import com.ponking.pblog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Ponking
 * @ClassName ArticleDetailController
 * @date 2020/4/8--14:58
 **/
@Controller
public class ArticleDetailController  extends BaseController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ITagService tagService;

    @RequestMapping("/article/{id}")
    public String articleDetail(Model model, @PathVariable("id") Integer id) {
        Article article = articleService.getById(id);
        model.addAttribute("article", article);
        getBlogInfoModel(model);
        return "detail/article_detail";
    }
}
