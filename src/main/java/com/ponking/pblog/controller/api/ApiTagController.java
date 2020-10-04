package com.ponking.pblog.controller.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.common.util.PageUtil;
import com.ponking.pblog.model.result.R;
import com.ponking.pblog.model.dto.TagDto;
import com.ponking.pblog.model.entity.Tag;
import com.ponking.pblog.service.ITagService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author peng
 * @since 2020-03-14
 */
@Api(value="标签controller",tags={"标签操作接口"})
@RestController
@RequestMapping("/sys/tags")
public class ApiTagController {

    @Autowired
    private ITagService tagService;

    @GetMapping("")
    public R getPage(@RequestParam("page")Integer page,@RequestParam("limit")Integer limit){
        PageUtil.BlogSysPage sysPage = PageUtil.getPage(tagService.page(new Page<>(page, limit)));
        return R.success(sysPage);
    }

    /**
     * 总数据
     * @return
     */
    @GetMapping("/all")
    public R getList(){
        List<Tag> tags = tagService.list();
        return R.success(tags);
    }

    @PostMapping("")
    public R saveTag(@RequestBody TagDto tag){
        Tag t = new Tag();
        try {
            BeanUtils.copyProperties(tag,t);
        }catch (BeansException e){
            e.printStackTrace();
            return R.failed();
        }
        tagService.save(t);
        return R.success();
    }


    @DeleteMapping("{id}")
    public R removeTag(@PathVariable Integer id){
        boolean result = tagService.removeById(id);
        if(!result){
            return R.failed();
        }
        return R.success().message("删除成功");
    }

    /**
     * 更新tag
     * @param tag
     * @return
     */
    @PutMapping("")
    public R updateTag(@RequestBody TagDto tag){
        boolean result = false;
        Tag t = new Tag();
        try {
            BeanUtils.copyProperties(t,tag);
            result = tagService.updateById(t);
        }catch (BeansException e){
            e.printStackTrace();
            return R.failed();
        }
        if(!result){
            return R.failed();
        }
        return R.success();
    }

}
