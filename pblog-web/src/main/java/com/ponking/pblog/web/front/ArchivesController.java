package com.ponking.pblog.web.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.model.vo.ArchiveVO;
import com.ponking.pblog.model.vo.ArchivesContentVO;
import com.ponking.pblog.common.params.PBlogProperties;
import com.ponking.pblog.service.IArticleService;
import com.ponking.pblog.service.ICategoryService;
import com.ponking.pblog.service.ILinkService;
import com.ponking.pblog.service.ITagService;
import com.ponking.pblog.util.ModelVoUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Ponking
 * @ClassName ArchivesControllerAbstract
 * @date 2020/4/7--21:42
 **/
@Controller
public class ArchivesController extends AbstractBaseController {


    public ArchivesController(IArticleService articleService, ICategoryService categoryService, ITagService tagService, ILinkService linkService, PBlogProperties config) {
        super(articleService, categoryService, tagService, linkService, config);
    }

    @RequestMapping("/archives")
    public String content(Model model, @RequestParam(value = "time", defaultValue = "-1") int time,
                          @RequestParam(value = "page", defaultValue = "1") int page) {
        QueryWrapper<ArchiveVO> wrapper = new QueryWrapper<>();
        Calendar calendar = Calendar.getInstance();
        // 201909
        Date date;
        if (time != -1) {
            int year = time / 100;
            int month = time % 100;
            calendar.set(year, month - 1, 1);
            date = calendar.getTime();
            /**
             * 拼接 sql
             * <p>!! 会有 sql 注入风险 !!</p>
             * <p>例1: apply("id = 1")</p>
             * <p>例2: apply("date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")</p>
             * <p>例3: apply("date_format(dateColumn,'%Y-%m-%d') = {0}", LocalDate.now())</p>
             * @param condition 执行条件
             * @return children
             */
            wrapper.apply("date_format(update_time,'%Y-%M') = date_format({0},'%Y-%M')", date).
                    orderByDesc("update_time");

        } else {
            date = calendar.getTime();
            wrapper.lt("update_time", date).
                    orderByDesc("update_time");
        }

        IPage<ArchiveVO> pageInfo = articleService.pageArchiveYearMonthFront(new Page<>(page, 4), wrapper);
        List<ArchivesContentVO> records = new ArrayList<>();
        if (pageInfo.getRecords().size() > 0) {
            records = ModelVoUtil.getArchivesFront(pageInfo.getRecords());
        }
        getBlogTableCardInfo(model);
        model.addAttribute("items", records);
        model.addAttribute("page", pageInfo);
        model.addAttribute("time", time);
        return "blog/archive";
    }
}
