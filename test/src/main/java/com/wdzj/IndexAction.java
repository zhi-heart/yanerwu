package com.wdzj;

import com.alibaba.fastjson.JSON;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Zuz
 * @Date 2017/5/25 17:19
 * @Description
 */
@Controller
public class IndexAction {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected HttpServletRequest request;

    @RequestMapping("/index.html")
    public String index() {

        AdminInfoVo adminInfo = (AdminInfoVo) request.getSession().getAttribute("SESSION_ADMIN");

        if (null == adminInfo) {
            AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
            adminInfo = JSON.parseObject(principal.getAttributes().get("adminInfo").toString(), AdminInfoVo.class);

            if (null != adminInfo.getUserAuth() && null != adminInfo.getUserAuth().getAuthorities()) {
                request.setAttribute("authoritiesTree", adminInfo.getUserAuth().getAuthorities());
            }
            request.getSession().setAttribute("SESSION_ADMIN", adminInfo);
        }

        if (null != adminInfo.getUserAuth() && null != adminInfo.getUserAuth().getAuthorities()) {
            request.setAttribute("authoritiesTree", adminInfo.getUserAuth().getAuthorities());
        }

        return "leftmenu";
    }


}
