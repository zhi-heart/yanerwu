package com.yanerwu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.utils.HttpClientUtil;
import com.yanerwu.utils.Tools;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author Zuz
 * @Date 2017/12/7 15:18
 * @Description
 */
@Service
public class BlogService {

    static String sql = "insert into blog(uuid, title, content) values(?,?,?)";
    static String url = "http://10.132.0.18:9200/yuqing/_search";
    static String params = "{\"from\": 0, \"size\": 100, \"query\": {\"bool\": {\"must\": {\"bool\": {\"should\": [{\"match\": {\"type\": {\"query\": 0, \"type\": \"phrase\"} } }, {\"match\": {\"type\": {\"query\": 1, \"type\": \"phrase\"} } } ] } } } }, \"_source\": {\"includes\": [\"title\", \"content\"], \"excludes\": [] }, \"sort\": [{\"publishTime\": {\"order\": \"desc\"} } ] }";
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DbUtilsTemplate bookTemplate;

    public void collect() {
        HttpHost httpHost = new HttpHost("120.27.145.248", 8080);
        long l = System.currentTimeMillis();
        String s = HttpClientUtil.sendPostEntity(url, params, null, "utf-8",httpHost);
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
        logger.info("博客推送完毕,耗时%s", (System.currentTimeMillis() - l) / 1000);
    }
}
