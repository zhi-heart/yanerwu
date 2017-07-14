package com.yanerwu.wap.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * @Author Zuz
 * @Date 2017/7/14 13:55
 * @Description
 */
@Aspect
public class LogAopAction {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.yanerwu.wap.action..*.*(..))")
    private void controllerAspect() {
    }

    /**
     * 方法开始执行
     */
    @Before("controllerAspect()")
    public void doBefore() {
    }

    /**
     * 方法结束执行
     */
    @After("controllerAspect()")
    public void after() {
    }

    /**
     * 方法结束执行后的操作
     */
    @AfterReturning("controllerAspect()")
    public void doAfter() {
    }

    /**
     * 方法有异常时的操作
     */
    @AfterThrowing("controllerAspect()")
    public void doAfterThrow() {
    }


    /**
     * 方法执行
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //日志实体对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 拦截的实体类，就是当前正在执行的controller
        Object target = pjp.getTarget();
        // 拦截的方法名称。当前正在执行的方法
        String methodName = pjp.getSignature().getName();
        // 拦截的方法参数
        Object[] args = pjp.getArgs();
        // 拦截的放参数类型
        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Class[] parameterTypes = msig.getMethod().getParameterTypes();

        Object object = null;

        Method method = null;
        try {
            method = target.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        Enumeration<?> enumeration = request.getParameterNames();// 获取表单内所有元素
        if (enumeration.hasMoreElements()) {
            while (enumeration.hasMoreElements()) {
                String inputName = (String) enumeration.nextElement();// 获取元素名
                jsonObject.put(inputName, request.getParameter(inputName));
            }
        }

        long l = System.currentTimeMillis();
        object = pjp.proceed();
        double consumingTime = Double.valueOf((System.currentTimeMillis() - l)) / 1000;
        logger.info("{}({}) 耗时:{}秒 params:【{}】", target.getClass().getName(), method.getName(), consumingTime, jsonObject.toJSONString());

//        if (null != method) {
//            // 判断是否包含自定义的注解，说明一下这里的SystemLog就是我自己自定义的注解
//            if (method.isAnnotationPresent(SystemLog.class)) {
//                SystemLog systemlog = method.getAnnotation(SystemLog.class);
//                log.setModule(systemlog.module());
//                log.setMethod(systemlog.methods());
//                log.setLoginIp(getIp(request));
//                log.setActionUrl(request.getRequestURI());
//
//                try {
//                    object = pjp.proceed();
//                    log.setDescription("执行成功");
//                    log.setState((short) 1);
//                } catch (Throwable e) {
//                    // TODO Auto-generated catch block
//                    log.setDescription("执行失败");
//                    log.setState((short) -1);
//                }
//            } else {//没有包含注解
//                object = pjp.proceed();
//                log.setDescription("此操作不包含注解");
//                log.setState((short) 0);
//            }
//        } else { //不需要拦截直接执行
//            object = pjp.proceed();
//            log.setDescription("不需要拦截直接执行");
//            log.setState((short) 0);
//        }
        return object;
    }

    /**
     * 获取ip地址
     *
     * @param request
     * @return
     */
    private String getIp(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
}