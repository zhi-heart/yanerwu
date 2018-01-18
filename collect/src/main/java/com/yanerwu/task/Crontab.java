package com.yanerwu.task;


import com.yanerwu.service.BiqugeService;
import com.yanerwu.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
        //银证流水
        //25000+376160.33-4841.93+6800-590+12-12-121424.62+209959.75=491063.53

        //2018 成本
        double cost = 59.14 * 8000;

        //2018-01 价格
        double nowAmount = 59.14 * 8000;

        List<Integer> records = Arrays.asList(
                -2500,  //moer
                206,    //2016.05
                -1487,
                -13181,
                4853,
                -5800,
                8257,
                -3241,
                5943,
                -35168, //2016.01
                -4275,
                16594,
                -22571,
                -16173,
                30245,
                22560,
                46854,
                -6042,
                2487,
                76012,
                -32171,//华泰12
                -17920 //平安12
        );

        Integer sum = records.stream()
                .mapToInt(a -> a.intValue())
                .sum();

        HashMap<String, Integer> debts = new HashMap<String, Integer>(10) {{
            put("招行", 35737);
            put("招联", 51185);
            put("浦发", 46017);
            put("中信", 7800);
            put("平安", 14480);
            put("质押", 210000);
            put("民生", 6148);
        }};

        int debtSum = debts.values().stream()
                .mapToInt(a -> a.intValue())
                .sum();
        //归总
        System.out.println(String.format("总值:%s 成本:%s 历史盈利:%s 2018盈利:%s 负债:%s 净值:%s", nowAmount, cost, sum, nowAmount - cost, debtSum, nowAmount - debtSum));
    }

    @Scheduled(cron = "0 0 6-23 * * ?")
    public void collectBiqugeTop50() {
        biqugeService.biqugeDetail(50);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void collectBiqugeAll() {
        biqugeService.biqugeDetail(9999);
    }

    @Scheduled(cron = "0 40 17 * * ?")
    public void collectBlog() {
        blogService.collect();
    }

}
