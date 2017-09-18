package com.yanerwu.phone;

import com.yanerwu.base.BaseTest;
import com.yanerwu.service.InformationService;
import com.yanerwu.utils.DateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author Zuz
 * @Date 2017/8/31 18:18
 * @Description
 */
public class PhoneTest extends BaseTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InformationService informationService;

    @Test
    public void informationTest() {
        List<String> dates = DateUtils.getDatesBetweenTwoDay("2017-08-01", "2017-08-31");
        for (int i = 0; i < dates.size() - 1; i++) {
            String lastDate = dates.get(i);
            String nowDate = dates.get(i + 1);
            long l = System.currentTimeMillis();
            informationService.run(lastDate, nowDate);
            long le = System.currentTimeMillis();
            logger.info("{}处理结束,耗时{}秒", lastDate, (le - l) / 1000);
        }
    }
}
