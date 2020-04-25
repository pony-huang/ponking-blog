package com.ponking.pblog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.common.util.ModelVoUtil;
import com.ponking.pblog.controller.BaseController;
import com.ponking.pblog.model.R;
import com.ponking.pblog.model.vo.ArchiveVO;
import com.ponking.pblog.model.vo.ArchivesFrontVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String index(Model model, @RequestParam(value = "time",defaultValue = "202001")int time,
                        @RequestParam(value = "page",defaultValue = "1")int page){
        QueryWrapper<ArchiveVO> wrapper = new QueryWrapper<>();
        Calendar calendar = Calendar.getInstance();
        // 201909
        int year = time / 100;
        int month = time % 100;
        calendar.set(year,month,1);
        Date date = calendar.getTime();
        wrapper.lt("update_time",date);
        IPage<ArchiveVO> pageInfo = articleService.pageArchiveFront(new Page(page,4),wrapper);
        List<ArchivesFrontVO> records = ModelVoUtil.getArchivesFront(pageInfo.getRecords());
        getBlogInfoModel(model);
        model.addAttribute("pageRecords",records);
        model.addAttribute("page",pageInfo);
        model.addAttribute("time",time);
        return "archive";
    }

    @RequestMapping(value = "/archives/list",method = RequestMethod.GET)
    @ResponseBody
    public R list(){
        List<ArchiveVO> archivesFrontVOS = articleService.listArchiveFront();
        List<ArchivesFrontVO> archivesFrontList = ModelVoUtil.getArchivesFront(archivesFrontVOS);
        return R.success(archivesFrontList);
    }
}
