package com.ponking.pblog.controller.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.model.R;
import com.ponking.pblog.model.entity.Tag;
import com.ponking.pblog.model.dto.TagDto;
import com.ponking.pblog.service.ITagService;
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
@RestController
@RequestMapping("/sys/tags")
public class ApiTagController {

    @Autowired
    private ITagService tagService;

    @GetMapping("")
    public R getData(@RequestParam("page")Integer page,@RequestParam("limit")Integer limit){
        List<Tag> tags = tagService.page(
                new Page<>(page,limit)
        ).getRecords();
        return R.success(new TagDto(tags.size(),tags));
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
    public R saveTag(@RequestBody Tag tag){
        tagService.save(tag);
        return R.success();
    }


    @DeleteMapping("{id}")
    public R removeTag(@PathVariable Integer id){
        boolean result = tagService.removeById(id);
        if(!result){
            return R.failed();
        }
        return R.message("删除成功");
    }

    /**
     * 更新tag
     * @param tag
     * @return
     */
    @PutMapping("")
    public R updateTag(@RequestBody Tag tag){
        boolean result = tagService.updateById(tag);
        if(!result){
            return R.failed();
        }
        return R.success();
    }

}
