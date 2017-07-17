package com.yanerwu;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.entity.BookSummary;
import com.yanerwu.processor.BiqugeIdProcessor;
import com.yanerwu.processor.QidianProcessor;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.codecraft.webmagic.Spider;

import java.net.URLEncoder;
import java.util.List;

/**
 * @Author Zuz
 * @Date 2017/7/7 16:52
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springmvc-jdbc.xml", "classpath:springmvc-servlet.xml"})
public class QidianTest {
    static String initUrl = "http://m.qidian.com/majax/rank/yuepiaolist?_csrfToken=CVvOKdS8H4MRhI0tR2URT6V3H9gzEn1jRy760prS&gender=male&catId=-1&yearmonth=201707";

    @Autowired
    private DbUtilsTemplate bookTemplate;

    @Test
    public void getBookSummary() {
        Spider.create(new QidianProcessor(bookTemplate))
                .addUrl(initUrl)
                .thread(1)
                .run();
    }

    @Test
    public void getBiqugeIdBySummary() throws Exception {
        String searchUrl = "http://zhannei.baidu.com/cse/search?s=14041278195252845489&q=%s";
        String sql = "select * from book_summary where biquge_url is null limit 999";
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        List<BookSummary> bookSummaries = bookTemplate.find(BookSummary.class, sql, processor);
        for (BookSummary b : bookSummaries) {
            Spider.create(new BiqugeIdProcessor(b.getId(), b.getName(), bookTemplate))
                    .addUrl(String.format(searchUrl, URLEncoder.encode(b.getName(), "UTF-8")))
                    .thread(1)
                    .run();
            Thread.sleep(1000);
        }
    }

}
