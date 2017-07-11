package com.yanerwu.service;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.entity.BookSummary;
import com.yanerwu.processor.BiqugeProcessor;
import com.yanerwu.utils.DateUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * @Author Zuz
 * @Date 2017/7/10 11:03
 * @Description
 */
@Service
public class BiqugeService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DbUtilsTemplate bookTemplate;

    public void biqugeDetailByName(List<BookSummary> bookSummarys) {
        for (BookSummary bs : bookSummarys) {
            if (StringUtils.isBlank(bs.getBiqugeUrl())) {
                continue;
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    long l = System.currentTimeMillis();
                    MDC.put("book", bs.getName());
                    Spider.create(new BiqugeProcessor(bs, bookTemplate))
                            .addUrl(bs.getBiqugeUrl())
                            .thread(5)
                            .run();
                    bs.setUpdateTime(DateUtils.getNowTime());
                    bookTemplate.update(bs);
                    logger.info("[{}]采集完毕,耗时{}秒", bs.getName(), (System.currentTimeMillis() - l) / 1000);
                }
            }).start();
        }
    }

    public void biqugeDetailByName(String... name) {
        String sql = "select * from book_summary where name=? limit 1";
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        List<BookSummary> bookSummarys = bookTemplate.find(BookSummary.class, sql, name, processor);
        biqugeDetailByName(bookSummarys);
    }

    public void biqugeDetailByName() {
        String sql = "select * from book_summary where biquge_url is not null and biquge_url!='' limit 3 and ";
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        List<BookSummary> bookSummarys = bookTemplate.find(BookSummary.class, sql, processor);
        biqugeDetailByName(bookSummarys);
    }
}
