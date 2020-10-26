package com.ponking.pblog.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author peng
 * @date 2020/10/20--20:02
 * @Des
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class QuartzTest {

    @Autowired
    private Scheduler scheduler;

    @Test
    public void test01(){
        System.out.println(scheduler);
    }
}
