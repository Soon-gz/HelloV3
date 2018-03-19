package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/5/8.
 */

public class PrizeHistoryBean {

    /**
     * recordId : 242
     * createTime : 1493718694000
     * state : 1
     * linePoints : 100
     * type : 4
     * teacherId : 4
     *
     * 1 完成宝宝评语：+5 ；2 发布校园动态：+2 ； 3 分享app：+2 （每日一次）； 4 抽奖一次 -100
     */

    private int recordId;
    private long createTime;
    private int state;
    private int linePoints;
    private int type;
    private int teacherId;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getLinePoints() {
        return linePoints;
    }

    public void setLinePoints(int linePoints) {
        this.linePoints = linePoints;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
