package com.yanerwu.processor;

import com.alibaba.fastjson.JSON;
import com.yanerwu.common.BaseProcessor;
import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.entity.BookSummary;
import com.yanerwu.utils.DateUtils;
import com.yanerwu.vo.QidianVo;
import com.yanerwu.vo.Record;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Zuz
 * @Date 2017/7/7 16:23
 * @Description
 */
public class QidianProcessor extends BaseProcessor implements PageProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private DbUtilsTemplate bookTemplate;

    public QidianProcessor(DbUtilsTemplate bookTemplate) {
        this.bookTemplate = bookTemplate;
    }

    @Override
    public void process(Page page) {
        //&pageNum=1
        QidianVo qidianVo = JSON.parseObject(page.getRawText(), QidianVo.class);

        List<BookSummary> bs = new ArrayList<>();
        List<Record> records = qidianVo.getData().getRecords();
        for (Record r : records) {
            logger.info("{}\t{}", r.getName(), r.getAuth());
            BookSummary b = new BookSummary();

            String sql = "select * from book_summary where qidian_id=?";
            RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
            List<BookSummary> bookSummaryList = bookTemplate.find(BookSummary.class, sql, r.getId(), processor);
            if (bookSummaryList.size() > 0) {
                b = bookSummaryList.get(0);
            }
            b.setName(r.getName());
            b.setSummary(r.getDesc());
            b.setAuthor(r.getAuth());
            b.setQidianId(r.getId());
            b.setStatus(2);
            b.setType(r.getCat());
            b.setTypeId(r.getCatId());
            //点击量 44.36万
            b.setCnt(conver(r.getCnt()));
            //rankCnt: "3.1万月票"
            b.setRankCnt(conver(r.getRankCnt().replace("月票", "")));

            if (bookSummaryList.size() > 0) {
                bookTemplate.update(b);
            }else {
                b.setCreateTime(DateUtils.getNowTime());
                bs.add(b);
            }
        }
        bookTemplate.insert(bs);
        if (0 == qidianVo.getData().getIsLast()) {
            page.addTargetRequest(String.format("%s&pageNum=%s", page.getUrl(), qidianVo.getData().getPageNum() + 1));
        }
    }

    private Integer conver(String str) {
        Double cnt = -1.0;
        try {
            if (str.contains("万")) {
                cnt = Double.valueOf(str.replace("万", "")) * 10000;
            } else {
                cnt = Double.valueOf(str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return cnt.intValue();
    }

    @Override
    public Site getSite() {
        Site site = super.getSite();
        return site;
    }
}
