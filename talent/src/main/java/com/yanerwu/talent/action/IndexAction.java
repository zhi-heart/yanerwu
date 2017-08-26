package com.yanerwu.talent.action;

import com.yanerwu.common.BaseAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * 执行搜索
     *
     * @return
     */
    @RequestMapping(value = "/index.html")
    public String list() {

        return "index";
    }
}
