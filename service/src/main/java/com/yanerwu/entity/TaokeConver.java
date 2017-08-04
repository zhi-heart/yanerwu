package com.yanerwu.entity;

/**
 * @Author Zuz
 * @Date 2017/7/26 23:15
 * @Description
 */
public class TaokeConver {

    private TaokeConverData data;
    private String status;
    private String check;
    private String info;
    private String message;

    public TaokeConverData getData() {
        return data;
    }

    public void setData(TaokeConverData data) {
        this.data = data;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
