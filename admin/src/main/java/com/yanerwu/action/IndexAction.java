package com.yanerwu.action;

import com.alibaba.fastjson.JSON;
import com.yanerwu.entity.Uuser;
import org.apache.commons.lang3.SerializationUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @Author: Zuz
 * @Description:
 * @Date: 2017/4/27 19:00
 */
@Controller
public class IndexAction extends BaseAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/index.html")
    public String index() {
        logger.info(String.format("ip:%s", request.getRemoteAddr()));

        AttributePrincipal userPrincipal = (AttributePrincipal) request.getUserPrincipal();
        String userJsonStr = String.valueOf(userPrincipal.getAttributes().get("uuser"));
        Uuser user = JSON.parseObject(userJsonStr, Uuser.class);

        request.setAttribute("user", user);

        return "index";
    }

    @RequestMapping("/aaa.html")
    public String aaa() {
        logger.info(String.format("ip:%s", request.getRemoteAddr()));
        return "aaa";
    }

}
