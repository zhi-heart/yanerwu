//package com.yanerwu.action;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @Author: Zuz
// * @Description:
// * @Date: 2017/4/27 19:00
// */
//public class BaseAction {
//
//    @Autowired
//    protected HttpServletRequest request;
//    @Autowired
//    protected HttpServletResponse response;
//
//    public BaseAction() {
//        String path = request.getContextPath();
//        String basePath = String.format("%s://%s:%s%s",
//                request.getScheme(), request.getServerName(), request.getServerPort(), path);
//        request.setAttribute("path", path);
//        request.setAttribute("basePath", basePath);
//    }
//}
