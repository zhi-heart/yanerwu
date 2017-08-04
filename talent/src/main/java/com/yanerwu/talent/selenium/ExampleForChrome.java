package com.yanerwu.talent.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @Author Zuz
 * @Date 2017/7/27 17:37
 * @Description
 */
public class ExampleForChrome {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "/Users/Zuz/Documents/chromedriver");

        // 创建一个 chrome 的浏览器实例
        WebDriver driver = new ChromeDriver();

        // 让浏览器访问 Baidu
//        String url = "https://daren.taobao.com/content/publish.htm?spm=a21vj.8768172.0.0.67755b2aJhfyQe&push=true&formName=daren_item";
        String url = "https://www.taobao.com/";
        driver.get(url);
        //窗口最大化
        driver.manage().window().maximize();

        //获取登录元素
        WebElement loginLink = driver.findElement(By.linkText("亲，请登录"));
        loginLink.click();

//        选择账号密码登录
        WebElement switchToForm = driver.findElement(By.id("J_Quick2Static"));
        switchToForm.click();
//        获取账号输入框
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
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.get("https://daren.taobao.com/content/publish.htm?spm=a21vj.8768172.0.0.67755b2aJhfyQe&push=true&formName=daren_item\";");

        // 获取 网页的 title
//        System.out.println("1 Page title is: " + driver.getTitle());
//
//        // 通过 id 找到 input 的 DOM
//        WebElement element = driver.findElement(By.id("kw"));
//
//        // 输入关键字
//        element.sendKeys("zTree");
//
//        // 提交 input 所在的  form
//        element.submit();
//
////        // 通过判断 title 内容等待搜索页面加载完毕，Timeout 设置10秒
////        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
////            public Boolean apply(WebDriver d) {
////                return d.getTitle().toLowerCase().endsWith("ztree");
////            }
////        });
//
//
//
//        // 显示搜索结果页面的 title
//        System.out.println("2 Page title is: " + driver.getTitle());

        //关闭浏览器
//        driver.quit();
    }
}
