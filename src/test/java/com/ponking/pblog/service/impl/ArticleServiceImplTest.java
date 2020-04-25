package com.ponking.pblog.service.impl;


import com.ponking.pblog.common.util.ModelVoUtil;
import com.ponking.pblog.model.dto.ArticleEditDto;
import com.ponking.pblog.model.entity.Tag;
import com.ponking.pblog.model.vo.ArchiveVO;
import com.ponking.pblog.model.vo.ArchivesFrontVO;
import com.ponking.pblog.service.IArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ponking
 * @ClassName ArticleServiceImplTest
 * @date 2020/3/22--21:25
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleServiceImplTest {


    @Autowired
    private IArticleService articleService;

    @Test
    public void save() {
        ArticleEditDto articleEditDto = new ArticleEditDto();

        Tag t1 = new Tag();
        t1.setId(12L);
        t1.setName("aaa");
        Tag t2 = new Tag();
        t2.setId(23L);
        t2.setName("bbb");

        List<Tag> tags = Arrays.asList(t1, t2);
//        articleEditDto.setTags(tags);
//        articleEditDto.setId(12L);
        articleEditDto.setTitle("hello save method");
        articleEditDto.setStatus(2);
        articleService.save(articleEditDto);
    }

    @Test
    public void archiveFrontTest() {
        List<ArchiveVO> archivesFrontVOS = articleService.listArchiveFront();
        List<ArchivesFrontVO> archivesFrontVOList = ModelVoUtil.getArchivesFront(archivesFrontVOS);
        for (ArchivesFrontVO archivesFrontVO : archivesFrontVOList) {
            System.out.println("------"+archivesFrontVO.getDate()+"------");
            for (ArchiveVO data : archivesFrontVO.getArchiveList()) {
                System.out.println(data.toString());
            }
        }
    }
}

