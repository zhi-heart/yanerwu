package com.yanerwu.wap.action;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.common.Page;
import com.yanerwu.entity.BookSummary;
import com.yanerwu.wap.service.BookSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
