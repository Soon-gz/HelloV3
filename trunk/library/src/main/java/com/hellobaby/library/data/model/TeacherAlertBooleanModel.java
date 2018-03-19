package com.hellobaby.library.data.model;

/**
 * Created by Administrator on 2017/4/27.
 */

public class TeacherAlertBooleanModel {
    /**
     * school : 0
     * sysmsg : 1
     * msg : 0
     */

    private int school;
    private int sysmsg;
    private int msg;

    public int getSchool() {
        return school;
    }

    public void setSchool(int school) {
        this.school = school;
    }

    public int getSysmsg() {
        return sysmsg;
    }

    public void setSysmsg(int sysmsg) {
        this.sysmsg = sysmsg;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }
}
