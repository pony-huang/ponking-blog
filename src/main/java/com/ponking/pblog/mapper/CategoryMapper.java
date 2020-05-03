package com.ponking.pblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.model.dto.CategoryDto;
import com.ponking.pblog.model.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ponking.pblog.model.vo.CategoryColumnVO;

import java.util.List;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author peng
 * @since 2020-03-20
 */
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 博客左侧栏分类列表
     * @return
     */
    List<CategoryColumnVO> selectCategoryColumnInfo();

    /**
     * 按分类归分博客
     * @param page
     * @param wrapper
     * @return
     */
    Page<CategoryDto> selectCategoryInfo(Page page, QueryWrapper<CategoryDto> wrapper);
}
