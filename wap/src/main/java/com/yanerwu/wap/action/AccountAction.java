package com.yanerwu.wap.action;

import com.alibaba.fastjson.JSON;
import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author Zuz
 * @Date 2017/7/15 12:52
 * @Description 阴阳师账号
 */
@Controller
public class AccountAction extends BaseAction {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DbUtilsTemplate bookTemplate;

    @ResponseBody
    @RequestMapping(value = "/get-action.html")
    public String getAccount() {
        String result = "{\"login_name\":\"\"}";
        String sql = "select * from yys_account where last_login_time<? order by login_name limit 1";
        List<Map<String, Object>> acctounts = bookTemplate.find(sql, DateUtils.getBackDate(-1));
        if (acctounts.size() > 0) {
            result = JSON.toJSONString(acctounts.get(0));
        }
        logger.info(result);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/ok.html")
    public String ok(String id) {
        logger.info("{} ok", id);
        String sql = "update yys_account set last_login_time=? where id=?";
        bookTemplate.update(sql, new Object[]{
                DateUtils.getNowTime(),
                id
        });
        return "true";
    }

}
