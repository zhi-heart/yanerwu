package com.yanerwu.common;

/**
 * @Author Zuz
 * @Date 2017/7/27 11:30
 * @Description
 */
public class Constants {
    public final static String GOODS_TOP = "http://api.dataoke.com/index.php?r=Port/index&type=top100&appkey=tx07wo79m7&v=2&page=2";
    public final static String GOODS_PAOLIANG = "http://api.dataoke.com/index.php?r=Port/index&type=paoliang&appkey=tx07wo79m7&v=2&page=2";
//    public final static String PUBLISH_GOODS_URL = "https://daren.taobao.com/content/publish.htm?spm=a21vj.8768172.0.0.67755b2aJhfyQe&push=true&formName=daren_item";
    public final static String PUBLISH_GOODS_URL = "https://daren.taobao.com/creation/post?spm=a2116r.8862366.0.0.6ff40992UryeSZ&template=item&from=draft";

    public final static String BOOK_COMMENT_LIST_URL = "http://m.qidian.com/majax/forum/getBookForumList?_csrfToken=CVvOKdS8H4MRhI0tR2URT6V3H9gzEn1jRy760prS&bookId=%s&tab=commall&pageNum=%s";
    public final static String BOOK_COMMENT_DETAIL_URL = "http://m.qidian.com/book/%s/thread/%s";

}
