package com.ponking.pblog;


import com.ponking.pblog.model.dto.ArticleEditDTO;
import com.ponking.pblog.model.entity.Tag;
import com.ponking.pblog.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ponking
 * @ClassName ArticleServiceImplTest
 * @date 2020/3/22--21:25
 **/
//@SpringBootTest
//@RunWith(SpringRunner.class)
public class ArticleServiceImplTest {


    @Autowired
    private IArticleService articleService;

    //    @Test
    public void save() {
        ArticleEditDTO articleEditDto = new ArticleEditDTO();

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
    }

    //    @Test
    public void archiveFrontTest() {
//        List<ArchiveVo> archivesFrontVOS = articleService.listArchiveFront();
//        List<ArchivesContentVo> archivesContentVoList = ModelVoUtil.getArchivesFront(archivesFrontVOS);
//        for (ArchivesContentVo archivesContentVo : archivesContentVoList) {
//            System.out.println("------"+ archivesContentVo.getDate()+"------");
//            for (ArchiveVo data : archivesContentVo.getArchiveList()) {
//                System.out.println(data.toString());
//            }
//        }
    }
}

