package com.ponking.pblog.service.impl;

import com.ponking.pblog.model.entity.Link;
import com.ponking.pblog.mapper.LinkMapper;
import com.ponking.pblog.service.ILinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 链接表 服务实现类
 * </p>
 *
 * @author peng
 * @since 2020-04-08
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements ILinkService {

}
