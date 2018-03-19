package com.hellobaby.library.data.model;

/**
 * Created by Administrator on 2017/5/31.
 */

public class ScheduleIdModel {

    /**
     * scheduleId : 2
     * creatorId : 4
     * scheduleDate : 1495036800000
     * createTime : 1496201088000
     * isAllDay : 0
     * title : 标题2
     * content : 标题2
     * remark : 标题3
     * scheduleStartDate : 1496218200000
     */

    private int scheduleId;
    private int creatorId;
    private long scheduleDate;
    private long createTime;
    private int isAllDay;
    private String title;
    private String content;
    private String remark;
    private long scheduleStartDate;

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public long getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(long scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getIsAllDay() {
        return isAllDay;
    }

    public void setIsAllDay(int isAllDay) {
        this.isAllDay = isAllDay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getScheduleStartDate() {
        return scheduleStartDate;
    }

    public void setScheduleStartDate(long scheduleStartDate) {
        this.scheduleStartDate = scheduleStartDate;
    }
}
