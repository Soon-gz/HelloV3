package com.hellobaby.library.data.model;

import java.util.List;

/**
 * Created by ShuWen on 2017/7/4.
 */

public class AttendenceHistoryModel {

    /**
     * pageNum : 1
     * pageSize : 15
     * startRow : 0
     * endRow : 15
     * total : 2
     * pages : 1
     */

    private PageBean page;
    /**
     * teacherattendanceId : 16
     * teacherId : 3
     * amstate : 1
     * createTime : 1499137250000
     * timeCardImgUrl : 302a7659dd4c4d349dc9e6ea1cacb4b9.jpg
     * qrcode : null
     * pmstate : 3
     * amTime : null
     * pmTime : 1499148003000
     * teacherName : 李松阳123456
     */

    private List<ListBean> list;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
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

    public static class ListBean {
        private int teacherattendanceId;
        private int teacherId;
        private int amstate;
        private long createTime;
        private String timeCardImgUrl;
        private String timeCardImgUrlPm;
        private String qrcode;
        private int pmstate;
        private long amTime;
        private long pmTime;
        private String teacherName;

        public int getTeacherattendanceId() {
            return teacherattendanceId;
        }

        public void setTeacherattendanceId(int teacherattendanceId) {
            this.teacherattendanceId = teacherattendanceId;
        }

        public String getTimeCardImgUrlPm() {
            return timeCardImgUrlPm;
        }

        public void setTimeCardImgUrlPm(String timeCardImgUrlPm) {
            this.timeCardImgUrlPm = timeCardImgUrlPm;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public int getAmstate() {
            return amstate;
        }

        public void setAmstate(int amstate) {
            this.amstate = amstate;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getTimeCardImgUrl() {
            return timeCardImgUrl;
        }

        public void setTimeCardImgUrl(String timeCardImgUrl) {
            this.timeCardImgUrl = timeCardImgUrl;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public int getPmstate() {
            return pmstate;
        }

        public void setPmstate(int pmstate) {
            this.pmstate = pmstate;
        }

        public long getAmTime() {
            return amTime;
        }

        public void setAmTime(long amTime) {
            this.amTime = amTime;
        }

        public long getPmTime() {
            return pmTime;
        }

        public void setPmTime(long pmTime) {
            this.pmTime = pmTime;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "teacherattendanceId=" + teacherattendanceId +
                    ", teacherId=" + teacherId +
                    ", amstate=" + amstate +
                    ", createTime=" + createTime +
                    ", timeCardImgUrl='" + timeCardImgUrl + '\'' +
                    ", timeCardImgUrlPm='" + timeCardImgUrlPm + '\'' +
                    ", qrcode='" + qrcode + '\'' +
                    ", pmstate=" + pmstate +
                    ", amTime=" + amTime +
                    ", pmTime=" + pmTime +
                    ", teacherName='" + teacherName + '\'' +
                    '}';
        }
    }
}
