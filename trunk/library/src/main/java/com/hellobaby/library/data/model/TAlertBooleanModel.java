package com.hellobaby.library.data.model;

/**
 * Created by Administrator on 2017/4/25.
 */

public class TAlertBooleanModel {
//0有
    /**
     * school : 0
     * teaching : 0
     * sysmsg : 0
     * attendance : 0
     * msg : 0
     * evaluation : 0
     */

    private int school;
    private int teaching;
    private int sysmsg;
    private int attendance;
    private int msg;
    private int evaluation;

    public int getInfomsg() {
        return infomsg;
    }

    public void setInfomsg(int infomsg) {
        this.infomsg = infomsg;
    }

    private int infomsg;

    public int getSchool() {
        return school;
    }

    public void setSchool(int school) {
        this.school = school;
    }

    public int getTeaching() {
        return teaching;
    }

    public void setTeaching(int teaching) {
        this.teaching = teaching;
    }

    public int getSysmsg() {
        return sysmsg;
    }

    public void setSysmsg(int sysmsg) {
        this.sysmsg = sysmsg;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }
}
