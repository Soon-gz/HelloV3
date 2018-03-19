package com.hellobaby.library.data.model;

/**
 * Created by Administrator on 2017/1/4.
 */

public class BabyAttendancesModel {
    /**
     * teacherId : 1
     * babyId : 1
     * babyName : jason
     * headImageurl : baby001.jpg
     * state : 1
     */

    private int teacherId;
    private int babyId;
    private String babyName;
    private String headImageurl;
    private int state;

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getBabyId() {
        return babyId;
    }

    public void setBabyId(int babyId) {
        this.babyId = babyId;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
