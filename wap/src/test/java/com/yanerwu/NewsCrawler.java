package com.yanerwu;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.contentextractor.News;

/**
 * @Author Zuz
 * @Date 2017/7/17 16:36
 * @Description
 */
public class NewsCrawler{

    public static void main(String[] args) throws Exception {
        String url = "http://www.wdzj.com/news/pingtai/325624.html";
        News news = ContentExtractor.getNewsByUrl(url);
        System.out.println(news.getUrl());
        System.out.println(news.getTitle());
        System.out.println(news.getTime());
        System.out.println(news.getContent());
        System.out.println(news.getContentElement());
    }

}
