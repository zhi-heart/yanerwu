package com.yanerwu.task;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.common.es.ElasticSearchHelper;
import com.yanerwu.entity.MvList;
import com.yanerwu.pipeline.YsdqPipeline;
import com.yanerwu.processor.YsdqProcessor;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Zuz
 * @Date 2017/5/8 00:41
 * @Description
 */
@Component
public class Crontab {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DbUtilsTemplate yanerwuTemplate;
    @Autowired
    private ElasticSearchHelper elasticSearchHelper;

    public static void main(String[] args) {
        System.out.println("尼古拉斯·凯奇,汤米·里·琼斯,肖恩·扬,塞克·克杂特".replaceAll("\\pP|\\pS", ""));
    }

    //    @Scheduled(cron = "0 0 * * * ?")
//    @Scheduled(fixedDelay = 1000 * 60 *30)
    public void synoym() {
        String sql = "select * from mv_list order by id desc limit 0,1000";
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        List<MvList> mvLists = yanerwuTemplate.find(MvList.class, sql, processor);

        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(MvList.class,
                "id", "name", "status", "type", "area", "actor", "summary", "original_url", "download_url", "pub_time", "upd_time");

        Map<String, Object> map = new HashMap<>();
        for (MvList m : mvLists) {
            map.put(String.valueOf(m.getId()), JSON.toJSONString(m, filter));
        }
        elasticSearchHelper.bulkIndex("movie", "base", map);
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    public void collectMovie() {
        String urlStr = "http://www.yingshidaquan.cc/vod-show-id-1-order-addtime-p-%s.html";
        List<String> urls = new ArrayList<>();
        for (int i = 1; i <= 1105; i++) {
            urls.add(String.format(urlStr, i));
        }

        Spider.create(new YsdqProcessor())
                .addUrl(urls.toArray(new String[urls.size()]))
                .addPipeline(new YsdqPipeline(yanerwuTemplate))
//                .setScheduler(new FileCacheQueueScheduler("/Users/Zuz/Desktop"))
                .thread(5)
                .run();
    }
}
