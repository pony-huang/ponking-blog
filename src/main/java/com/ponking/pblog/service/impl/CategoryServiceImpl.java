package com.ponking.pblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ponking.pblog.common.exception.GlobalException;
import com.ponking.pblog.model.entity.Category;
import com.ponking.pblog.mapper.CategoryMapper;
import com.ponking.pblog.model.vo.CategoryColumnVO;
import com.ponking.pblog.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author peng
 * @since 2020-03-20
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 插入新的分类
     * @param category
     * @return
     */
    @Override
    public boolean save(Category category) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("name",category.getName());
        // 1. 判断是否已存在该分类
        Integer count = categoryMapper.selectCount(wrapper);
        if(count>0){
            throw new GlobalException("已存在【"+category.getName()+"】分类");
        }
        return super.save(category);
    }

    /**
     * 更新Category
     * @param category
     * @return
     */
    @Override
    public boolean updateById(Category category) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        // 1.  originName 为原始名称,updateName为需要更新的名称,若originName = updateName可视为允许修改
        String originName = categoryMapper.selectById(category.getId()).getName();
        String updateName = category.getName();
        if (updateName.equals(originName)){
            return super.updateById(category);
        }
        // 2. 是否已存在该分类(原始的名称可以省略)
        wrapper.eq("name", updateName);
        Integer count = categoryMapper.selectCount(wrapper);
        if(count>0){
            throw new GlobalException("已存在【"+category.getName()+"】分类");
        }
        return super.updateById(category);
    }

    /**
     * 博客左侧栏分类列表
     * @return
     */
    @Override
    public List<CategoryColumnVO> listCategoryColumnInfo() {
        return categoryMapper.selectCategoryColumnInfo();
    }
}
