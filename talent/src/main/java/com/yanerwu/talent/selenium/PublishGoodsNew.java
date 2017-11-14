package com.yanerwu.talent.selenium;

import com.yanerwu.common.Constants;
import com.yanerwu.talent.vo.PublishGoodsVO;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Zuz
 * @Date 2017/7/28 14:37
 * @Description
 */
public class PublishGoodsNew {
    private static Logger logger = LoggerFactory.getLogger(PublishGoodsNew.class);


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
                Thread.sleep(1000);
                //如果有弹窗,点击确认
                try {
                    Alert alert = driver.switchTo().alert();
                    if (null != alert) {
                        alert.accept();
                    }
                } catch (Throwable e) {

                }

                //检测是否未登录
                if (driver.getCurrentUrl().contains("login?")) {
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
                    driver.findElement(By.xpath("//div[@id='ice_container']/div/div[2]/div/div/div[4]/div/div[2]/form/div/div/div/div/div/div/i")).click();
                    Thread.sleep(3000);
                    break;
                } catch (Throwable e) {

                }
            } while (true);

            //进入窗口,点击添加宝贝
            driver.findElement(By.xpath("//*[@id=\"dialog-body-1\"]/div/div/div[1]/div[1]/div/div/div/div/div[2]/div")).click();
            Thread.sleep(3000);

            //宝贝url输入框
            WebElement itemUrl = driver.findElement(By.xpath("//*[@id=\"dialog-body-1\"]/div/div/div[1]/div[2]/div[2]/div/div/span[1]/input"));
            itemUrl.clear();
            Thread.sleep(300);
            //都当成天猫
            itemUrl.sendKeys(String.format("https://detail.%s.com/item.htm?id=%s", "tmall", vo.getGoodsId()));
            Thread.sleep(5000);

            //点击添加宝贝
            try {
                driver.findElement(By.xpath("//*[@id=\"dialog-body-1\"]/div/div/div[1]/div[2]/div[2]/div/div/button")).click();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(3000);

            //点击宝贝页面确定
            try {
                driver.findElement(By.xpath("//*[@id=\"dialog-footer-2\"]/div/div[2]/button[1]")).click();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(3000);

            //输入标题
            WebElement title = driver.findElement(By.xpath("//*[@id=\"title\"]"));
            title.clear();
            title.sendKeys(vo.getTitleSimple());
            Thread.sleep(3000);

            //推荐理由
            WebElement summary = driver.findElement(By.xpath("//*[@id=\"summary\"]"));
            summary.clear();
            Thread.sleep(300);

            StringBuffer sb = new StringBuffer();
            sb.append(String.format("领%s元券，券后【%s元】,包邮秒杀！已抢%s件！", vo.getQuanPrice(), vo.getPrice(),vo.getSalesNum()));
            sb.append(String.format("复制这条信息%s，重新打开☞手机淘宝☜即可领券！",vo.getConverWord()));
            sb.append(String.format("【推荐理由】%s", vo.getIntroduce()));
            if (sb.length() > 135) {
                sb.delete(135, sb.length());
            }
            summary.sendKeys(sb.toString());
            Thread.sleep(3000);

            //提交 可能失败,重试一次
            WebElement submit = driver.findElement(By.xpath("//*[@id=\"ice_container\"]/div/div[2]/div/div/div[3]/div/div[2]/div/div/button[1]"));

            try {
                Thread.sleep(3000);
                submit.click();
            } catch (Exception e) {
                Thread.sleep(3000);
                submit.click();
            }

            //检查发布成功
            for (int i = 0; i < 10; i++) {
                try {
                    if (driver.getCurrentUrl().contains("normal")) {
                        bool = 2;
                        break;
                    }
                    Thread.sleep(3000);
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("userId:{} gid:{} goodsId:{} time:{} status:{} title:{}",
                vo.getUserId(), vo.getId(), vo.getGoodsId(), (System.currentTimeMillis() - l) / 1000, bool, vo.getTitleSimple());
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
}
