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
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8") //
                .addHeader("Cache-Control", "max-age=0") //
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36")
                .setCharset("UTF-8");
    }
}
