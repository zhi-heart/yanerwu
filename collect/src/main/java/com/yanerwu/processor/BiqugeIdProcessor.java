package com.yanerwu.processor;

import com.yanerwu.common.BaseProcessor;
import com.yanerwu.common.DbUtilsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @Author Zuz
 * @Date 2017/7/7 16:23
 * @Description 根据
 */
public class BiqugeIdProcessor extends BaseProcessor implements PageProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private DbUtilsTemplate bookTemplate;
    private Long id;
    private String bookName;

    public BiqugeIdProcessor(Long id,String bookName, DbUtilsTemplate bookTemplate) {
        this.id = id;
        this.bookName = bookName;
        this.bookTemplate = bookTemplate;
    }

    @Override
    public void process(Page page) {
        String biqugeBookName = page.getHtml().xpath("//h3[@class='result-item-title result-game-item-title']/a/@title").get().trim();
        String biqugeUrl = page.getHtml().xpath("//h3[@class='result-item-title result-game-item-title']/a/@href").get().trim();

        if (bookName.equals(biqugeBookName)) {
            String sql = "update book_summary set biquge_url=? where id=?";
            bookTemplate.update(sql, new Object[]{
                    biqugeUrl,
                    id
            });
        }
    }

    @Override
    public Site getSite() {
        Site site = super.getSite();
        site.setSleepTime(1000);
        site.setDomain("zhannei.baidu.com");
        return site;
    }
}
