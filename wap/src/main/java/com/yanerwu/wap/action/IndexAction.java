package com.yanerwu.wap.action;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.common.Page;
import com.yanerwu.entity.BookSummary;
import com.yanerwu.utils.HttpClientUtil;
import com.yanerwu.wap.service.BookSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Zuz
 * @Date 2017/5/8 11:25
 * @Description
 */
@Controller
public class IndexAction extends BaseAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DbUtilsTemplate bookTemplate;
    @Autowired
    private BookSummaryService bookSummaryService;

    @RequestMapping("/index.html")
    public String index(BookSummary bookSummary, Page page) {
        request.setAttribute("page", bookSummaryService.findPage(bookSummary, page));
        return "index";
    }

    @ResponseBody
    @RequestMapping("/s.html")
    public String s(@RequestParam(defaultValue = "002230") String s) {
        String result = "";
        try {
            String url = String.format("http://hq.sinajs.cn/list=%s%s", "6".equals(s.substring(0, 1)) ? "sh" : "sz", s);
            String[] split = HttpClientUtil.doGet(url).split(",");
            double last = Double.valueOf(split[2]) * 100;
            double now = Double.valueOf(split[3]) * 100;
            double d = (now - last) / last;
            d = new BigDecimal(d * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            result = String.format("--------------\n%s %s\n--------------\n", split[31], d);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/a.html")
    public String a() {
        String result = "";
        try {
            double amount = 0;
            Pattern pattern = Pattern.compile(".*=\"(.*)\";");
            Matcher matcher = pattern.matcher(HttpClientUtil.doGet("http://hq.sinajs.cn/list=sz002230,sz300033"));
            while (matcher.find()) {
                String[] stock = matcher.group(1).split(",");
                String name = stock[0];
                Double price = Double.valueOf(stock[3]);
                switch (name) {
                    case "科大讯飞":
                        amount += price * 3000;
                        break;
                    case "同花顺":
                        amount += price * 1900;
                        break;
                }
            }

            result = String.format("--------------\n%.2f\t%.2f%%\n--------------\n", amount / 10000, ((amount / 188152) - 1)*100);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }
}
