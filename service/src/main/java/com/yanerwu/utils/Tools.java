package com.yanerwu.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Zuz
 * @Description:
 * @Date: 2017/4/27 17:26
 */
public class Tools {
    private static final Logger logger = Logger.getLogger(Tools.class);
    private static String SPACE = "   ";

    public static final Set<String> blackSet = new HashSet();


    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String delHtmlText(String inputString) {
        String htmlStr = inputString.trim(); // 含html标签的字符串
        String textStr = "";

        if (StringUtils.isBlank(htmlStr)) {
            return textStr;
        }

        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Pattern p_html1;
        Matcher m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            String regEx_html1 = "\\[[^\\]]*\\]";


            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            htmlStr = p_html1.matcher(htmlStr).replaceAll("");

			/* 空格 —— */
            // p_html = Pattern.compile("\\ ", Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = htmlStr.replaceAll("&nbsp;", " ");
            htmlStr = htmlStr.replaceAll(" ", " ");
            htmlStr = htmlStr.replaceAll("\r|\n|\t", "").replaceAll("\"", "“").replaceAll("\'", "‘");
            textStr = htmlStr.trim();

        } catch (Exception e) {
        }
        return textStr;
    }

    /**
     * 除(font)标签,其他全去掉
     *
     * @param inputString
     * @return
     */
    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<(?!font|/font).*?>"; // 定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符串
    }

    public static String getFieldName(Class<?> cls) {
        return getFieldName(cls, new String[]{});
    }

    /**
     * 获取类所有属性名
     *
     * @param cls
     * @param exclude
     * @return
     */
    public static String getFieldName(Class<?> cls, String... exclude) {
        StringBuffer sb = new StringBuffer();
        List<String> excDic = new ArrayList();
        if (null != exclude && exclude.length > 0) {
            excDic = Arrays.asList(exclude);
        }
        java.lang.reflect.Field[] fields = cls.getDeclaredFields();
        for (java.lang.reflect.Field f : fields) {
            String name = f.getName();
            if ("serialVersionUID".equals(name)) {
                continue;
            }
            if (excDic.contains(name)) {
                continue;
            }
            sb.append(String.format("%s,", name));
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    public static Integer[] strArrayToIntArray(String rawStr) {
        String[] strs = rawStr.split(",");
        Integer[] is = new Integer[strs.length];
        for (int i = 0; i < strs.length; i++) {
            is[i] = Integer.valueOf(strs[i]);
        }
        return is;
    }

    /**
     * 检测数组是否包含某个或多个元素
     *
     * @param a
     * @param cs
     * @return
     */
    public static <T> boolean arraysContains(T[] a, T... cs) {
        List<T> list = Arrays.asList(a);
        for (T c : cs) {
            if (list.contains(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * list转string 逗号分隔
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> String listToString(List<T> list) {
        StringBuffer sb = null;
        try {
            if (null == list || list.size() < 1) {
                return "";
            }
            sb = new StringBuffer();
            for (T t : list) {
                sb.append(String.valueOf(t));
                sb.append(",");
            }
            sb.delete(sb.length() - 1, sb.length());
        } catch (Exception e) {
            logger.error(e);
        }
        return sb.toString();
    }

    public static long getObjectBytes(Object obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ooo = new ObjectOutputStream(baos);
            ooo.writeObject(obj);
            byte[] bys = baos.toByteArray();
            return (bys.length - 4) / 1024;
        } catch (IOException e) {
            logger.error("", e);
        }
        return -1;
    }

    /**
     * 深度克隆 必须实现 Serializable
     *
     * @param obj
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj) {
        T clonedObj = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            clonedObj = (T) ois.readObject();
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clonedObj;
    }

    public static String toString(Object[] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(String.valueOf(a[i]));
            if (i == iMax)
                return b.toString();
            b.append(",");
        }
    }

}
