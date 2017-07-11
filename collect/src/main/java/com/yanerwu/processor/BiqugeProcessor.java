package com.yanerwu.processor;

import com.yanerwu.common.BaseProcessor;
import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.entity.BookDetail;
import com.yanerwu.entity.BookSummary;
import com.yanerwu.utils.DateUtils;
import com.yanerwu.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Zuz
 * @Date 2017/7/5
 * @Description 笔趣阁 http://www.biquge.com.tw/
 */
public class BiqugeProcessor extends BaseProcessor implements PageProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private DbUtilsTemplate bookTemplate;
    private BookSummary bookSummary;

    public BiqugeProcessor(BookSummary bookSummary, DbUtilsTemplate bookTemplate) {
        this.bookSummary = bookSummary;
        this.bookTemplate = bookTemplate;
    }

    @Override
    public void process(Page page) {
        String currentUrl = page.getUrl().toString();
        //详情页
        if (currentUrl.equals(bookSummary.getBiqugeUrl())) {
            String biqugeLastChapter = page.getHtml().xpath("//*[@id='info']/p[4]/a/text()").get().trim();
            List<String> biqugeUrls = page.getHtml().xpath("//*[@id='list']/dl/dd/a/@href").all();
            List<String> biqugeTitles = page.getHtml().xpath("//*[@id='list']/dl/dd/a/text()").all();

            //库里是最新章节,跳过
            String lastChapter = String.valueOf(bookTemplate.findBy("select title from book_detail where book_id=? order by no desc limit 1", "title", bookSummary.getId()));
            if (lastChapter.equals(biqugeLastChapter)) {
                return;
            }

            List<String> titleList = new ArrayList<>();
            String sql = "select title from book_detail where book_id=?";
            for (Map<String, Object> map : bookTemplate.find(sql, bookSummary.getId())) {
                String title = String.valueOf(map.get("title")).trim();
                titleList.add(title);
            }

            List<BookDetail> bds = new ArrayList<>();
            for (int i = 0; i < biqugeUrls.size(); i++) {
                String title = biqugeTitles.get(i).trim();
                if (titleList.contains(title)) {
                    continue;
                }

                String titleMd5 = Tools.encoderMd5(title);

                BookDetail b = new BookDetail();
                b.setBookId(bookSummary.getId());
                b.setTitle(title);
                b.setTitleMd5(titleMd5);
                b.setNo(i);
                b.setUpdateTime(DateUtils.getNowTime());
                bds.add(b);

                page.addTargetRequest(biqugeUrls.get(i));
            }
            bookTemplate.insert(bds);
        } else {
            String title = page.getHtml().xpath("//*[@class='bookname']/h1/text()").get().trim();
            String content = page.getHtml().xpath("//*[@id='content']/text()").get();

            String sql = "update book_detail set content=? where title_md5=?";
            bookTemplate.update(sql, new Object[]{
                    content,
                    Tools.encoderMd5(title)
            });
        }
    }

    @Override
    public Site getSite() {
        Site site = super.getSite();
        site.setCharset("utf-8");
        return site;
    }
}
