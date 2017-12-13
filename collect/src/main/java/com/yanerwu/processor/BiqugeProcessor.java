package com.yanerwu.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yanerwu.common.BaseProcessor;
import com.yanerwu.common.Constants;
import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.entity.BookDetail;
import com.yanerwu.entity.BookSummary;
import com.yanerwu.utils.DateUtils;
import com.yanerwu.utils.HttpClientUtil;
import com.yanerwu.utils.Tools;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author Zuz
 * @Date 2017/7/5
 * @Description 八一中文网
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
        if (currentUrl.equals(bookSummary.getCollectUrl())) {

            String last = page.getHtml().xpath("//*[@id='info']/p[4]/a/@href").get().trim();
            last = Tools.getMatcher("/book/.*/(.*).html", last);

            List<String> collectUrls = page.getHtml().xpath("//*[@id='list']/dl/dd/a/@href").all();
            List<String> collectTitles = page.getHtml().xpath("//*[@id='list']/dl/dd/a/text()").all();

            Map<String, BookDetail> bookDetailMap = new HashMap<>();
            String sql = "select id, book_id, no, title, title_md5, content_bytes from book_detail where book_id=?";
            RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
            List<BookDetail> bookDetails = bookTemplate.find(BookDetail.class, sql, bookSummary.getId(), processor);
            for (BookDetail bookDetail : bookDetails) {
                bookDetailMap.put(bookDetail.getTitle(), bookDetail);
            }

            final List<BookDetail> bds = new ArrayList<>();
            for (int i = 0; i < collectUrls.size(); i++) {
                String title = collectTitles.get(i).trim();
                if (bookDetailMap.containsKey(title) && bookDetailMap.get(title).getContentBytes() > 0) {
                    continue;
                }

                if (!bookDetailMap.containsKey(title)) {
                    String titleMd5 = Tools.encoderMd5(title);
                    BookDetail b = new BookDetail();
                    b.setBookId(bookSummary.getId());
                    b.setTitle(title);
                    b.setTitleMd5(titleMd5);
                    b.setNo(i);
                    b.setSourceUrl(collectUrls.get(i));
                    b.setUpdateTime(DateUtils.getNowTime());
                    bds.add(b);
                }

                page.addTargetRequest(collectUrls.get(i));
            }
            bookTemplate.insert(bds);

            //推送百度
            new Thread(() -> {
                try {
                    TimeUnit.MINUTES.sleep(1);
                    StringBuffer sb = new StringBuffer();
                    for (BookDetail b : bds) {
                        String s = String.format("http://book.yanerwu.com/book/%s/%s.html\n", b.getBookId(), b.getNo());
                        sb.append(s);
                    }
                    Tools.pushBaidu(sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();


        } else {
            String title = page.getHtml().xpath("//*[@class='bookname']/h1/text()").get().trim();
            String content = page.getHtml().xpath("//*[@id='content']").get().replaceAll("<.*div.*>\n?", "").replace("\n", "").trim();

            //广告
            content = Tools.matcherReplace("。(.*[八,一,中,文,Ｗ,．,８,１,Ｚ,Ｗ,Ｃ,Ｏ]+.*Ｍ)", content);

            String sql = "update book_detail set content=?,content_bytes=? where title_md5=?";
            bookTemplate.update(sql, new Object[]{
                    content,
                    content.getBytes().length,
                    Tools.encoderMd5(title)
            });
        }
    }

    @Override
    public Site getSite() {
        Site site = super.getSite();
        site.setCharset("gbk");
        return site;
    }

    public static void main(String[] args) {
        String bookId="1003354631";
        String s = HttpClientUtil.doGet(String.format(Constants.BOOK_COMMENT_LIST_URL, bookId, 1));
        JSONArray jsonArray = JSON.parseObject(s).getJSONObject("data").getJSONArray("threadList");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            String c = o.getString("content");
            if (c.length() >= 45) {
                String threadId = o.getString("threadId");
                String s1 = HttpClientUtil.doGet(String.format(Constants.BOOK_COMMENT_DETAIL_URL, bookId, threadId));
                Html h = new Html(s1);
                String s2 = h.xpath("//*[@class='comm-content']/text()").get().trim();
                System.out.println(s2);
            }
        }
    }
}
