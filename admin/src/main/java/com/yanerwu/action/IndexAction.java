package com.yanerwu.action;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Zuz
 * @Description:
 * @Date: 2017/4/27 19:00
 */
@Controller
public class IndexAction extends BaseAction{

    private Logger log = LogManager.getLogger(getClass());

    @RequestMapping("/index.html")
    public String index() {
        log.info(String.format("ip:%s",request.getRemoteAddr()));
        return "index";
    }

    @Login(action = Action.Skip)
    @RequestMapping("/aaa.html")
    public String aaa() {
        log.info(String.format("ip:%s",request.getRemoteAddr()));
        return "aaa";
    }

}
