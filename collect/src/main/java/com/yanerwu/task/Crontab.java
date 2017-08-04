package com.yanerwu.task;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.common.es.ElasticSearchHelper;
import com.yanerwu.entity.MvList;
import com.yanerwu.pipeline.YsdqPipeline;
import com.yanerwu.processor.YsdqProcessor;
import com.yanerwu.service.BiqugeService;
import com.yanerwu.utils.HttpClientUtil;
import com.yanerwu.utils.Mail;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Zuz
 * @Date 2017/5/8 00:41
 * @Description
 */
@Component
public class Crontab {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    BiqugeService biqugeService;
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

    //    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
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

    @Scheduled(cron = "0 0 6-23 * * ?")
    public void collectBiqugeTop50() {
        biqugeService.biqugeDetail(50);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void collectBiqugeAll() {
        biqugeService.biqugeDetail(9999);
    }

    @Scheduled(cron = "8 38 11 ? * 2,3,4,5,6")
    @Scheduled(cron = "8 8 15 ? * 2,3,4,5,6")
    public void stock(){
        String result = "";
        try {
            double amount = 0;
            Pattern pattern = Pattern.compile(".*=\"(.*)\";");
            Matcher matcher = pattern.matcher(HttpClientUtil.doGet("http://hq.sinajs.cn/list=sz002230,sz300033"));
            while (matcher.find()) {
                String[] stock = matcher.group(1).split(",");
                String name = stock[0];
                Double price = Double.valueOf(stock[3]);
                switch (name) {
                    case "科大讯飞":
                        amount += price * 3000;
                        break;
                    case "同花顺":
                        amount += price * 1900;
                        break;
                }
            }

            result = String.format("--------------</br>%.2f&nbsp;&nbsp;&nbsp;&nbsp;%.2f%%</br>--------------<br>", amount / 10000, ((amount / 188152) - 1) * 100);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String subject = "账户详情";
        String content = result;
        String smtp = "smtp.163.com";
        String from = "zhi_heart@163.com";
        String to = "zhi_heart@aliyun.com";
        String username = "zhi_heart@163.com";
        String password = "3kNu8ZTzHFGWkE";
        Mail.sendAndCc(smtp, from, to, null, subject, content, username, password);
    }

}
