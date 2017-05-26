package com.wdzj;

import com.alibaba.fastjson.JSON;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author LiChangchun
 * @ClassName: LoginInterceptor
 * @Description: 后台管理拦截器
 */
public class AdminLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object obj) throws Exception {

        AdminInfoVo adminInfo = (AdminInfoVo) request.getSession().getAttribute("SESSION_ADMIN");

        if (null == adminInfo) {
            AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
            adminInfo = JSON.parseObject(principal.getAttributes().get("adminInfo").toString(), AdminInfoVo.class);

            if (null != adminInfo.getUserAuth() && null != adminInfo.getUserAuth().getAuthorities()) {
                request.setAttribute("authoritiesTree", adminInfo.getUserAuth().getAuthorities());
            }
            request.getSession().setAttribute("SESSION_ADMIN", adminInfo);
        }


        if (null != adminInfo) {
            //权限判断
            Map<String, List<Map<String, Object>>> appAuthorities = adminInfo.getAllAuth();
            if (null != appAuthorities) {
                String uri = String.format("%s/", request.getRequestURI());
                boolean isHas = false;
                if (appAuthorities.containsKey("authorities")) {
                    List<Map<String, Object>> authorities = appAuthorities.get("authorities");
                    if (null != authorities && authorities.size() > 0) {
                        for (Map<String, Object> authority : authorities) {
                            if (uri.contains(String.format("/%s/", authority.get("appAuthAction")))) {
                                isHas = true;
                                break;
                            }
                        }
                    }
                }

                UserAuth userAuth = adminInfo.getUserAuth();
                //权限校验
                if (null != userAuth) {
                    if (isHas) {
                        boolean isValid = false;
                        if (null != userAuth.getAuthorities()) {
                            List<Map<String, Object>> authorities = userAuth.getAuthorities();
                            if (null != authorities && authorities.size() > 0) {

                                for (Map<String, Object> authority : authorities) {
                                    if (authority.containsKey("children")) {
                                        List<Map<String, Object>> childrens = (List<Map<String, Object>>) authority.get("children");
                                        for (Map<String, Object> c : childrens) {
                                            if (uri.contains(String.format("/%s/", c.get("appAuthAction")))) {
                                                isValid = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (!isValid) {
                            response.setContentType("text/html;charset=UTF-8");
                            response.getWriter().write("对不起，您没有足够的权限！");
                            return false;
                        }
                    }
                    if (null != userAuth.getAuthoritiesTree()) {
                        request.setAttribute("authoritiesTree", adminInfo.getUserAuth().getAuthorities());
                    }
                } else {
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().write("对不起，您没有足够的权限！");
                    return false;
                }
            }
            if (obj instanceof HandlerMethod) {
                HandlerMethod method = (HandlerMethod) obj;
                StringBuffer params = new StringBuffer("{");
                Map<String, String[]> paramMap = request.getParameterMap();
                for (String name : paramMap.keySet()) {
                    StringBuffer value = new StringBuffer("[");
                    String[] values = paramMap.get(name);
                    if (values != null && values.length > 0) {
                        for (String v : values) {
                            value.append("\"").append(v).append("\",");
                        }
                        if (value.length() > 1) {
                            value.deleteCharAt(value.length() - 1);
                        }
                    }
                    value.append("]");
                    if (value.length() <= 50) {
                        params.append(name).append(":").append(value.toString()).append(",");
                    }
                }
                if (params.length() > 1) {
                    params.deleteCharAt(params.length() - 1);
                }
                params.append("}");
            }
            String userName = adminInfo.getAdminUserName();
            request.setAttribute("username", userName);
            return true;
        }
        //如果用户未登录，则重定向到登陆页
        if (null == adminInfo) {
            response.sendRedirect(request.getContextPath() + "/wdzjsyslogin");
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object obj, ModelAndView mav) throws Exception {
        if (obj instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) obj;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object obj, Exception exception)
            throws Exception {
    }


}