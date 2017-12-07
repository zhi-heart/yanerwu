package com.yanerwu.task;


import com.yanerwu.service.BiqugeService;
import com.yanerwu.service.BlogService;
import com.yanerwu.utils.DateUtils;
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
    private BiqugeService biqugeService;
    @Autowired
    private BlogService blogService;

    public static void main(String[] args) {
        List<Integer> records = Arrays.asList(
                -2500,//moer
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
                46854,
                -6042,
                2487
        );

        //8月
//        int total = 285050 + 63657 + 9035;
        //11月
        int total = 332320 + 51680 + 8600;

        //截止到8月份
        Integer sum = records.stream()
                .mapToInt(a -> a.intValue())
                .sum();

        //主要负债 9.14
//        List<Integer> debts = Arrays.asList(
//                50000,
//                73534,
//                48861,
//                66241,
//                8800
//        );
        //主要负债 11.14
        List<Integer> debts = Arrays.asList(
                20368,//	e招贷
                38544,//	招行信用卡
                10000,//	京东金融
                75611,//	浦发
                47000,//	网商银行
                73559,//	招联金融
                16333,//	平安信用卡
                36000,//	YG
                1400//	花呗
        );

        int debtSum = debts.stream()
                .mapToInt(a -> a.intValue())
                .sum();
        //归总
        System.out.println(String.format("总值:%s 成本:%s 盈利:%s 负债:%s 净值:%s", total, total - sum, sum, debtSum, total - debtSum));

//        浦发			22428
//        招行			24794
//        平安			8862
//        网商			4964
//        招联			7273
//        22428+20000+4964+7273=54665  8月要还的

//        new Crontab().stock();
    }

    @Scheduled(cron = "0 0 6-23 * * ?")
    public void collectBiqugeTop50() {
        biqugeService.biqugeDetail(50);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void collectBiqugeAll() {
        biqugeService.biqugeDetail(9999);
    }

    @Scheduled(cron = "0 0 6 * * ?")
    public void collectBlog(){
        blogService.collect();
    }

//    @Scheduled(cron = "0 0 15 ? * 1,2,3,4,5")
    public void stock() {
        double totalAmount = 320000.0;
        String subject = "";
        StringBuffer content = new StringBuffer();
        try {
            double nowAmount = 0, lastAmount = 0;
            Pattern pattern = Pattern.compile(".*=\"(.*)\";");
            Matcher matcher = pattern.matcher(HttpClientUtil.doGet("http://hq.sinajs.cn/list=sz002230"));
            while (matcher.find()) {
                String[] stock = matcher.group(1).split(",");
                if (!DateUtils.getBackDate(0).equals(stock[stock.length - 3])) {
                    return;
                }
                Double nowPrice = Double.valueOf(stock[3]);
                Double lastPrice = Double.valueOf(stock[2]);

                nowAmount += nowPrice * 6200;
                lastAmount += lastPrice * 6200;
                int dayAmount = (int) ((nowPrice - lastPrice) * 6200);
                int amount = (int) (nowPrice * 6200 - totalAmount);

                String c = String.format("%s -> %s %3.2f%% %s -> %+-5d -> %+-6d",
                        "K",
                        nowPrice >= lastPrice ? "↑" : "↓",
                        (nowPrice - lastPrice) / lastPrice * 100,
                        nowPrice >= lastPrice ? "↑" : "↓",
                        dayAmount,
                        amount);
                content.append(String.format("<p>%s</p>\n", c));
            }

            subject = String.format("%s %.2f%% %s -> %s -> %s",
                    nowAmount >= lastAmount ? "↑" : "↓",
                    ((nowAmount / totalAmount) - 1) * 100,
                    nowAmount >= lastAmount ? "↑" : "↓",
                    (int) (nowAmount - lastAmount),
                    (int) (nowAmount - totalAmount));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        logger.info(subject);
        logger.info(content.toString());
        String smtp = "smtp.163.com";
        String from = "zhi_heart@163.com";
        String to = "zhi_heart@aliyun.com";
        String username = "zhi_heart@163.com";
        String password = "3kNu8ZTzHFGWkE";
        Mail.sendAndCc(smtp, from, to, null, subject, content.toString().replace(" ", "&nbsp;"), username, password);
    }
}
