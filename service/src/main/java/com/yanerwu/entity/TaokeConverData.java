package com.yanerwu.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author Zuz
 * @Date 2017/7/26 23:15
 * @Description
 */
public class TaokeConverData {

    private boolean status;
    @JSONField(name = "data")
    private TaokeConverLink data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public TaokeConverLink getData() {
        return data;
    }

    public void setData(TaokeConverLink data) {
        this.data = data;
    }
}
