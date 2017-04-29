package com.yanerwu.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Zuz
 * @Description:
 * @Date: 2017/4/28 18:04
 */
@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction {

    private Logger log = LogManager.getLogger(getClass());

    @RequestMapping("/list.html")
    public String userList() {
        log.info(String.format("ip:%s", request.getRemoteAddr()));
        return "user/list";
    }
}
