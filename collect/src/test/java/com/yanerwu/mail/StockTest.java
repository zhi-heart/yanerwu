package com.yanerwu.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @Author Zuz
 * @Date 2017/9/11 15:16
 * @Description
 */
public class StockTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
//        StockTest s = new StockTest();
//        s.run();
        for (int i = 0; i < 100; i++) {
            System.out.println(new Random().nextInt(99) + 1);
        }
    }

    public void run() {
//        double totalAmount = 320000.0;
//        int kdxf = 256343;
//        int ths = 63657;
//        String subject = "";
//        StringBuffer content = new StringBuffer();
//        try {
//            double nowAmount = 0, lastAmount = 0;
//            Pattern pattern = Pattern.compile(".*=\"(.*)\";");
//            Matcher matcher = pattern.matcher(HttpClientUtil.doGet("http://hq.sinajs.cn/list=sz002230,sz300033"));
//            while (matcher.find()) {
//                String[] stock = matcher.group(1).split(",");
//                if(!DateUtils.getBackDate(0).equals(stock[stock.length - 3])){
//                    return;
//                }
//                String name = stock[0];
//                Double nowPrice = Double.valueOf(stock[3]);
//                Double lastPrice = Double.valueOf(stock[2]);
//
//                int dayAmount = 0;
//                int amount = 0;
//                switch (name) {
//                    case "科大讯飞":
//                        nowAmount += nowPrice * 5000;
//                        lastAmount += lastPrice * 5000;
//                        dayAmount = (int) ((nowPrice - lastPrice) * 5000);
//                        amount = (int) (nowPrice * 5000) - kdxf;
//                        break;
//                    case "同花顺":
//                        nowAmount += nowPrice * 1000;
//                        lastAmount += lastPrice * 1000;
//                        dayAmount = (int) ((nowPrice - lastPrice) * 1000);
//                        amount = (int) (nowPrice * 1000) - ths;
//                        break;
//                }
//
//                String c = String.format("%s -> %s %3.2f%% %s -> %+-5d -> %+-6d",
//                        "同花顺".equals(name) ? "T" : "K",
//                        nowPrice >= lastPrice ? "↑" : "↓",
//                        (nowPrice - lastPrice) / lastPrice * 100,
//                        nowPrice >= lastPrice ? "↑" : "↓",
//                        dayAmount,
//                        amount);
//                content.append(String.format("<p>%s</p>\n", c));
//            }
//
//            subject = String.format("%s %.2f%% %s -> %s -> %s",
//                    nowAmount >= lastAmount ? "↑" : "↓",
//                    ((nowAmount / totalAmount) - 1) * 100,
//                    nowAmount >= lastAmount ? "↑" : "↓",
//                    (int) (nowAmount - lastAmount),
//                    (int) (nowAmount - totalAmount));
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//        logger.info(subject);
//        String smtp = "smtp.163.com";
//        String from = "zhi_heart@163.com";
//        String to = "zhi_heart@aliyun.com";
//        String username = "zhi_heart@163.com";
//        String password = "3kNu8ZTzHFGWkE";
//        Mail.sendAndCc(smtp, from, to, null, subject, content.toString().replace(" ","&nbsp;"), username, password);
    }
}
