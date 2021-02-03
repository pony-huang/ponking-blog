package com.ponking.pblog.web.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.model.dto.ArticleDto;
import com.ponking.pblog.model.entity.Tag;
import com.ponking.pblog.common.params.PBlogProperties;
import com.ponking.pblog.service.IArticleService;
import com.ponking.pblog.service.ICategoryService;
import com.ponking.pblog.service.ILinkService;
import com.ponking.pblog.service.ITagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ponking
 * @ClassName TagControllerAbstract
 * @date 2020/4/7--21:44
 **/
@Controller
public class TagController extends AbstractBaseController {


    public TagController(IArticleService articleService, ICategoryService categoryService, ITagService tagService, ILinkService linkService, PBlogProperties config) {
        super(articleService, categoryService, tagService, linkService, config);
    }

    @RequestMapping("/tags")
    public String index(Model model) {
        getBlogTableCardInfo(model);
        System.out.println();
        return "blog/tag";
    }


    @RequestMapping("/tags/{tagId}")
    public String list(Model model, @PathVariable Integer tagId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page) {
        IPage<ArticleDto> iPage = new Page<>(page, 4);
        QueryWrapper<ArticleDto> wrapper = new QueryWrapper<>();
        wrapper.eq("bt.id", tagId);
        IPage<ArticleDto> articles = articleService.articleInfoOfTagDtoList(iPage, wrapper);
        Tag tag = tagService.getById(tagId);
        model.addAttribute("articles", articles);
        model.addAttribute("tag", tag);
        getBlogTableCardInfo(model);
        return "blog/detail/front_tag_article_list";
    }
}
