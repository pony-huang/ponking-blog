package com.ponking.pblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.model.dto.TagDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author Ponking
 * @ClassName TagMapperTest
 * @date 2020/4/24--21:52
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TagMapperTest {

    @Autowired
    private TagMapper tagMapper;

    @Test
    public void selectTagColumnInfo() {
    }

    @Test
    public void selectTagInfoPage() {
        IPage<TagDto> articleFrontPage = tagMapper.selectTagInfoPage(new Page<>(1, 2), new QueryWrapper<>());
        for (TagDto record : articleFrontPage.getRecords()) {
            System.out.println(record);
        }
    }
}
