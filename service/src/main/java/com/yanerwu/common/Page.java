package com.yanerwu.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Page<T> implements Serializable, Iterable<T> {

    private static final long serialVersionUID = 1L;
    /**
     * 返回结果
     */
    private List<T> result;
    /**
     * 总条数
     */
    private long totalCount;
    /**
     * 每页显示多少条
     */
    private long numPerPage = 500;
    /**
     * 页标数字多少个
     */
    private long pageNumShown;
    /**
     * 当前是第几页
     */
    private long currentPage = 1;
    /**
     * 页面传过来要去的第几页
     */
    private long pageNum = 1;

    private String orderField;

    private String orderDirection;


    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(long numPerPage) {
        this.numPerPage = numPerPage;
    }

    public long getPageNumShown() {
        return pageNumShown;
    }

    public void setPageNumShown(long pageNumShown) {
        this.pageNumShown = pageNumShown;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return result == null ? (Iterator<T>) Collections.emptyList().iterator() : result.iterator();
    }


}
