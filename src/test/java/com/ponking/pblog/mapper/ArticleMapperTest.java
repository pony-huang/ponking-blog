package com.ponking.pblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.model.dto.ArticleFrontListDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Ponking
 * @ClassName ArticleMapperTest
 * @date 2020/4/7--22:44
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void selectOneDTO() {
    }

    @Test
    public void selectArticleFrontList() {
        QueryWrapper<ArticleFrontListDto> wrapper = new QueryWrapper();
        Page dtoPage = articleMapper.selectArticleFrontList(new Page<>(1,2),wrapper);
        System.out.println(dtoPage);
    }
}
