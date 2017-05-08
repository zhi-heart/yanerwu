package com.yanerwu.action;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.pipeline.YsdqPipeline;
import com.yanerwu.processor.YsdqProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Zuz
 * @Date 2017/5/8 11:25
 * @Description
 */
@Controller
public class CollectAction {

    private Logger log = LogManager.getLogger(getClass());

    @Autowired
    private DbUtilsTemplate yanerwuTemplate;

    @ResponseBody
    @RequestMapping("/collect.html")
    public String collect() {
        String urlStr = "http://www.yingshidaquan.cc/vod-show-id-1-order-addtime-p-%s.html";
        List<String> urls = new ArrayList<>();
        for (int i = 1; i <=1098 ; i++) {
//        for (int i = 1; i <= 1; i++) {
            urls.add(String.format(urlStr, i));
        }
        new Spider(new YsdqProcessor())
                .addUrl(urls.toArray(new String[urls.size()]))
                .addPipeline(new YsdqPipeline(yanerwuTemplate))
//                .setScheduler(new FileCacheQueueScheduler("/Users/Zuz/Desktop"))
                .thread(10)
                .run();
        return "true";
    }

    @ResponseBody
    @RequestMapping("/index.html")
    public String index() {
        return "index";
    }
}
