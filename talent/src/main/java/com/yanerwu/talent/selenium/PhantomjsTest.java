package com.yanerwu.talent.selenium;

/**
 * @Author Zuz
 * @Date 2017/8/7 19:14
 * @Description
 */
public class PhantomjsTest {
    public static void main(String[] args) throws Exception {
//        //设置必要参数
//        DesiredCapabilities dcaps = new DesiredCapabilities();
//        //ssl证书支持
//        dcaps.setCapability("acceptSslCerts", true);
//        //截屏支持
//        dcaps.setCapability("takesScreenshot", true);
//        //css搜索支持
//        dcaps.setCapability("cssSelectorsEnabled", true);
//        //js支持
//        dcaps.setJavascriptEnabled(true);
//        //驱动支持
//        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/local/bin/phantomjs");
//        //创建无界面浏览器对象
//        PhantomJSDriver driver = new PhantomJSDriver(dcaps);
//
//        PublishGoodsVO vo = new PublishGoodsVO();
//
//        Long userId=1L;
//
//        int bool = 3;
//        long l = System.currentTimeMillis();
//        try {
//            do {
//                driver.get(Constants.PUBLISH_GOODS_URL);
//                Thread.sleep(3000);
//
//                try {
//                    //点击添加宝贝
//                    driver.findElement(By.xpath("//*[@id=\"body\"]/div/div[1]/div[1]/div[2]")).click();
//                    Thread.sleep(1000);
//                    break;
//                } catch (InterruptedException e) {
//
//                }
//            } while (true);
//
//            //宝贝url输入框
//            WebElement itemUrl = driver.findElement(By.id("itemUrl"));
//            itemUrl.clear();
//            Thread.sleep(300);
//
//            if (vo.isTmall()) {
//                itemUrl.sendKeys(String.format("https://detail.%s.com/item.htm?id=%s", "tmall", vo.getGoodsId()));
//            } else {
//                itemUrl.sendKeys(String.format("https://detail.%s.com/item.htm?id=%s", "taobao", vo.getGoodsId()));
//            }
//            Thread.sleep(1000);
//
//            //获取宝贝详情
//            WebElement getInfo = driver.findElement(By.xpath("//*[@id=\"dialog-body-1\"]/div/form/div/div[2]/div/div/button"));
//            getInfo.click();
//            Thread.sleep(3000);
//
//            //宝贝详情确认
//            driver.findElement(By.xpath("//*[@id=\"dialog-footer-2\"]/div/button[1]")).click();
//            Thread.sleep(3000);
//
//            //输入标题
//            WebElement title = driver.findElement(By.xpath("//*[@id=\"title\"]/div/span/input"));
//            title.clear();
//            title.sendKeys(vo.getTitleSimple());
//            Thread.sleep(1000);
//
//            //推荐理由
//            WebElement summary = driver.findElement(By.xpath("//*[@id=\"summary\"]/div/span/textarea"));
//            summary.clear();
//            Thread.sleep(300);
//
//            StringBuffer sb = new StringBuffer();
//            sb.append(String.format("领%s元独家券，券后【%s元】,包邮秒杀！", vo.getQuanPrice(), vo.getPrice()));
//            sb.append(String.format("【领券下单】%s,长按复制整段信息,重新打开☞手机淘宝☜抢购",vo.getConverWord()));
//            sb.append(String.format("【推荐理由】%s", vo.getIntroduce()));
//            if (sb.length() > 135) {
//                sb.delete(135, sb.length());
//            }
//            summary.sendKeys(sb.toString());
//            Thread.sleep(2000);
//
//            //提交 可能失败,重试一次
//            WebElement submit = driver.findElement(By.name("submit"));
//            try {
//                Thread.sleep(1000);
//                submit.click();
//            } catch (InterruptedException e) {
//                Thread.sleep(1000);
//                submit.click();
//            }
//
//            //检查发布成功
//            for (int i = 0; i < 10; i++) {
//                try {
//                    if (driver.getPageSource().contains("发布成功")) {
//                        bool = 2;
//                        break;
//                    }
//                    Thread.sleep(1000);
//                } catch (Exception e) {
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
