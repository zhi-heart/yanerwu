package com.yanerwu.wap.action;

import com.yanerwu.common.Page;
import com.yanerwu.entity.BookSummary;
import com.yanerwu.wap.service.BookSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Zuz
 * @version 1.0
 * @Description 列表
 */
@Component
@RequestMapping(value = "/BookSummary")
public class BookSummaryAction extends BaseAction {

    //默认多列排序,example: username desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS = null;
    //navTab rel="BookSummary"
    private static final String NAV_TAB_REL = "BookSummary";
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BookSummaryService bookSummaryService;

    private BookSummary bookSummary = new BookSummary();

    private boolean bool = false;
    private String message = DWZ_STATUS_SUCCESS;

    /**
     * 执行搜索
     *
     * @return
     */
    @RequestMapping(value = "/list.html")
    public String list(BookSummary bookSummary, Page page) {
        attribute();
        page = bookSummaryService.findPage(bookSummary, page);
        request.setAttribute("page", page);
        return "BookSummary/list";
    }

    /**
     * 进入正文
     *
     * @return
     */
    @RequestMapping(value = "/info.html", method = RequestMethod.GET)
    public String infoShow(BookSummary bookSummary) {
        bookSummary = bookSummaryService.getById(bookSummary);
        request.setAttribute("entity", bookSummary);
        return "BookSummary/info";
    }
}