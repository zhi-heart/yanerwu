package com.yanerwu.talent.selenium;

import com.yanerwu.common.Constants;
import com.yanerwu.talent.vo.PublishGoodsVO;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Zuz
 * @Date 2017/7/28 14:37
 * @Description
 */
public class PublishGoods {

    private static Logger logger = LoggerFactory.getLogger(PublishGoods.class);


    public static String getLonginUrl(Long userId) {
        String url = "";
        try {
            WebDriver driver = SeleniumUtil.getInstance().getDriver(userId);
            driver.get(Constants.PUBLISH_GOODS_URL);
            Thread.sleep(5000);
            //切换ifram
            driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"taobadaren_cd\"]/div[1]/div/div[2]/div/div[2]/div/iframe")));
            url = driver.findElement(By.xpath("//*[@id=\"J_QRCodeImg\"]/img")).getAttribute("src");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return url;
    }


    /**
     * @param vo
     * @return -1:未登录,2:正常,3:异常
     */
    public static int publish(PublishGoodsVO vo, Long userId) {
        WebDriver driver = SeleniumUtil.getInstance().getDriver(userId);
        int bool = 3;
        long l = System.currentTimeMillis();
        try {
            do {
                driver.get(Constants.PUBLISH_GOODS_URL);
                //窗口最大化
//                driver.manage().window().maximize();
                Thread.sleep(3000);

                //检测是否未登录
                if (driver.getCurrentUrl().contains("login.htm")) {
                    logger.info("用户:{} 未登录,请登录", userId);
                    return -1;
                }
                //保持连接
                if (null == vo) {
                    driver.get(Constants.PUBLISH_GOODS_URL);
                    return -2;
                }
                try {
                    //点击添加宝贝
                    driver.findElement(By.xpath("//*[@id=\"body\"]/div/div[1]/div[1]/div[2]")).click();
                    Thread.sleep(1000);
                    break;
                } catch (InterruptedException e) {

                }
            } while (true);

            //宝贝url输入框
            WebElement itemUrl = driver.findElement(By.id("itemUrl"));
            itemUrl.clear();
            Thread.sleep(300);

            if (vo.isTmall()) {
                itemUrl.sendKeys(String.format("https://detail.%s.com/item.htm?id=%s", "tmall", vo.getGoodsId()));
            } else {
                itemUrl.sendKeys(String.format("https://detail.%s.com/item.htm?id=%s", "taobao", vo.getGoodsId()));
            }
            Thread.sleep(1000);

            //获取宝贝详情
            WebElement getInfo = driver.findElement(By.xpath("//*[@id=\"dialog-body-1\"]/div/form/div/div[2]/div/div/button"));
            getInfo.click();
            Thread.sleep(3000);

            //宝贝详情确认
            driver.findElement(By.xpath("//*[@id=\"dialog-footer-2\"]/div/button[1]")).click();
            Thread.sleep(3000);

            //输入标题
            WebElement title = driver.findElement(By.xpath("//*[@id=\"title\"]/div/span/input"));
            title.clear();
            title.sendKeys(vo.getTitleSimple());
            Thread.sleep(1000);

            //推荐理由
            WebElement summary = driver.findElement(By.xpath("//*[@id=\"summary\"]/div/span/textarea"));
            summary.clear();
            Thread.sleep(300);

            StringBuffer sb = new StringBuffer();
            sb.append(String.format("领%s元独家券，券后【%s元】,包邮秒杀！", vo.getQuanPrice(), vo.getPrice()));
            sb.append(String.format("【领券下单】%s,长按复制整段信息,重新打开☞手机淘宝☜抢购",vo.getConverWord()));
            sb.append(String.format("【推荐理由】%s", vo.getIntroduce()));
            if (sb.length() > 135) {
                sb.delete(135, sb.length());
            }
            summary.sendKeys(sb.toString());
            Thread.sleep(2000);

            //提交 可能失败,重试一次
            WebElement submit = driver.findElement(By.name("submit"));
            try {
                Thread.sleep(1000);
                submit.click();
            } catch (InterruptedException e) {
                Thread.sleep(1000);
                submit.click();
            }

            //检查发布成功
            for (int i = 0; i < 10; i++) {
                try {
                    String text = driver.findElement(By.xpath("//*[@id=\"ice_page_content-success\"]/div/div[2]/div[2]/div/p[1]")).getText();
                    if ("发布成功！".equals(text)) {
                        bool = 2;
                        break;
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("userId:{} gid:{} goodsId:{} title:{} time:{} status:{}",
                vo.getUserId(), vo.getId(), vo.getGoodsId(), vo.getTitleSimple(), (System.currentTimeMillis() - l) / 1000, bool);
        return bool;
    }

    /**
     * 等待驱动器加载页面完成
     */
    public static void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoad = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, 500);
        wait.until(pageLoad);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保持链接
     */
    public void hold() {


    }

}
