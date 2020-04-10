package com.ponking.pblog.service;

import com.ponking.pblog.model.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ponking.pblog.model.vo.CategoryColumnVO;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author peng
 * @since 2020-03-20
 */
public interface ICategoryService extends IService<Category> {
    /**
     * 博客左侧栏分类列表
     * @return
     */
    List<CategoryColumnVO> listCategoryColumnInfo();
}
