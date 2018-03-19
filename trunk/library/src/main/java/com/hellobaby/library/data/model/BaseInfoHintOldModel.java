package com.hellobaby.library.data.model;

import java.util.List;

/**
 * Created by ShuWen on 2017/6/21.
 */

public class BaseInfoHintOldModel {

    /**
     * pageNum : 1
     * pageSize : 15
     * startRow : 0
     * endRow : 15
     * total : 0
     * pages : 1
     */

    private PageBean page;
    /**
     * createTime : 1497947388000
     * toReplyUtype : 1
     * toName : 朱鹏
     * topicType : 2
     * tInfoCommId : 13
     * toReplyUid : 11
     * name : 爸爸
     * topicId : 23
     * commentContent : 你哪的
     * headImageurl : 7811fe95168945ada11d7ef5977858a1.jpg
     * commentUtype : 1
     * commentUid : 5
     */

    private List<BaseInfoHintModel> commList;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<BaseInfoHintModel> getCommList() {
        return commList;
    }

    public void setCommList(List<BaseInfoHintModel> commList) {
        this.commList = commList;
    }

    public static class PageBean {
        private int pageNum;
        private int pageSize;
        private int startRow;
        private int endRow;
        private int total;
        private int pages;

        public int getPageNum() {
            return pageNum;
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

}
