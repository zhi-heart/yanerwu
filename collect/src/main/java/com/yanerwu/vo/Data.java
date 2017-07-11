package com.yanerwu.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Zuz
 * @Date 2017/7/7 16:28
 * @Description
 */
public class Data {
    private int total;
    private int isLast;
    private int pageNum;
    private List<Record> records = new ArrayList<>();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getIsLast() {
        return isLast;
    }

    public void setIsLast(int isLast) {
        this.isLast = isLast;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
