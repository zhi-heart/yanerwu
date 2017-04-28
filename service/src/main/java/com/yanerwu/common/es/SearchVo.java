package com.yanerwu.common.es;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Author: Zuz
 * @Description:
 * @Date: 2017/4/27 17:26
 */
public class SearchVo implements Serializable{
    private static final long serialVersionUID = 5131462271721293855L;

    private String[] searchKeys = new String[1];
    private String[] mustKeys = new String[1];
    private Integer[] type = new Integer[]{1};
    private long startTime = 0;//毫秒数
    private String sort = "";
    private boolean highlight=true;//高亮


    public int getType() {
        return type[0];
    }

    public Integer[] getTypes() {
        return type;
    }

    public void setType(Integer... type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String[] getSearchKeys() {
        return searchKeys;
    }

    public String getSearchText() {
        return searchKeys[0];
    }

    public void setSearchKey(String... searchKey) {
        this.searchKeys = searchKey;
    }

    public void setMustKey(String... mustKey) {
        this.mustKeys = mustKey;
    }

    public String[] getMustKeys() {
        return mustKeys;
    }

    public String getMustKey() {
        return mustKeys[0];
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    @Override
    public String toString() {
        return "SearchVo{" +
                "searchKeys=" + Arrays.toString(searchKeys) +
                ", mustKeys=" + Arrays.toString(mustKeys) +
                ", type=" + Arrays.toString(type) +
                ", startTime=" + startTime +
                ", sort='" + sort + '\'' +
                ", highlight=" + highlight +
                '}';
    }
}