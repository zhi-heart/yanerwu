package com.yanerwu.common.es;

import java.util.List;
import java.util.Set;


public class Pagination<T> implements java.io.Serializable {
    private static final long serialVersionUID = 678923557081175954L;

    private int pageSize = 10; //每页显示记录数
    private int currentPage = 1;//当前页
    private int maxElements = 0;//总记录数
    private int totalPage = 0;//总页数
    private int liststep = 2;
    private int listbegin = 0;
    private int listend = 0;
    private int offset = 0;
    private List<T> list;
    private Set<?> set;

    /**
     * @param @return
     * @return int    返回类型
     * @throws
     * @Title: getTotalPage
     * @Description: 获取总页数
     */
    public int getTotalPage() {
        if (this.maxElements % this.pageSize == 0) {
            this.totalPage = this.maxElements / this.pageSize;
        } else {
            this.totalPage = this.maxElements / this.pageSize + 1;
        }
        return totalPage;
    }

    /**
     * @param @param currentPage
     * @return void    返回类型
     * @throws
     * @Title: setCurrentPage
     * @Description: 设置当前页
     */
    public void setCurrentPage(int currentPage) {
        if (currentPage < 1) {
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }
    }

    public int getListbegin() {
        listbegin = (currentPage - (int) Math.ceil((double) liststep / 2));
        if (listbegin < 1) {
            listbegin = 1;
        }
        return listbegin;
    }

    public int getListend() {
        listend = currentPage + liststep / 2;
        if (listend > totalPage) {
            listend = totalPage;
        }
        return listend;
    }

    public Pagination() {

    }

    public Pagination(int maxElements) {
        this.maxElements = maxElements;
    }

    public Pagination(int pageSize, int currentPage, int maxElements) {
        this.pageSize = pageSize;
        this.maxElements = maxElements;
        this.currentPage = currentPage;
    }

    public int getListstep() {
        return liststep;
    }

    public void setListstep(int liststep) {
        this.liststep = liststep;
    }

    public void setListbegin(int listbegin) {
        this.listbegin = listbegin;
    }

    public void setListend(int listend) {
        this.listend = listend;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (currentPage >= totalPage) {
            currentPage = totalPage;
        }
        return currentPage;
    }

    public void setMaxElements(int maxElements) {
        this.maxElements = maxElements;
    }

    public int getMaxElements() {
        return maxElements;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        if (offset <= 0) {
            this.offset = 0;
        } else if (offset > maxElements) {
            this.offset = maxElements - 1;
        } else {
            this.offset = offset;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @SuppressWarnings("rawtypes")
    public void setList(List<T> list) {
        this.list = list;
    }

    @SuppressWarnings("rawtypes")
    public List<T> getList() {
        return list;
    }

    @SuppressWarnings("rawtypes")
    public Set getSet() {
        return set;
    }

    @SuppressWarnings("rawtypes")
    public void setSet(Set set) {
        this.set = set;
    }

}
