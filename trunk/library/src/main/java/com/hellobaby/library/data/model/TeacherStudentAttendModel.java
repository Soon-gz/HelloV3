package com.hellobaby.library.data.model;

/**
 * Created by Administrator on 2017/5/3.
 */

public class TeacherStudentAttendModel {
    /**
     * babyId : 9
     * classId : 1
     * headImageurl :
     * babyName : adfsa
     * babyGender : 1
     * birthday : 1421251200000
     * address : 151
     * createTime : 1492694688000
     * phoneNum : 15323123123
     * enterTime : null
     * outTime : null
     * isLeave : false
     * dataType : 0
     */

    private int babyId;
    private int classId;
    private String headImageurl;
    private String babyName;
    private int babyGender;
    private long birthday;
    private String address;
    private long createTime;
    private String phoneNum;
    private long enterTime;
    private long outTime;
    private int isLeave;
    private int dataType;

    public int getBabyId() {
        return babyId;
    }

    public void setBabyId(int babyId) {
        this.babyId = babyId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public int getBabyGender() {
        return babyGender;
    }

    public void setBabyGender(int babyGender) {
        this.babyGender = babyGender;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public long getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(long enterTime) {
        this.enterTime = enterTime;
    }

    public long getOutTime() {
        return outTime;
    }

    public void setOutTime(long outTime) {
        this.outTime = outTime;
    }

    public int getIsLeave() {
        return isLeave;
    }

    public void setIsLeave(int isLeave) {
        this.isLeave = isLeave;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
