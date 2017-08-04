package com.yanerwu.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @Author: Zuz
 * @Description:
 * @Date: 2017/4/27 19:00
 */
public class BaseAction {

    protected final String DWZ_STATUS_SUCCESS_CODE = "200";
    protected final String DWZ_STATUS_ERROR_CODE = "300";
    protected final String DEZ_STATUS_TIME_OUT_CODE = "301";
    protected final String DWZ_STATUS_SUCCESS = "操作成功";
    protected final String DWZ_STATUS_ERROR = "操作失败!";
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpSession session;
    private Logger logger = LogManager.getLogger(getClass());

    protected void attribute() {
        Enumeration<?> enumeration = request.getParameterNames();// 获取表单内所有元素
        if (enumeration.hasMoreElements()) {
            while (enumeration.hasMoreElements()) {
                String inputName = (String) enumeration.nextElement();// 获取元素名
                request.setAttribute(inputName, request.getParameter(inputName));
            }
            System.out.println();
        }
    }

    protected String retImessage(String statusCode, String message, boolean close) {
        return retImessage(statusCode, message, null, null, null, close ? "closeCurrent" : null, null);
    }

    /**
     * ajax 返回信息
     *
     * @param statusCode   状态码 成功:200,失败:300
     * @param message      输出信息
     * @param data         返回的数据
     * @param navTabId
     * @param rel
     * @param callbackType 返回类别 关闭并刷新:closeCurrent 跳转到forwardUrl callbackType="forward"
     * @param forwardUrl
     */
    protected String retImessage(String statusCode, String message, Object data, String navTabId, String rel, String callbackType, String forwardUrl) {
        JSONObject retJson = new JSONObject();
        retJson.put("statusCode", statusCode);
        retJson.put("message", message);
        retJson.put("navTabId", navTabId);
        retJson.put("rel", rel);
        retJson.put("callbackType", callbackType);
        retJson.put("forwardUrl", forwardUrl);
        retJson.put("data", data);

        return retJson.toJSONString();
    }
}
