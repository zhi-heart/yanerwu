package com.yanerwu.action;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.service.BiqugeService;
import com.yanerwu.service.InformationService;
import com.yanerwu.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author Zuz
 * @Date 2017/5/8 11:25
 * @Description
 */
@Controller
public class CollectAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DbUtilsTemplate yanerwuTemplate;
    @Autowired
    private DbUtilsTemplate bookTemplate;
    @Autowired
    private BiqugeService biqugeService;
    @Autowired
    private InformationService informationService;

    @ResponseBody
    @RequestMapping("/book.html")
    public String book(String name) {
        if (StringUtils.isBlank(name)) {
            biqugeService.biqugeDetailByName();
        }else{
            biqugeService.biqugeDetailByName(name);
        }
        return name;
    }

    @ResponseBody
    @RequestMapping("/index.html")
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("/i.html")
    public String information(String startDate,String endDate) {
        logger.info("=================> {} -> {}", startDate, endDate);
        List<String> dates = DateUtils.getDatesBetweenTwoDay(startDate, endDate);
        for (int i = 0; i < dates.size() - 1; i++) {
            String lastDate = dates.get(i);
            String nowDate = dates.get(i + 1);
            long l = System.currentTimeMillis();
            informationService.run(lastDate, nowDate);
            long le = System.currentTimeMillis();
            logger.info("{}处理结束,耗时{}秒", lastDate, (le - l) / 1000);
        }
        return "true";
    }

}
