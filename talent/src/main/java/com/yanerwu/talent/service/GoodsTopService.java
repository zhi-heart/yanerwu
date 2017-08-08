package com.yanerwu.talent.service;

import com.alibaba.fastjson.JSON;
import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.common.Page;
import com.yanerwu.entity.GoodsTop;
import com.yanerwu.entity.TaokeConver;
import com.yanerwu.talent.entity.User;
import com.yanerwu.talent.entity.UserGoods;
import com.yanerwu.talent.selenium.PublishGoods;
import com.yanerwu.talent.selenium.SeleniumUtil;
import com.yanerwu.talent.vo.PublishGoodsVO;
import com.yanerwu.utils.HttpClientUtil;
import com.yanerwu.utils.Mail;
import com.yanerwu.utils.Tools;
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

    public Page findPage(GoodsTop query, Page page) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql2 = new StringBuilder("select * from goods_top t where 1=1 ");
        if (Tools.isNotEmpty(query.getId())) {
            sql2.append(" and  t.id = ? ");
            params.add(query.getId());
        }
        if (Tools.isNotEmpty(query.getGoodsId())) {
            sql2.append(" and  t.goods_id like ? ");
            params.add(String.format("%%%s%%", query.getGoodsId()));
        }
        if (Tools.isNotEmpty(query.getTitle())) {
            sql2.append(" and  t.title like ? ");
            params.add(String.format("%%%s%%", query.getTitle()));
        }
        if (Tools.isNotEmpty(query.getTitleSimple())) {
            sql2.append(" and  t.title_simple like ? ");
            params.add(String.format("%%%s%%", query.getTitleSimple()));
        }
        if (Tools.isNotEmpty(query.getPic())) {
            sql2.append(" and  t.pic like ? ");
            params.add(String.format("%%%s%%", query.getPic()));
        }
        if (Tools.isNotEmpty(query.getCid())) {
            sql2.append(" and  t.cid = ? ");
            params.add(query.getCid());
        }
        if (Tools.isNotEmpty(query.getOrgPrice())) {
            sql2.append(" and  t.org_price = ? ");
            params.add(query.getOrgPrice());
        }
        if (Tools.isNotEmpty(query.getPrice())) {
            sql2.append(" and  t.price = ? ");
            params.add(query.getPrice());
        }
        if (Tools.isNotEmpty(query.getIsTmall())) {
            sql2.append(" and  t.is_tmall = ? ");
            params.add(query.getIsTmall());
        }
        if (Tools.isNotEmpty(query.getSalesNum())) {
            sql2.append(" and  t.sales_num = ? ");
            params.add(query.getSalesNum());
        }
        if (Tools.isNotEmpty(query.getDsr())) {
            sql2.append(" and  t.dsr = ? ");
            params.add(query.getDsr());
        }
        if (Tools.isNotEmpty(query.getSellerId())) {
            sql2.append(" and  t.seller_id = ? ");
            params.add(query.getSellerId());
        }
        if (Tools.isNotEmpty(query.getCommissionJihua())) {
            sql2.append(" and  t.commission_jihua = ? ");
            params.add(query.getCommissionJihua());
        }
        if (Tools.isNotEmpty(query.getCommissionQueqiao())) {
            sql2.append(" and  t.commission_queqiao = ? ");
            params.add(query.getCommissionQueqiao());
        }
        if (Tools.isNotEmpty(query.getJihuaLink())) {
            sql2.append(" and  t.jihua_link like ? ");
            params.add(String.format("%%%s%%", query.getJihuaLink()));
        }
        if (Tools.isNotEmpty(query.getJihuaShenhe())) {
            sql2.append(" and  t.jihua_shenhe = ? ");
            params.add(query.getJihuaShenhe());
        }
        if (Tools.isNotEmpty(query.getIntroduce())) {
            sql2.append(" and  t.introduce like ? ");
            params.add(String.format("%%%s%%", query.getIntroduce()));
        }
        if (Tools.isNotEmpty(query.getQuanId())) {
            sql2.append(" and  t.quan_id like ? ");
            params.add(String.format("%%%s%%", query.getQuanId()));
        }
        if (Tools.isNotEmpty(query.getQuanPrice())) {
            sql2.append(" and  t.quan_price = ? ");
            params.add(query.getQuanPrice());
        }
        if (Tools.isNotEmpty(query.getQuanTime())) {
            sql2.append(" and  t.quan_time like ? ");
            params.add(String.format("%%%s%%", query.getQuanTime()));
        }
        if (Tools.isNotEmpty(query.getQuanSurplus())) {
            sql2.append(" and  t.quan_surplus = ? ");
            params.add(query.getQuanSurplus());
        }
        if (Tools.isNotEmpty(query.getQuanReceive())) {
            sql2.append(" and  t.quan_receive = ? ");
            params.add(query.getQuanReceive());
        }
        if (Tools.isNotEmpty(query.getQuanCondition())) {
            sql2.append(" and  t.quan_condition like ? ");
            params.add(String.format("%%%s%%", query.getQuanCondition()));
        }
        if (Tools.isNotEmpty(query.getQuanMlink())) {
            sql2.append(" and  t.quan_m_link like ? ");
            params.add(String.format("%%%s%%", query.getQuanMlink()));
        }
        if (Tools.isNotEmpty(query.getQuanLink())) {
            sql2.append(" and  t.Quan_link like ? ");
            params.add(String.format("%%%s%%", query.getQuanLink()));
        }
        if (StringUtils.isNotBlank(page.getOrderField())) {
            sql2.append("order by ? ?");
            params.add(page.getOrderField());
            params.add(page.getOrderDirection());
        }
        return talentTemplate.findPage(page, sql2.toString(), params.toArray(), GoodsTop.class);
    }

    /**
     * 新增
     */
    public Object save(GoodsTop goodsTop) {
        return talentTemplate.insert(goodsTop);
    }

    /**
     * 修改
     */
    public int update(GoodsTop goodsTop) {
        return talentTemplate.update(goodsTop);
    }

    /**
     * 删除
     */
    public int delete(GoodsTop goodsTop) {
        return talentTemplate.delete(goodsTop);
    }

    public <T> T getById(GoodsTop goodsTop) {
        return (T) talentTemplate.getById(goodsTop);
    }

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
            }
        }
        talentTemplate.insert(gts);

        //插入user_goods
        RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
        String userSql = "select * from user where status=0";
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
        String userSql = "select * from user where status=0";
        List<User> users = talentTemplate.find(User.class, userSql, processor);
        Map<Long, User> userMap = new HashMap<>();
        for (User u : users) {
            userMap.put(u.getUserId(), u);
        }


        String userGoodsSql = "select * from user_goods where status=0";
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
                                String sql = "SELECT ug.id, ug.user_id, gt.org_price, gt.quan_price, gt.price, gt.title, gt.title_simple, " +
                                        "gt.introduce, gt.is_tmall, ug.conver_word, ug.goods_id, u.taobao_login_name, u.mail FROM " +
                                        "user_goods ug, goods_top gt,user u WHERE ug.gid = gt.id AND ug.status = 1 and u.user_id=ug.user_id " +
                                        "and u.status=0 and u.user_id=? LIMIT 999";
                                RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
                                List<PublishGoodsVO> vos = talentTemplate.find(PublishGoodsVO.class, sql, userId, processor);
                                Collections.shuffle(vos);
                                int i = 0;
                                do {
                                    PublishGoodsVO vo = vos.size() > 0 ? vos.get(i) : null;
                                    exec.execute(() -> {
                                        int status = PublishGoods.publish(vo, userId);
                                        switch (status) {
                                            case -2:
                                                //保持连接
                                                logger.info("保持连接");
                                                break;
                                            case -1:
                                                //掉线
                                                try {
                                                    String loginUrl = PublishGoods.getLonginUrl(userId);
                                                    logger.info("userId:{} loginUrl:{}", userId, loginUrl);
                                                    //发送邮件
                                                    User u = getUserByUserId(userId);
                                                    if (StringUtils.isNotBlank(u.getMail())) {
                                                        String subject = String.format("请用淘宝账号 %s 扫码二维码", u.getTaobaoLoginName());
                                                        String content = String.format("<img src='%s' />", loginUrl);
                                                        String smtp = "smtp.163.com";
                                                        String from = "vip_chenweizhi@163.com";
                                                        String to = u.getMail();
                                                        String username = "vip_chenweizhi@163.com";
                                                        String password = "qq328527654";
                                                        Mail.sendAndCc(smtp, from, to, null, subject, content, username, password);
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
                                        try {
                                            TimeUnit.SECONDS.sleep((int) (60 * Math.random() + 1));
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
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
