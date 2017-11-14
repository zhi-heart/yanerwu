package com.yanerwu.talent.service;

import com.alibaba.fastjson.JSON;
import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.entity.GoodsTop;
import com.yanerwu.entity.TaokeConver;
import com.yanerwu.talent.entity.User;
import com.yanerwu.talent.entity.UserGoods;
import com.yanerwu.talent.selenium.PublishGoodsNew;
import com.yanerwu.talent.selenium.SeleniumUtil;
import com.yanerwu.talent.vo.PublishGoodsVO;
import com.yanerwu.utils.HttpClientUtil;
import com.yanerwu.utils.Mail;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author Zuz
 * @version 1.0
 * @Description 人气商品
 */
@Service
public class GoodsTopService {

    public static ConcurrentMap<Long, ExecutorService> execMap = new ConcurrentHashMap<>();
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DbUtilsTemplate talentTemplate;

    /**
     * 采集热门商品
     */
    public void collectGoodsTop(String url) {
        String str = HttpClientUtil.doGet(url);
        String jsonArray = JSON.parseObject(str).getJSONArray("result").toJSONString();
        List<GoodsTop> goodsTops = JSON.parseArray(jsonArray, GoodsTop.class);

        List<GoodsTop> gts = new ArrayList<>();
        for (final GoodsTop gt : goodsTops) {
            String sql = "select id from goods_top where id=?";
            if (null == talentTemplate.findBy(sql, 1, gt.getId())) {
                gts.add(gt);
                break;
            }
        }
        talentTemplate.insert(gts);

        //插入user_goods
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        String userSql = "select * from user where status=0 and pid=0";
        List<User> users = talentTemplate.find(User.class, userSql, processor);
        List<UserGoods> ugs = new ArrayList<>();
        for (GoodsTop gt : gts) {
            for (User u : users) {
                UserGoods ug = new UserGoods();
                ug.setUserId(u.getUserId());
                ug.setGid(gt.getId());
                ug.setGoodsId(gt.getGoodsId());
                ug.setStatus(0);
                ugs.add(ug);
            }
        }
        talentTemplate.insert(ugs);

        logger.info("采集结束,插入数据{}条", gts.size());
    }

    /**
     * 转换链接
     */
    public void collectConver() {
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        String userSql = "select * from user where status=0 and pid=0";
        List<User> users = talentTemplate.find(User.class, userSql, processor);
        Map<Long, User> userMap = new HashMap<>();
        for (User u : users) {
            userMap.put(u.getUserId(), u);
        }

        String userGoodsSql = "select * from user_goods where status=0 and create_time > date_sub(now(), interval 3 hour)";
        List<UserGoods> userGoodses = talentTemplate.find(UserGoods.class, userGoodsSql, processor);
        for (UserGoods ug : userGoodses) {
            User u = userMap.get(ug.getUserId());
            String url = "http://www.dataoke.com/dtpwd";
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("gid", String.valueOf(ug.getGid())));
            params.add(new BasicNameValuePair("type", "1"));

            Map<String, String> headers = new HashMap<>();
            headers.put("Host", "www.dataoke.com");
            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:54.0) Gecko/20100101 Firefox/54.0");
            headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            headers.put("Cookie", String.format("userid=%s; user_email=%s;token=%s; browserCode=8f5662af0c880257a3c38b8f81fb1126", u.getUserId(), u.getUserEmail(), u.getToken()));
            String s = HttpClientUtil.sendPost(url, params, headers);
            try {
                TaokeConver taokeConver = JSON.parseObject(s, TaokeConver.class);
                if ("1".equals(taokeConver.getStatus())) {
                    String link = taokeConver.getData().getData().getLink();
                    String password = taokeConver.getData().getData().getPassword();
                    ug.setConverLink(link);
                    ug.setConverWord(password);
                    ug.setStatus(1);
                    if (StringUtils.isBlank(ug.getConverWord())) {
                        logger.info(String.format("gid:{} goodsId:{} converLink:{} ConverWord:{}"), ug.getGid(), ug.getGoodsId(), ug.getConverLink(), ug.getConverWord());
                        logger.info(s);
                        talentTemplate.delete(ug);
                        continue;
                    }
                } else {
                    //有错误
                    System.out.println(String.format("有错误,详情:[%s]", s));
                    ug.setStatus(4);
                }
                //状态?{"0":"待转换","1":"待发布","2":"已发布","3":"发布异常","4":"转链异常","5":"商品失效"}
            } catch (Exception e) {
                ug.setStatus(4);
                logger.info("Exception gid:{} json:{}", ug.getGid(), s);
            }
            talentTemplate.update(ug);
        }


        //处理 单联盟 > 多达人
        userSql = "select * from user where status=0 and pid>0";
        String insertSql = "insert into user_goods (user_id, goods_id, gid, conver_link, conver_word, status) " +
                "select ? user_id, goods_id, gid, conver_link, conver_word, 1 status from user_goods where user_id = ? and status = 1 ";
        talentTemplate.find(User.class, userSql, processor)
                .stream()
                .forEach((u) ->
                        talentTemplate.update(insertSql, new Object[]{
                                u.getUserId(),
                                u.getPid()
                        })
                );


    }

    /**
     * 发布商品
     */
    public void publishGoods() {
        for (Long userId : execMap.keySet()) {
            new Thread(
                    () -> {
                        while (true) {
                            try {
                                ExecutorService exec = Executors.newFixedThreadPool(1);
                                String sql = "SELECT ug.id, ug.user_id, gt.org_price, gt.quan_price, gt.price, gt.title, gt.title_simple,gt.sales_num, " +
                                        "gt.introduce, gt.is_tmall, ug.conver_word, ug.goods_id, u.taobao_login_name, u.mail_login_name FROM " +
                                        "user_goods ug, goods_top gt,user u WHERE ug.gid = gt.id AND ug.status = 1 and u.user_id=ug.user_id " +
                                        "and u.status=0 and u.user_id=? LIMIT 999";
                                RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
                                List<PublishGoodsVO> vos = talentTemplate.find(PublishGoodsVO.class, sql, userId, processor);
                                Collections.shuffle(vos);
                                int i = 0;
                                do {
                                    PublishGoodsVO vo = vos.size() > 0 ? vos.get(i) : null;
                                    exec.execute(() -> {
                                        int status = PublishGoodsNew.publish(vo, userId);
                                        switch (status) {
                                            case -2:
                                                //保持连接
                                                logger.info("保持连接");
                                                break;
                                            case -1:
                                                //掉线
                                                try {
                                                    String loginUrl = PublishGoodsNew.getLonginUrl(userId);
                                                    logger.info("userId:{} loginUrl:{}", userId, loginUrl);
                                                    //发送邮件
                                                    User u = getUserByUserId(userId);
                                                    if (StringUtils.isNotBlank(u.getMailLoginName())) {
                                                        String subject = String.format("请用淘宝账号 %s 扫码二维码", u.getTaobaoLoginName());
                                                        String content = String.format("<img src='%s' />", loginUrl);
                                                        String smtp = "smtp.163.com";
                                                        Mail.sendAndCc(smtp, u.getMailLoginName(), u.getMailLoginName(), null, subject, content, u.getMailLoginName(), u.getMailLoginPwd());
                                                    }

                                                    TimeUnit.MINUTES.sleep(3);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                break;
                                            default:
                                                //状态?{"0":"待转换","1":"待发布","2":"已发布","3":"发布异常","4":"转链异常"}
                                                String updateSql = "update user_goods set status=? where id=?";
                                                talentTemplate.update(updateSql, new Object[]{
                                                        status,
                                                        vo.getId()
                                                });
                                                break;
                                        }
//                                        try {
//                                            TimeUnit.SECONDS.sleep((int) (60 * Math.random() + 1));
//                                        } catch (InterruptedException e) {
//                                            e.printStackTrace();
//                                        }
                                    });
                                    i++;
                                } while (i < vos.size());
                                exec.shutdown();
                                while (true) {
                                    if (exec.isTerminated()) {
                                        break;
                                    }
                                    try {
                                        TimeUnit.MINUTES.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (Exception e) {
                                logger.error("", e);
                            }
                        }
                    }
            ).start();
        }
    }

    /**
     * 初始化驱动
     */
    public void init() {
        String sql = "select * from user where status=0";
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        List<User> users = talentTemplate.find(User.class, sql, processor);
        for (User user : users) {
            execMap.put(user.getUserId(), Executors.newFixedThreadPool(1));
            SeleniumUtil seleniumUtil = SeleniumUtil.getInstance();
            WebDriver driver = seleniumUtil.getDriver(user.getUserId());
            driver.manage().window().maximize();
//            driver.get(Constants.PUBLISH_GOODS_URL);
//            ((JavascriptExecutor) driver).executeScript(String.format("alert(\"请用 %s 登录!\")", user.getTaobaoLoginName()));
        }
    }

    private User getUserByUserId(Object userId) {
        String sql = "select * from user where status=0 and user_id=?";
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        return talentTemplate.find(User.class, sql, userId, processor).get(0);
    }
}
