package com.yanerwu.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yanerwu.annotation.Column;
import com.yanerwu.annotation.Table;
import com.yanerwu.common.BaseProcessor;
import com.yanerwu.common.DbUtilsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Zuz
 * @Date 2017/7/7 16:23
 * @Description 根据起点, 获取爬取站id, url
 */
public class YYSRandProcessor extends BaseProcessor implements PageProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private DbUtilsTemplate bookTemplate;

    public YYSRandProcessor(DbUtilsTemplate bookTemplate) {
        this.bookTemplate = bookTemplate;
    }

    @Override
    public void process(Page page) {
        List<RandDTO> randDTOList = new ArrayList<>();
        JSONObject resultJSON = JSON.parseObject(page.getRawText());
        JSONArray datas = resultJSON.getJSONArray("data");
        for (int i = 0; i < datas.size(); i++) {
            try {
                JSONObject jsonObject = datas.getJSONObject(i);
                String time = jsonObject.getString("get_time");

                String reqId = jsonObject.getString("req_id");

                String nick = jsonObject.getJSONObject("user_info").getString("nick");
                String uid = jsonObject.getJSONObject("user_info").getString("uid");
                String server = jsonObject.getJSONObject("user_info").getString("server");

                String propName = jsonObject.getJSONObject("prop_info").getString("prop_name");
                String from = jsonObject.getJSONObject("prop_info").getString("from");

//            logger.info("{} {} {} {} {} {} {}", time, reqId, nick, uid, server, propName, from);

                RandDTO r = new RandDTO();
                r.setTime(time);
                r.setReqId(reqId);
                r.setNick(nick);
                r.setUid(uid);
                r.setServer(server);
                r.setPropName(propName);
                r.setFrom(from);
                randDTOList.add(r);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        if (randDTOList.size() > 0) {
            bookTemplate.insert(randDTOList);
        }

    }

    @Override
    public Site getSite() {
        Site site = super.getSite();
        site.setSleepTime(1000);
        return site;
    }
}

@Table(name = "yys_rand")
class RandDTO {
    @Column(name = "time")
    private String time;
    @Column(name = "reg_id")
    private String reqId;
    @Column(name = "nick")
    private String nick;
    @Column(name = "uid")
    private String uid;
    @Column(name = "server")
    private String server;
    @Column(name = "prop_name")
    private String propName;
    @Column(name = "from")
    private String from;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}