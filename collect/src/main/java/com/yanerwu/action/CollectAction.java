package com.yanerwu.action;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.service.BiqugeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
