package com.yanerwu.wap.action;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.contentextractor.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Zuz
 * @Date 2017/7/15 12:52
 * @Description 正文提取
 */
@Controller
public class ExtractorAction extends BaseAction {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/extractor.html")
    public String extractor(String url) throws Exception {
        News news = ContentExtractor.getNewsByUrl(url);
        request.setAttribute("title", news.getTitle());
        request.setAttribute("content",news.getContentElement());
        return "news";
    }
}
