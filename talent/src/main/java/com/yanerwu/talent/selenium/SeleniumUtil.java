package com.yanerwu.talent.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author Zuz
 * @Date 2017/7/28 14:38
 * @Description
 */
public class SeleniumUtil {
    private static SeleniumUtil seleniumUtil = null;
    private ConcurrentMap<Long, WebDriver> driverMap = new ConcurrentHashMap<>();
    private WebDriver driver = null;

    public static synchronized SeleniumUtil getInstance() {
        if (seleniumUtil == null) {
            System.setProperty("webdriver.chrome.driver", "/Users/Zuz/Documents/chromedriver");
            seleniumUtil = new SeleniumUtil();
        }
        return seleniumUtil;
    }

//    /**
//     * 获取驱动
//     *
//     * @return
//     */
//    public WebDriver getDriver() {
//        if (driver == null) {
//            // 创建一个 chrome 的浏览器实例
//            driver = new ChromeDriver();
//        }
//        return driver;
//    }

    /**
     * 根据id获取驱动
     *
     * @return
     */
    public WebDriver getDriver(Long id) {
        if (null == id) {
            return null;
        }
        // 创建一个 chrome 的浏览器实例
        if (!driverMap.containsKey(id)) {
            //Chrome
            driverMap.put(id, new ChromeDriver());
        }
        return driverMap.get(id);
    }
}
