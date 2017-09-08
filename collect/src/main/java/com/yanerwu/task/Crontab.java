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

import java.util.Arrays;
import java.util.List;
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

    public static void main(String[] args) {
        List<Integer> records = Arrays.asList(new Integer[]{
                -2909,//moer
                206,//05
                -1487,
                -13181,
                4853,
                -5800,
                8257,
                -3241,
                5943,
                -35168,
                -4275,
                16594,
                -22571,
                -16173,
                30245,
                22560,
                46854
        });

        //截止到8月份
        Integer sum = records.stream()
                .mapToInt(a -> a.intValue())
                .sum();
        System.out.println(sum);

        //从
        System.out.println(348707 - sum);
    }

    @Scheduled(cron = "0 0 6-23 * * ?")
    public void collectBiqugeTop50() {
        biqugeService.biqugeDetail(50);
    }

//    @Scheduled(fixedDelay = 99999999999L, initialDelay = 1000 * 30)
//    public void collectBiqugeAll() {
//        biqugeService.biqugeDetail(9999);
//    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void collectBiqugeAll() {
        biqugeService.biqugeDetail(9999);
    }

    @Scheduled(cron = "8 8 15 ? * 1,2,3,4,5")
    public void stock() {
        double c=320000.0;
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
                        nowAmount += nowPrice * 1000;
                        lastAmount += lastPrice * 1000;
                        break;
                }
            }

            subject = String.format("%s %.2f%% %s -> %s -> %s",
                    nowAmount >= lastAmount ? "↑" : "↓",
                    ((nowAmount / c) - 1) * 100,
                    nowAmount >= lastAmount ? "↑" : "↓",
                    (int) (nowAmount - lastAmount),
                    (int) (nowAmount - c));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        logger.info(subject);
        String smtp = "smtp.163.com";
        String from = "zhi_heart@163.com";
        String to = "zhi_heart@aliyun.com";
        String username = "zhi_heart@163.com";
        String password = "3kNu8ZTzHFGWkE";
        Mail.sendAndCc(smtp, from, to, null, subject, content, username, password);
    }

}
