package com.ponking.pblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.BaseTest;
import com.ponking.pblog.model.dto.TagInfoDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Ponking
 * @ClassName TagMapperTest
 * @date 2020/4/24--21:52
 **/

public class TagMapperTest extends BaseTest {

    @Autowired
    private TagMapper tagMapper;

    @Test
    public void selectTagColumnInfo() {
    }

    @Test
    public void selectTagInfoPage() {
        IPage<TagInfoDto> articleFrontPage = tagMapper.selectTagInfoPage(new Page<>(1, 2), new QueryWrapper<>());
        for (TagInfoDto record : articleFrontPage.getRecords()) {
            System.out.println(record);
        }
    }
}
