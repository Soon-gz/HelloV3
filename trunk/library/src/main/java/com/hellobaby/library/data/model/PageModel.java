package com.hellobaby.library.data.model;

/**
 * Created by Administrator on 2017/2/9.
 */

public class PageModel {
    /**
     * pageNum : 2
     * pageSize : 15
     * startRow : 15
     * endRow : 30
     * total : 161
     * pages : 11
     */

    private int pageNum;
    private int pageSize;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;

    public int getPageNum() {
        return pageNum==0?1:pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
