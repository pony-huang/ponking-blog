package com.ponking.pblog.config;

import com.ponking.pblog.model.entity.BlogConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author peng
 * @date 2020/10/18--14:01
 * @Des
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class PBlogConfigTest {

    @Autowired
    private PBlogConfig blogConfig;

    @Test
    public void test01(){
        PBlogConfig blogConfig = this.blogConfig;
        System.out.println();
    }

}