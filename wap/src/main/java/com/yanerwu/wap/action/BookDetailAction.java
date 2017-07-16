package com.yanerwu.wap.action;

import com.alibaba.fastjson.JSON;
import com.yanerwu.common.Page;
import com.yanerwu.entity.BookDetail;
import com.yanerwu.wap.service.BookDetailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Zuz
 * @version 1.0
 * @Description BookDetail
 */
@Component
@RequestMapping(value = "/BookDetail")
public class BookDetailAction extends BaseAction {

    //默认多列排序,example: username desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS = null;
    //navTab rel="BookDetail"
    private static final String NAV_TAB_REL = "BookDetail";
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BookDetailService bookDetailService;

    private BookDetail bookDetail = new BookDetail();

    private boolean bool = false;
    private String message = DWZ_STATUS_SUCCESS;

    /**
     * 执行搜索
     *
     * @return
     */
    @RequestMapping(value = "/list.html")
    public String list(BookDetail bookDetail, Page page) {
        attribute();
        if (null == bookDetail.getBookId()) {
            logger.error("bookId 为空");
            return null;
        }
        if (StringUtils.isBlank(page.getOrderDirection())) {
            page.setOrderField("no");
            page.setOrderDirection("desc");
        }
        page = bookDetailService.findPage(bookDetail, page);
        request.setAttribute("page", page);
        return "/BookDetail/list";
    }

    @ResponseBody
    @RequestMapping(value = "/json-list.html")
    public String jsonList(BookDetail bookDetail, Page page) {
        String bookId = request.getParameter("bookId");
        if (null == bookDetail.getBookId()) {
            logger.error("bookId 为空");
            return null;
        }
        if (StringUtils.isBlank(page.getOrderDirection())) {
            page.setOrderField("no");
            page.setOrderDirection("desc");
        }
        String result = JSON.toJSONString(bookDetailService.findPage(bookDetail, page).getResult());
        return result;
    }

    @RequestMapping(value = "/info.html", method = RequestMethod.GET)
    public String infoShow(BookDetail bookDetail) {
        bookDetail = bookDetailService.findBookDetail(bookDetail);
        StringBuffer content = new StringBuffer();
        bookDetail.setContent(bookDetail.getContent().replace("    ", "　　　　"));
        for (String str : bookDetail.getContent().split("　　　　")) {
            content.append(String.format("<p class='content-padded'>&nbsp&nbsp&nbsp&nbsp%s</p>\n", str));
        }
        bookDetail.setContent(content.toString());
        request.setAttribute("entity", bookDetail);
        return "/BookDetail/info";
    }
}