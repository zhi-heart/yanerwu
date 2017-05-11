package com.yanerwu.common;

import us.codecraft.webmagic.Site;

/**
 * @Author Zuz
 * @Date 2017/5/7 23:05
 * @Description
 */
public class BaseProcessor {
    protected Site getSite() {
        return Site.me()
                .setSleepTime(1000) // 睡眠1秒
                .setTimeOut(1000 * 60) // 60秒超时
                .setRetryTimes(3) // 失败重试3次,仅只网络访问
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") //
                .addHeader("Cache-Control", "max-age=0") //
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0")
                .setCharset("UTF-8");
    }
}
