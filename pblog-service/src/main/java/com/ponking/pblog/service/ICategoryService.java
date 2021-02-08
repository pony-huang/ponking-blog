package com.ponking.pblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ponking.pblog.model.dto.CategoryAddDTO;
import com.ponking.pblog.model.dto.CategoryEditDTO;
import com.ponking.pblog.model.entity.Category;
import com.ponking.pblog.model.vo.CategoryTableCardVO;

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
     *
     * @return
     */
    List<CategoryTableCardVO> listCategoryColumnInfo();

    /**
     * 保存
     *
     * @param addDTO
     * @return
     */
    boolean save(CategoryAddDTO addDTO);

    /**
     * 更新
     *
     * @param editDTO
     * @return
     */
    boolean updateById(CategoryEditDTO editDTO);
}
