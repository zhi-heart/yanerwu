package com.yanerwu.book;

import com.yanerwu.base.BaseTest;
import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.entity.BookSummary;
import com.yanerwu.processor.BiqugeIdProcessor;
import com.yanerwu.processor.QidianProcessor;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Spider;

import java.net.URLEncoder;
import java.util.List;

/**
 * @Author Zuz
 * @Date 2017/7/7 16:52
 * @Description
 */
public class QidianTest extends BaseTest{
    static String initUrl = "http://m.qidian.com/majax/rank/yuepiaolist?_csrfToken=CVvOKdS8H4MRhI0tR2URT6V3H9gzEn1jRy760prS&gender=male&catId=-1&yearmonth=201708";

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
        String searchUrl = "http://zhannei.baidu.com/cse/search?s=15390153038627446418&q=%s";
        String sql = "select * from book_summary where collect_url is null limit 999";
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        List<BookSummary> bookSummaries = bookTemplate.find(BookSummary.class, sql, processor);
        for (BookSummary b : bookSummaries) {
            Spider.create(new BiqugeIdProcessor(b.getId(), b.getName(), bookTemplate))
                    .addUrl(String.format(searchUrl, URLEncoder.encode(b.getName(), "UTF-8")))
                    .thread(3)
                    .run();
            Thread.sleep(1000);
        }
    }

}
