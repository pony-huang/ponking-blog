package com.ponking.pblog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.common.util.ModelVoUtil;
import com.ponking.pblog.model.vo.ArchiveVo;
import com.ponking.pblog.model.vo.ArchivesContentVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Ponking
 * @ClassName ArchivesController
 * @date 2020/4/7--21:42
 **/
@Controller
public class ArchivesController extends BaseController {


    @RequestMapping("/archives")
    public String content(Model model, @RequestParam(value = "time",defaultValue = "-1")int time,
                        @RequestParam(value = "page",defaultValue = "1")int page){
        QueryWrapper<ArchiveVo> wrapper = new QueryWrapper<>();
        Calendar calendar = Calendar.getInstance();
        // 201909
        Date date;
        if(time != -1){
            int year = time / 100;
            int month = time % 100;
            calendar.set(year,month-1,1);
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
            wrapper.apply("date_format(update_time,'%Y-%M') = date_format({0},'%Y-%M')",date).
                    orderByDesc("update_time");

        }else{
            date = calendar.getTime();
            wrapper.lt("update_time",date).
                    orderByDesc("update_time");
        }

        IPage<ArchiveVo> pageInfo = articleService.pageArchiveYearMonthFront(new Page<>(page,4),wrapper);
        List<ArchivesContentVo> records = new ArrayList<>();
        if(pageInfo.getRecords().size()>0){
            records = ModelVoUtil.getArchivesFront(pageInfo.getRecords());
        }
        getBlogTableCardInfo(model);
        model.addAttribute("pageRecords",records);
        model.addAttribute("page",pageInfo);
        model.addAttribute("time",time);
        return "archive";
    }
}
