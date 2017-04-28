package com.yanerwu.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanConverter {

    /**
     * 将javaBean转换成Map
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    public static Map<String, String> toMap(Object javaBean) {
        Map<String, String> result = new HashMap();
        Method[] methods = javaBean.getClass().getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(javaBean, (Object[]) null);
                    result.put(field, null == value ? "" : value.toString());
                }
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 将map转换成Javabean
     *
     * @param javabean javaBean
     * @param data     map数据
     */
    public static Object toJavaBean(Object javabean, Map<String, Object> data) {
        Method[] methods = javabean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            try {
                if (method.getName().startsWith("set")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    method.invoke(javabean, new Object[]{data.get(field)});
                }
            } catch (Exception e) {
            }
        }

        return javabean;
    }

}
