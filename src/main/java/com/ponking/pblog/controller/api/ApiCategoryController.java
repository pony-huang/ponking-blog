package com.ponking.pblog.controller.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.model.R;
import com.ponking.pblog.model.entity.Category;
import com.ponking.pblog.model.vo.CategoryPage;
import com.ponking.pblog.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author peng
 * @since 2020-03-20
 */
@RestController
@RequestMapping("/sys/categories")
public class ApiCategoryController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * 分页数据
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("")
    public R getPage(@RequestParam("page")Integer page,@RequestParam("limit")Integer limit){
        List<Category> categories = categoryService.page(
                new Page<>(page,limit)
        ).getRecords();
        return R.success(new CategoryPage(categories.size(),categories));
    }

    /**
     * 总数据
     * @return
     */
    @GetMapping("/all")
    public R getList(){
        List<Category> categories = categoryService.list();
        return R.success(categories);
    }

    @PostMapping("")
    public R saveCategory(@RequestBody Category category){
        categoryService.save(category);
        return R.success();
    }

    @DeleteMapping("/{id}")
    public R removeCategory(@PathVariable Integer id){
        boolean result = categoryService.removeById(id);
        if(!result){
            return R.failed();
        }
        return R.message("删除成功");
    }

    /**
     * 更新category
     * @param category
     * @return
     */
    @PutMapping("")
    public R updateCategory(@RequestBody Category category){
        boolean result = categoryService.updateById(category);
        if(!result){
            return R.failed();
        }
        return R.success();
    }
}
