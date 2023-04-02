package com.cyf.plugin;

/**
 * @author 陈一锋
 * @date 2021/9/4 5:01 下午
 */
public class Page {
    private int total;
    private int pageSize;
    private int pageNum;

    public Page(int pageSize, int pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getOffset() {
        return pageSize * (pageNum - 1);
    }
}
