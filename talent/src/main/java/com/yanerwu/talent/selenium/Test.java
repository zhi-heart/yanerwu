package com.yanerwu.talent.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

/**
 * @Author Zuz
 * @Date 2017/7/31 15:11
 * @Description
 */
public class Test {
    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "/Users/Zuz/Documents/geckodriver");
        System.setProperty("webdriver.firefox.bin", "/Applications/Firefox.app/Contents/MacOS/firefox");

        ProfilesIni pi = new ProfilesIni();
        FirefoxProfile profile = pi.getProfile("default");

        WebDriver driver = new FirefoxDriver(profile);

        try {
            String url = "https://www.taobao.com/";
            driver.get(url);
            //窗口最大化
            //driver.manage().window().maximize();
            Thread.sleep(3000);

            //获取登录元素
            WebElement loginLink = driver.findElement(By.linkText("亲，请登录"));
            loginLink.click();
            Thread.sleep(3000);

            //选择账号密码登录
            WebElement switchToForm = driver.findElement(By.id("J_Quick2Static"));
            switchToForm.click();
            Thread.sleep(3000);
            //获取账号输入框
            WebElement userName = driver.findElement(By.id("TPL_username_1"));
            userName.clear();
            //获取密码输入框
            WebElement password = driver.findElement(By.id("TPL_password_1"));
            password.clear();
            //输入账号密码
            userName.sendKeys("zhi_heart");
            password.sendKeys("zztianxie919");
            //获取登录按钮
            WebElement submit = driver.findElement(By.id("J_SubmitStatic"));
            submit.click();
            Thread.sleep(10000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
