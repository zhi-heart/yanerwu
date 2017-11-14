package com.yanerwu.talent.action;

import com.yanerwu.common.BaseAction;
import com.yanerwu.common.Constants;
import com.yanerwu.talent.service.GoodsTopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Zuz
 * @Date 2017/8/11 11:39
 * @Description
 */
@Controller
public class IndexAction extends BaseAction {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsTopService goodsTopService;

    /**
     * 执行搜索
     *
     * @return
     */
    @RequestMapping(value = "/index.html")
    public String list() {

        return "index";
    }

    /**
     * 手动触发
     *
     * @return
     */
    @RequestMapping(value = "/run.html")
    public String run() {
        new Thread(() -> {
            long l = System.currentTimeMillis();
            logger.info("手动触发 开始执行collectGoodsTop");
            goodsTopService.collectGoodsTop(Constants.GOODS_TOP);
            logger.info("手动触发 执行collectGoodsTop结束,耗时:{}毫秒", System.currentTimeMillis() - l);

            logger.info("手动触发 collectConver");
            l = System.currentTimeMillis();
            goodsTopService.collectConver();
            logger.info("手动触发 collectConver,耗时:{}毫秒", System.currentTimeMillis() - l);
        }).start();
        request.setAttribute("result", "手动触发成功!");
        return "index";
    }
}
