package com.hellobaby.library.data.model;

/**
 * Created by Administrator on 2017/3/14.
 */

public class AgentSendContentModel {

    /**
     * classId : 1
     * className : 大班1
     * babyName : jason的孩子
     * creatorName : 陈平
     * creatorPhoneNum : 13757650433
     */

    private String classId;
    private String className;
    private String babyName;
    private String creatorName;
    private String creatorPhoneNum;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorPhoneNum() {
        return creatorPhoneNum;
    }

    public void setCreatorPhoneNum(String creatorPhoneNum) {
        this.creatorPhoneNum = creatorPhoneNum;
    }
}
