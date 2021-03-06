package com.yanerwu.base;

import com.yanerwu.common.DbUtilsTemplate;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author Zuz
 * @Date 2017/8/26 11:31
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springmvc-jdbc.xml", "classpath:springmvc-servlet.xml"})
public class BaseTest {
    @Autowired
    protected DbUtilsTemplate bookTemplate;
}
