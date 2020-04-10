package com.ponking.pblog.controller.front;

import com.ponking.pblog.common.util.ModelVoUtil;
import com.ponking.pblog.controller.BaseController;
import com.ponking.pblog.model.R;
import com.ponking.pblog.model.vo.ArchiveVO;
import com.ponking.pblog.model.vo.ArchivesFrontVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Ponking
 * @ClassName ArchivesController
 * @date 2020/4/7--21:42
 **/
@Controller
public class ArchivesController extends BaseController {

    @RequestMapping("/archives")
    public String index(Model model){
        getBlogInfoModel(model);
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
