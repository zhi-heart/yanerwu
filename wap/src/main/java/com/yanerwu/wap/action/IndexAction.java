package com.yanerwu.wap.action;

import com.alibaba.fastjson.JSON;
import com.yanerwu.common.Page;
import com.yanerwu.entity.BookDetail;
import com.yanerwu.entity.BookSummary;
import com.yanerwu.wap.service.BlogService;
import com.yanerwu.wap.service.BookDetailService;
import com.yanerwu.wap.service.BookSummaryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Zuz
 * @Date 2017/5/8 11:25
 * @Description
 */
@Controller
public class IndexAction extends BaseAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BookSummaryService bookSummaryService;
    @Autowired
    private BookDetailService bookDetailService;
    @Autowired
    private BlogService blogService;

    /**
     * 首页
     *
     * @param bookSummary
     * @param page
     * @return
     */
    @RequestMapping("/index.html")
    public String index(BookSummary bookSummary, Page page) {
        request.setAttribute("page", bookSummaryService.findPage(bookSummary, page));
        return "index";
    }

    /**
     * 列表页
     *
     * @param bookId
     * @return
     */
    @RequestMapping(value = "/book/{bookId}.html", method = RequestMethod.GET)
    public String infoShow(
            @PathVariable("bookId") Long bookId,
            Page page,
            @RequestParam(defaultValue = "no") String orderField,
            @RequestParam(defaultValue = "desc") String orderDirection
    ) {
        attribute();

        BookDetail bd = new BookDetail();
        bd.setBookId(bookId);

        page.setOrderField(orderField);
        page.setOrderDirection(orderDirection);

        bookDetailService.findPage(bd, page);

        //获取书本信息
        BookSummary bookSummary = bookSummaryService.getById(new BookSummary(bookId));
        request.setAttribute("bookName", bookSummary.getName());

        request.setAttribute("page", page);
        return "/BookDetail/list";
    }

    /**
     * 列表页 - 无线滚动翻页
     *
     * @param bookDetail
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/book/json-list.html")
    public String jsonList(BookDetail bookDetail, Page page) {
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

    /**
     * 内容页
     *
     * @param bookId
     * @param no
     */
    @RequestMapping(value = "/book/{bookId}/{no}.html")
    public String info(
            @PathVariable("bookId") Long bookId,
            @PathVariable("no") Integer no
    ) {
        BookDetail bookDetail = new BookDetail();
        bookDetail.setBookId(bookId);
        bookDetail.setNo(no);

        bookDetail = bookDetailService.findBookDetail(bookDetail);

        if (StringUtils.isBlank(bookDetail.getContent())) {
            bookDetail.setContent("暂无更新,请稍后再试,么么哒");
        }
        request.setAttribute("entity", bookDetail);

        //获取书本信息
        BookSummary bookSummary = bookSummaryService.getById(new BookSummary(bookId));
        request.setAttribute("bookName", bookSummary.getName());

        return "/BookDetail/info";
    }

    @RequestMapping(value = "/blog/{uuid}.html")
    public String info(
            @PathVariable("uuid") String uuid
    ) {
        request.setAttribute("entity", blogService.getBlogByUuid(uuid));
        return "/Blog/info";
    }
}
