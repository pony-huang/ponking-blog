package com.ponking.pblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ponking.pblog.model.vo.TagContentPage;
import com.ponking.pblog.model.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ponking.pblog.model.vo.TagTableCardVo;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author peng
 * @since 2020-03-14
 */
public interface ITagService extends IService<Tag> {

    /**
     * 博客右侧栏标签
     * @return
     */
    List<TagTableCardVo> listTagColumnInfo();


    /**
     * fdfd
     * @param iPage
     * @param wrapper
     * @return
     */
    IPage<TagContentPage> getArticleFrontPage(IPage<TagContentPage> iPage, QueryWrapper<TagContentPage> wrapper);
}
