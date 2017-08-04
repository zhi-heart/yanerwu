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
public class ExampleForFireFox {
    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/Users/Zuz/Documents/chromedriver");

        // 创建一个 chrome 的浏览器实例
        WebDriver driver = new ChromeDriver();

        //淘宝登录的cookie无效
//        String cookiesStr = "";
//        JSONArray jsonArray = JSON.parseArray(cookiesStr);
//
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JSONObject c = jsonArray.getJSONObject(i);
//            Cookie.Builder builder = new Cookie.Builder(c.getString("name"), c.getString("value"));
//            if (c.containsKey("domain")) {
//                builder.domain(c.getString("domain"));
//            }
//            if (c.containsKey("expiry")) {
//                builder.expiresOn(new Date(c.getLong("expiry")));
//            }
//            if (c.containsKey("httpOnly")) {
//                builder.isHttpOnly(c.getBoolean("httpOnly"));
//            }
//            if (c.containsKey("path")) {
//                builder.domain(c.getString("path"));
//            }
//            if (c.containsKey("secure")) {
//                builder.isSecure(c.getBoolean("secure"));
//            }
//            Cookie cookie = builder.build();
//
//            driver.manage().addCookie(cookie);
//        }

        String url = "https://daren.taobao.com/content/publish.htm?spm=a21vj.8768172.0.0.67755b2aJhfyQe&push=true&formName=daren_item";
        driver.get(url);
        //窗口最大化
        driver.manage().window().maximize();

//        Set<Cookie> cookies = driver.manage().getCookies();
//        System.out.println(JSON.toJSONString(cookies));

        //点击添加宝贝
        driver.findElement(By.xpath("//*[@id=\"body\"]/div/div[1]/div[1]/div[2]")).click();
        Thread.sleep(1000);

        //宝贝url输入框
        WebElement itemUrl = driver.findElement(By.id("itemUrl"));
        itemUrl.clear();
        Thread.sleep(300);
        itemUrl.sendKeys("https://detail.tmall.com/item.htm?id=37805154658");
        Thread.sleep(1000);

        //获取宝贝详情
        WebElement getInfo = driver.findElement(By.xpath("//*[@id=\"dialog-body-1\"]/div/form/div/div[2]/div/div/button"));
        getInfo.click();
        Thread.sleep(1000);

        //宝贝详情确认
        driver.findElement(By.xpath("//*[@id=\"dialog-footer-2\"]/div/button[1]")).click();
        Thread.sleep(3000);

        //输入标题
        WebElement title = driver.findElement(By.xpath("//*[@id=\"title\"]/div/span/input"));
        title.clear();
        Thread.sleep(300);
        title.sendKeys("决明子枕头枕芯薰衣草荞麦壳一对拍2夏季凉学生单双成人");
        Thread.sleep(1000);

        //推荐理由
        WebElement summary = driver.findElement(By.xpath("//*[@id=\"summary\"]/div/span/textarea"));
        summary.clear();
        Thread.sleep(300);
        summary.sendKeys("旗舰店正品【累计热销100多万只】，天然花草填充，安神助眠，透气网面设计，高度可调节，舒适睡眠体验，有效缓解颈椎疲劳");
        Thread.sleep(1000);

        //提交
        WebElement submit = driver.findElement(By.name("submit"));
        submit.click();


        //关闭浏览器
//        driver.quit();
    }
}
