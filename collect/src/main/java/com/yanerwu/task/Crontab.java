package com.yanerwu.task;


import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.service.BiqugeService;
import com.yanerwu.utils.HttpClientUtil;
import com.yanerwu.utils.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

//    @Scheduled(cron = "0 0 6-23 * * ?")
//    public void collectBiqugeTop50() {
//        biqugeService.biqugeDetail(50);
//    }
//
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void collectBiqugeAll() {
//        biqugeService.biqugeDetail(9999);
//    }

    @Scheduled(fixedDelay = 99999999999L, initialDelay = 1000 * 30)
    public void collectBiqugeAll() {
        biqugeService.biqugeDetail(9999);
    }

    @Scheduled(cron = "8 8 15 ? * 1,2,3,4,5")
    public void stock() {
        String subject = "", content = "";
        try {
            double nowAmount = 0, lastAmount = 0;
            Pattern pattern = Pattern.compile(".*=\"(.*)\";");
            Matcher matcher = pattern.matcher(HttpClientUtil.doGet("http://hq.sinajs.cn/list=sz002230,sz300033"));
            while (matcher.find()) {
                String[] stock = matcher.group(1).split(",");
                String name = stock[0];
                Double nowPrice = Double.valueOf(stock[3]);
                Double lastPrice = Double.valueOf(stock[2]);

                switch (name) {
                    case "科大讯飞":
                        nowAmount += nowPrice * 5000;
                        lastAmount += lastPrice * 5000;
                        break;
                    case "同花顺":
                        nowAmount += nowPrice * 0;
                        lastAmount += lastPrice * 0;
                        break;
                }
            }
//            result = String.format("--------------</br>%.2f&nbsp;&nbsp;&nbsp;&nbsp;%.2f%%</br>--------------<br>", amount / 10000, ((amount / 188152) - 1) * 100);
//            result = String.format("------</br>%.2f%%%s</br>------", ((nowAmount / 188152) - 1) * 100, nowAmount >= lastAmount ? "↑" : "↓");
            subject = String.format("%s %.2f%% %s", nowAmount >= lastAmount ? "↑" : "↓", ((nowAmount / (37.13 * 5000)) - 1) * 100, nowAmount >= lastAmount ? "↑" : "↓");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String smtp = "smtp.163.com";
        String from = "zhi_heart@163.com";
        String to = "zhi_heart@aliyun.com";
        String username = "zhi_heart@163.com";
        String password = "3kNu8ZTzHFGWkE";
        Mail.sendAndCc(smtp, from, to, null, subject, content, username, password);
    }

}
