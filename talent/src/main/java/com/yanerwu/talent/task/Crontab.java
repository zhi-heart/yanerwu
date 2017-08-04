package com.yanerwu.talent.task;

import com.yanerwu.common.Constants;
import com.yanerwu.talent.service.GoodsTopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author Zuz
 * @Date 2017/7/27 11:32
 * @Description
 */
@Component
public class Crontab {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsTopService goodsTopService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void collect() {
        long l = System.currentTimeMillis();
//        logger.info("开始执行collectGoodsTop");
//        long l = System.currentTimeMillis();
//        goodsTopService.collectGoodsTop(Constants.GOODS_TOP);
//        logger.info("执行collectGoodsTop结束,耗时:{}毫秒", System.currentTimeMillis() - l);

        logger.info("开始执行collectGoodsPaoliang");
        l = System.currentTimeMillis();
        goodsTopService.collectGoodsTop(Constants.GOODS_PAOLIANG);
        logger.info("开始执行collectGoodsPaoliang,耗时:{}毫秒", System.currentTimeMillis() - l);

        logger.info("collectConver");
        l = System.currentTimeMillis();
        goodsTopService.collectConver();
        logger.info("collectConver,耗时:{}毫秒", System.currentTimeMillis() - l);
    }

    @PostConstruct
    public void init() {
        goodsTopService.init();
        goodsTopService.publishGoods();
    }

}
