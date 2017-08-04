package com.yanerwu.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Author: Zuz
 * @Description:
 * @Date: 2017/4/27 17:26
 */
public class Tools {
    private static final Logger logger = LogManager.getLogger(Tools.class);
    private static String SPACE = "   ";

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

    /**
     * 深度克隆 必须实现 Serializable
     *
     * @param obj
     * @param <T>
     * @return
     */
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

    /**
     * 可以用于判断 Map,Collection,String,Array是否为空
     *
     * @param o
     * @return
     */
    public static boolean isEmpty(Object o) {
        if (o == null) return true;

        if (o instanceof String) {
            if (((String) o).length() == 0) {
                return true;
            }
        } else if (o instanceof Collection) {
            if (((Collection) o).isEmpty()) {
                return true;
            }
        } else if (o.getClass().isArray()) {
            if (Array.getLength(o) == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            if (((Map) o).isEmpty()) {
                return true;
            }
        } else {
            return false;
        }

        return false;
    }

    /**
     * 可以用于判断 Map,Collection,String,Array是否不为空
     *
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Object c) throws IllegalArgumentException {
        return !isEmpty(c);
    }

    /**
     * <p>
     * URLDecoder 解码地址
     * </p>
     *
     * @param url 解码地址
     * @return
     */
    public static String decodeURL(String url, String encoding) {
        if (url == null) {
            return null;
        }
        String retUrl = url;

        try {
            for (int i = 0; i < 3; i++) {
                retUrl = URLDecoder.decode(retUrl, encoding);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return retUrl;
    }

    /**
     * url 参数值获取
     *
     * @param url
     * @param name
     * @return
     */
    public static String getUrlParamsByName(String url, String name) {
        String retStr = "";
        if (StringUtils.isNotBlank(url) && url.contains("?")) {
            Map<String, String> map = new HashMap<>();
            String[] paramsArrays = url.split("\\?")[1].split("&");
            for (String pstr : paramsArrays) {
                String[] s = pstr.split("=");
                map.put(s[0], decodeURL(s[1], "utf-8"));
            }
            retStr = map.get(name);
        }
        return retStr;
    }

    public static String listToStr(List<?> list, String s) {
        StringBuffer sb = new StringBuffer();
        try {
            for (Object l : list) {
                sb.append(sb);
                sb.append(",");
            }
            if (list.size() > 0) {
                sb.delete(sb.length() - 1, sb.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String replaceToNull(String str, String... repStr) {
        String result = str;
        for (String r : repStr) {
            result.replace(r, "");
        }
        return str;
    }

    /**
     * 利用MD5进行加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     * @throws NoSuchAlgorithmException     没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException
     */
    public static String encoderMd5(String str) {
        String newstr = str;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newstr;
    }

    /**
     * 删除html标签
     *
     * @param inputString
     * @return
     */
    public static String delHtmlText(String inputString) {
        if (StringUtils.isBlank(inputString)) {
            return "";
        }
        String htmlStr = inputString.trim(); // 含html标签的字符串
        String textStr = "";

        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Pattern p_html1;
        java.util.regex.Matcher m_html;
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

}
