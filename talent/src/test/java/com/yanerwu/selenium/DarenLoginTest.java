package com.yanerwu.selenium;

import com.yanerwu.talent.selenium.SeleniumUtil;
import com.yanerwu.utils.DateUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * @Author Zuz
 * @Date 2017/9/22 17:23
 * @Description
 */
public class DarenLoginTest {
    public static void main(String[] args) throws Exception {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(DateUtils.getBackDate(-1));


        SeleniumUtil seleniumUtil = SeleniumUtil.getInstance();
        WebDriver driver = seleniumUtil.getDriver(1L);

        driver.get("https://www.taobao.com/sitemap.php");

        driver.manage().deleteAllCookies();

        String cookieStr = "_tb_token_=e8a389b6e3b73;" +
                "cookie1=W8gxYKIZWHKmU7jfu9BXrNAgOclaLQ53luA3%2BTSoYnY%3D;" +
                "unb=2256236032;" +
                "cookie2=3c2637bcd331497382250b911df76ed2;" +
                "sg=t23;";
        for (String cookie : cookieStr.split(";")) {
            String[] s = cookie.split("=");
//            Cookie c = new Cookie(s[0].trim(), s[1].trim(), ".taobao.com", null);
            Cookie c = new Cookie(s[0].trim(), s[1].trim(), ".taobao.com", "/",null);
            driver.manage().addCookie(c);
        }

//        TimeUnit.SECONDS.sleep(1);
        driver.get("https://member1.taobao.com/member/fresh/deliver_address.htm");
        Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println(cookies);
    }

}
