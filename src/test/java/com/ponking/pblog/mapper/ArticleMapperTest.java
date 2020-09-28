package com.ponking.pblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.model.dto.ArticleDto;
import com.ponking.pblog.model.vo.ArchiveTableCartVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Ponking
 * @ClassName ArticleMapperTest
 * @date 2020/4/7--22:44
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleMapperTest  {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void selectOneDTO() {
    }

    @Test
    public void selectArticleFrontList() {
        Page<ArticleDto> articles = articleMapper.selectArticleDtoList(new Page<>(1,10),new QueryWrapper<>());
        for (ArticleDto record : articles.getRecords()) {
            System.out.println(record.getTitle());
        }
    }

    @Test
    public void selectArticleByYearMonthDto() {
        List<ArchiveTableCartVo> archiveTableCartVos = articleMapper.selectArchiveColumnInfo();
        System.out.println(archiveTableCartVos);
    }
}
