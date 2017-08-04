package com.yanerwu.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author Zuz
 * @Date 2017/7/26 23:15
 * @Description
 */
public class TaokeConverLink {

    @JSONField(name = "qq_taokouling")
    private String password;
    @JSONField(name = "qq_link")
    private String link;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
