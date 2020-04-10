package com.ponking.pblog;

import com.ponking.pblog.mapper.ArticleMapper;
import com.ponking.pblog.model.dto.ArticleEditDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PBlogApplicationTests {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void contextLoads() {
        ArticleEditDto articleEditDto = articleMapper.selectOneDTO(1);
        System.out.println(articleEditDto);
    }

}
