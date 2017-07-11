package com.yanerwu.vo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author Zuz
 * @Date 2017/7/7 16:27
 * @Description
 */
public class Record {
    @JSONField(name = "bid")
    private int id;
    @JSONField(name = "bName")
    private String name;
    @JSONField(name = "bAuth")
    private String auth;
    @JSONField(name = "desc")
    private String desc;
    @JSONField(name = "cat")
    private String cat;
    @JSONField(name = "catId")
    private Integer catId;
    @JSONField(name = "rankCnt")
    private String rankCnt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getRankCnt() {
        return rankCnt;
    }

    public void setRankCnt(String rankCnt) {
        this.rankCnt = rankCnt;
    }
}
