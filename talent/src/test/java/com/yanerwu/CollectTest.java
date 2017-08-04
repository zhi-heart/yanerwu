package com.yanerwu;

import com.yanerwu.common.DbUtilsTemplate;
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
@ContextConfiguration(locations = {"classpath:springmvc-servlet.xml"})
public class CollectTest {

    @Autowired
    private DbUtilsTemplate talentTemplate;

    @Test
    public void jdbcTest() throws Exception {
        String sql = "select id from goods_top where id=1";
        String s = String.valueOf(talentTemplate.findBy(sql, 1));
        System.out.println(s);

    }

}
