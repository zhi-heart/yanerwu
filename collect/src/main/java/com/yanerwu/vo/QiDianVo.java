package com.yanerwu.vo;

/**
 * @Author Zuz
 * @Date 2017/7/7 16:12
 * @Description
 */
public class QidianVo {
    private Integer code;
    private String msg;
    private Data data = new Data();

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}