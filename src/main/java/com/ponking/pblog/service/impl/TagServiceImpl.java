package com.ponking.pblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ponking.pblog.common.exception.GlobalException;
import com.ponking.pblog.model.entity.Tag;
import com.ponking.pblog.mapper.TagMapper;
import com.ponking.pblog.model.vo.TagColumnVO;
import com.ponking.pblog.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author peng
 * @since 2020-03-14
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public boolean save(Tag tag) {
        // 1. 判断是否已存在该标签
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("name",tag.getName());
        Integer count = tagMapper.selectCount(wrapper);
        if(count>0){
            throw new GlobalException("已存在【"+tag.getName()+"】标签");
        }
        return super.save(tag);
    }

    /**
     * 更新Tag
     * @param tag
     * @return
     */
    @Override
    public boolean updateById(Tag tag) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        // 1.  originName 为原始名称,updateName为需要更新的名称,若originName = updateName可视为允许修改
        String originName = tagMapper.selectById(tag.getId()).getName();
        String updateName = tag.getName();
        if (updateName.equals(originName)){
            return super.updateById(tag);
        }
        // 2. 是否已存在该分类(原始的名称可以省略)
        wrapper.eq("name", updateName);
        Integer count = tagMapper.selectCount(wrapper);
        if(count>0){
            throw new GlobalException("已存在【"+tag.getName()+"】分类");
        }
        return super.updateById(tag);
    }

    /**
     * 博客右侧栏标签
     * @return
     */
    @Override
    public List<TagColumnVO> listTagColumnInfo() {
        return tagMapper.selectTagColumnInfo();
    }
}
