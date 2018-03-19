package com.hellobaby.library.data.model;

/**
 * Created by Administrator on 2017/4/25.
 */

public class TAlertModel {
    private Integer alertId;

    private Integer userId;

    private Integer babyId;

    public Integer getBabyId() {
        return babyId;
    }

    public void setBabyId(Integer babyId) {
        this.babyId = babyId;
    }

    private Long schoolLastMaxTime;

    private Long msgLastMaxTime;

    private Long evaluationLastMaxTime;

    private Long teachingLastMaxTime;

    private Long attendanceLastMaxTime;

    private Long sysmsgLastMaxTime;

    public Integer getAlertId() {
        return alertId;
    }

    public void setAlertId(Integer alertId) {
        this.alertId = alertId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getSchoolLastMaxTime() {
        return schoolLastMaxTime;
    }

    public void setSchoolLastMaxTime(Long schoolLastMaxTime) {
        this.schoolLastMaxTime = schoolLastMaxTime;
    }

    public Long getMsgLastMaxTime() {
        return msgLastMaxTime;
    }

    public void setMsgLastMaxTime(Long msgLastMaxTime) {
        this.msgLastMaxTime = msgLastMaxTime;
    }

    public Long getEvaluationLastMaxTime() {
        return evaluationLastMaxTime;
    }

    public void setEvaluationLastMaxTime(Long evaluationLastMaxTime) {
        this.evaluationLastMaxTime = evaluationLastMaxTime;
    }

    public Long getTeachingLastMaxTime() {
        return teachingLastMaxTime;
    }

    public void setTeachingLastMaxTime(Long teachingLastMaxTime) {
        this.teachingLastMaxTime = teachingLastMaxTime;
    }

    public Long getAttendanceLastMaxTime() {
        return attendanceLastMaxTime;
    }

    public void setAttendanceLastMaxTime(Long attendanceLastMaxTime) {
        this.attendanceLastMaxTime = attendanceLastMaxTime;
    }

    public Long getSysmsgLastMaxTime() {
        return sysmsgLastMaxTime;
    }

    public void setSysmsgLastMaxTime(Long sysmsgLastMaxTime) {
        this.sysmsgLastMaxTime = sysmsgLastMaxTime;
    }
}
