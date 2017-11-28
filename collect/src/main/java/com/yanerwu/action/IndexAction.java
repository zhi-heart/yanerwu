package com.yanerwu.action;

import com.yanerwu.service.BiqugeService;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Zuz
 * @Date 2017/5/8 11:25
 * @Description
 */
@Controller
public class IndexAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BiqugeService biqugeService;

    @ResponseBody
    @RequestMapping(value = "/run.html", method = RequestMethod.GET)
    public String run(
            @RequestParam(defaultValue = "no") String limit
    ) {
        if (NumberUtils.isCreatable(limit)) {
            biqugeService.biqugeDetail(Integer.valueOf(limit));
            return "true";
        } else {
            return "false";
        }
    }

}
