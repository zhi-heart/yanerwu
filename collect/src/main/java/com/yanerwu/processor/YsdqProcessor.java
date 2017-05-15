package com.yanerwu.processor;

import com.yanerwu.Cache;
import com.yanerwu.common.BaseProcessor;
import com.yanerwu.entity.MvList;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author Zuz
 * @Date 2017/5/7 23:03
 * @Description 影视大全 http://www.yingshidaquan.cc/
 */
public class YsdqProcessor extends BaseProcessor implements PageProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void process(Page page) {
        String currentUrl = page.getUrl().toString();

        //详情页
        if(currentUrl.contains("http://www.yingshidaquan.cc/html/")){
            MvList m = Cache.map.get(currentUrl);

            page.getHtml().xpath("//div[@class='pic']/img/@src").get();
            m.setSummary(page.getHtml().xpath("//div[@class='endtext']/text()").get());
            m.setArea(page.getHtml().xpath("//div[@class='fl']/text()").get());
            m.setDownloadUrl(page.getHtml().regex("downlist\".*unescape\\(\"(.*)\\\"\\)").get());

            page.putField("m", m);
        }else{
            List<String> infoStrList = page.getHtml().xpath("//div[@class=info]").all();
            List<MvList> mvs = new ArrayList<>();
            for (String infoStr : infoStrList) {
                MvList m = new MvList();

                Html html = new Html(Jsoup.parse(infoStr));

                m.setOriginalUrl(html.xpath("//h2/a/@href").get());
                m.setName(html.xpath("//h2/a/@title").get());
                m.setActor(html.xpath("//p[1]/text()").get().replace("主演：",""));
                m.setUpdTime(String.format("2017-%s",html.xpath("//p[2]/text()").get().replace("更新：","")));
                m.setStatus(html.xpath("//p[3]/text()").get().replace("状态：",""));
                m.setType(html.xpath("//p[4]/text()").get().replace("类型：",""));
                m.setUuid(UUID.randomUUID().toString());
                mvs.add(m);

                page.addTargetRequest(m.getOriginalUrl());
                Cache.map.put(m.getOriginalUrl(), m);
            }
        }
    }

    @Override
    public Site getSite() {
        return super.getSite();
    }
}
