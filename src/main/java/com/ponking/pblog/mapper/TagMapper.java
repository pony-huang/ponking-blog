package com.ponking.pblog.mapper;

import com.ponking.pblog.model.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ponking.pblog.model.vo.TagColumnVO;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author peng
 * @since 2020-03-14
 */
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 博客右侧栏标签
     * @return
     */
    List<TagColumnVO> selectTagColumnInfo();
}
