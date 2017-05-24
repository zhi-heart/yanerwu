//package com.yanerwu.action;
//
//import com.alibaba.fastjson.JSON;
//import com.yanerwu.common.Page;
//import com.yanerwu.service.MovieService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * @Author Zuz
// * @Date 2017/5/15 17:07
// * @Description
// */
//@Controller
//public class MovieAction extends BaseAction{
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private MovieService movieService;
//
//    @RequestMapping("/movie-search.html")
//    public String movieSearch(Page page, String searchKey) {
//        StringBuffer sb = new StringBuffer();
//
//        page.setNumPerPage(10);
//
//        movieService.findPage(page, searchKey);
//
//        request.setAttribute("searchKey", searchKey);
//
//        System.out.println(JSON.toJSONString(page.getResult()));
//
//        return "Movie/list";
//    }
//
//}
