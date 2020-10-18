package com.ponking.pblog.service.impl;

import com.ponking.pblog.model.entity.BlogConfig;
import com.ponking.pblog.mapper.ConfigMapper;
import com.ponking.pblog.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客配置表 服务实现类
 * </p>
 *
 * @author peng
 * @since 2020-10-18
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, BlogConfig> implements IConfigService {

}
