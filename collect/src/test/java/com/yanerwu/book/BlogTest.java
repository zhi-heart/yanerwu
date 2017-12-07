package com.yanerwu.book;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yanerwu.base.BaseTest;
import com.yanerwu.utils.HttpClientUtil;
import com.yanerwu.utils.Tools;
import org.junit.Test;

import java.util.UUID;

/**
 * @Author Zuz
 * @Date 2017/12/7 13:53
 * @Description
 */
public class BlogTest extends BaseTest {
    static String sql = "insert into blog(uuid, title, content) values(?,?,?)";
    static String url = "http://10.132.0.18:9200/yuqing/_search";
    static String params = "{\"from\": 700, \"size\": 100, \"query\": {\"bool\": {\"must\": {\"bool\": {\"should\": [{\"match\": {\"type\": {\"query\": 0, \"type\": \"phrase\"} } }, {\"match\": {\"type\": {\"query\": 1, \"type\": \"phrase\"} } } ] } } } }, \"_source\": {\"includes\": [\"title\", \"content\"], \"excludes\": [] }, \"sort\": [{\"publishTime\": {\"order\": \"desc\"} } ] }";

    @Test
    public void t() {
        String s = HttpClientUtil.sendPostEntity(url, params, null, "utf-8");
        JSONArray hits = JSON.parseObject(s).getJSONObject("hits").getJSONArray("hits");
        for (int i = 0; i < hits.size(); i++) {
            JSONObject source = hits.getJSONObject(i).getJSONObject("_source");
            String uuid = UUID.randomUUID().toString().toLowerCase().replace("-", "");
            String title = source.getString("title");
            String content = source.getString("content");
            int update = bookTemplate.update(sql, new Object[]{
                    uuid,
                    title,
                    content
            });
            if (update > 0) {
                Tools.pushBaidu(String.format("http://book.yanerwu.com/blog/%s.html", uuid));
            }
        }
    }
}
