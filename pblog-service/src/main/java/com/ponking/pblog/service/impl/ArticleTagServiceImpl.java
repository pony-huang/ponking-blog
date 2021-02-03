package com.ponking.pblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ponking.pblog.dao.ArticleTagMapper;
import com.ponking.pblog.model.entity.ArticleTag;
import com.ponking.pblog.service.IArticleTagService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章与标签的对应关系 服务实现类
 * </p>
 *
 * @author peng
 * @since 2020-03-22
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {

}
