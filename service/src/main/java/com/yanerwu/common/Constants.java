package com.yanerwu.common;

/**
 * @Author Zuz
 * @Date 2017/7/27 11:30
 * @Description
 */
public class Constants {
    public final static String GOODS_TOP = "http://api.dataoke.com/index.php?r=Port/index&type=top100&appkey=tx07wo79m7&v=2&page=2";
    public final static String GOODS_PAOLIANG = "http://api.dataoke.com/index.php?r=Port/index&type=paoliang&appkey=tx07wo79m7&v=2&page=2";
    public final static String PUBLISH_GOODS_URL = "https://we.taobao.com/creation/post?template=item&from=draft";

    public final static String BOOK_COMMENT_LIST_URL = "http://m.qidian.com/majax/forum/getBookForumList?_csrfToken=CVvOKdS8H4MRhI0tR2URT6V3H9gzEn1jRy760prS&bookId=%s&tab=commall&pageNum=%s";
    public final static String BOOK_COMMENT_DETAIL_URL = "http://m.qidian.com/book/%s/thread/%s";


    public static abstract class BaseProxy {
        public static final String host = "http-dyn.abuyun.com";
        public static final int port = 9020;
        public static final String name = "HBC323020069XOBD";
        public static final String pwd = "6F32FE4CEDC13B98";
    }
}
