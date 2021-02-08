package com.ponking.pblog.web.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.common.result.R;
import com.ponking.pblog.model.dto.CategoryDTO;
import com.ponking.pblog.model.entity.Category;
import com.ponking.pblog.service.ICategoryService;
import com.ponking.pblog.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
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
@Api(tags = {"分类功能模块"})
@RestController
@RequestMapping("/api/categories")
public class ApiCategoryController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * 分页数据
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("page")
    @ApiOperation("分页查询")
    public R page(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        PageUtil.BlogSysPage sysPage = PageUtil.getPage(categoryService.page(new Page<>(page, limit)));
        return R.success(sysPage);
    }

    /**
     * 总数据
     *
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("列表数据")
    public R list() {
        List<Category> categories = categoryService.list();
        return R.success(categories);
    }

    @PostMapping
    @ApiOperation("添加分类")
    public R save(@RequestBody CategoryDTO category) {
        Category c = new Category();
        try {
            BeanUtils.copyProperties(category, c);
        } catch (BeansException e) {
            e.printStackTrace();
            return R.failed();
        }
        categoryService.save(c);
        return R.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("单个删除")
    public R removeById(@PathVariable Integer id) {
        boolean result = categoryService.removeById(id);
        if (!result) {
            return R.failed();
        }
        return R.success("删除成功");
    }

    /**
     * 更新category
     *
     * @param category
     * @return
     */
    @PutMapping
    @ApiOperation("更新分类")
    public R update(@RequestBody CategoryDTO category) {
        Category c = new Category();
        try {
            BeanUtils.copyProperties(c, category);
        } catch (BeansException e) {
            e.printStackTrace();
            return R.failed();
        }
        categoryService.updateById(c);
        return R.success();
    }
}
