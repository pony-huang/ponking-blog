package com.ponking.pblog.mapper;

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
}
