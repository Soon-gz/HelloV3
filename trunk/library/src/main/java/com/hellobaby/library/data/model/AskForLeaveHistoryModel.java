package com.hellobaby.library.data.model;

import java.io.Serializable;

/**
 * Created by ShuWen on 2017/10/31.
 */

public class AskForLeaveHistoryModel implements Serializable {

    /**
     * leaveId : 9
     * teacherId : 4
     * createTime : 1510156800000
     * startTime : 1000000000
     * endTime : 2000000000
     * type : 1  1 病假 2 事假
     * leaveReason : 原因
     * state : 0  0:待批准 1：已批准 2：已拒绝
     * leaveTime : null
     */

    private int leaveId;
    private int teacherId;
    private long createTime;
    private long startTime;
    private long endTime;
    private int type;
    private String leaveReason;
    private int state;
    private long leaveTime;

    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(long leaveTime) {
        this.leaveTime = leaveTime;
    }
}
