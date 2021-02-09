package com.ponking.pblog.web.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.common.result.R;
import com.ponking.pblog.model.dto.CategoryAddDTO;
import com.ponking.pblog.model.dto.CategoryEditDTO;
import com.ponking.pblog.model.entity.Category;
import com.ponking.pblog.service.ICategoryService;
import com.ponking.pblog.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @GetMapping("page")
    @ApiOperation("分页查询")
    public R page(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        return R.success(PageUtil.getPage(categoryService.page(new Page<>(page, limit))));
    }

    @GetMapping("/list")
    @ApiOperation("列表数据")
    public R list() {
        List<Category> categories = categoryService.list();
        return R.success(categories);
    }

    @PostMapping
    @ApiOperation("添加分类")
    public R save(@RequestBody CategoryAddDTO category) {
        categoryService.save(category);
        return R.success().message("添加成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("单个删除")
    public R removeById(@PathVariable Integer id) {
        categoryService.removeById(id);
        return R.success().message("删除成功");
    }

    @PutMapping
    @ApiOperation("更新分类")
    public R update(@RequestBody CategoryEditDTO editDTO) {
        categoryService.updateById(editDTO);
        return R.success().message("更新成功");
    }
}
