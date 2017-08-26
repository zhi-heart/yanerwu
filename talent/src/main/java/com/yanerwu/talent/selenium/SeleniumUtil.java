package com.yanerwu.talent.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

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
//            System.setProperty("webdriver.chrome.driver", "/Users/Zuz/Documents/chromedriver");
            System.setProperty("webdriver.chrome.driver", "c:/zuz/chromedriver.exe");
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
            if (true) {
                //Chrome
                ChromeOptions options = new ChromeOptions();
                options.setBinary("C:/Users/Administrator/AppData/Local/Google/Chrome/Application/chrome.exe");
                driverMap.put(id, new ChromeDriver());
            } else {
                //设置必要参数
                DesiredCapabilities dcaps = new DesiredCapabilities();
                //ssl证书支持
                dcaps.setCapability("acceptSslCerts", true);
                //截屏支持
                dcaps.setCapability("takesScreenshot", true);
                //css搜索支持
                dcaps.setCapability("cssSelectorsEnabled", true);
                //js支持
                dcaps.setJavascriptEnabled(true);
                //驱动支持
                dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/local/bin/phantomjs");
                //创建无界面浏览器对象
                PhantomJSDriver driver = new PhantomJSDriver(dcaps);
                driverMap.put(id, driver);
            }


        }
        return driverMap.get(id);
    }

    public static void main(String[] args) throws Exception {
        DesiredCapabilities dcaps = new DesiredCapabilities();
        //ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        //截屏支持
        dcaps.setCapability("takesScreenshot", true);
        //css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        //js支持
        dcaps.setJavascriptEnabled(true);
        //驱动支持
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/local/bin/phantomjs");
        //创建无界面浏览器对象
        PhantomJSDriver driver = new PhantomJSDriver(dcaps);

        driver.get("http://www.baidu.com");
        Thread.sleep(2000);
        System.out.println(driver.getPageSource());

        System.out.println("111");

    }
}
