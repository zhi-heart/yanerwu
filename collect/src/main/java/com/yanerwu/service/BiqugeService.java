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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        List<String> bookList = new ArrayList<>();
        ExecutorService exec = Executors.newFixedThreadPool(3);
        for (BookSummary bs : bookSummarys) {
            if (StringUtils.isBlank(bs.getCollectUrl())) {
                continue;
            }
            bookList.add(bs.getName());
            exec.execute(
                    () -> {
                        long l = System.currentTimeMillis();
                        MDC.put("book", bs.getName());
                        Spider.create(new BiqugeProcessor(bs, bookTemplate))
                                .addUrl(bs.getCollectUrl())
                                .thread(5)
                                .run();
                        bs.setUpdateTime(DateUtils.getNowTime());
                        bookTemplate.update(bs);
                        logger.info("[{}]采集完毕,耗时{}秒", bs.getName(), (System.currentTimeMillis() - l) / 1000);
                    }
            );
        }
        exec.shutdown();
        while (true) {
            if (exec.isTerminated()) {
                logger.info("[{}]采集结束",Arrays.toString(bookList.toArray()));
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public void biqugeDetailByName(String... name) {
        String sql = "select * from book_summary where name=? limit 1";
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        List<BookSummary> bookSummarys = bookTemplate.find(BookSummary.class, sql, name, processor);
        biqugeDetailByName(bookSummarys);
    }

    public void biqugeDetail(int limit) {
        String sql = "SELECT * FROM book_summary WHERE collect_url IS NOT NULL AND collect_url != '' AND update_time < date_sub(now(), INTERVAL 3 HOUR) order by rank_cnt desc LIMIT ? ";
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        List<BookSummary> bookSummarys = bookTemplate.find(BookSummary.class, sql, limit,processor);
        biqugeDetailByName(bookSummarys);
    }

}
