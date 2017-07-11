package com.yanerwu;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.service.BiqugeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author Zuz
 * @Date 2017/7/7 16:52
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springmvc-jdbc.xml", "classpath:springmvc-servlet.xml"})
public class BiqugeTest {

    @Autowired
    private DbUtilsTemplate bookTemplate;
    @Autowired
    private BiqugeService biqugeService;

    @Test
    public void getBiqugeIdBySummary() throws Exception {
        biqugeService.biqugeDetailByName("天道图书馆");
    }

}
