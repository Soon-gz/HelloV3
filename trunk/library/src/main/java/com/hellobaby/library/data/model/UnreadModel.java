package com.hellobaby.library.data.model;

import com.hellobaby.library.Const;

/**
 * Created by Administrator on 2017/1/12.
 */

public class UnreadModel {
    /**
     * messageId : 2
     * classId : 2
     * babyId : 11
     * userId : 1
     * headImageurl : 530a250f82f743b59e1dd76d21617e97.jpg
     * userName : chenping
     * phoneNum : 13757650411
     */

    private int messageId;
    private int classId;
    private int babyId;
    private int userId;
    private String headImageurl;
    private String headImgUrlAbs;//头像绝对的Url
    private String babyRelation;//宝宝名字的家长

    public String getHeadImgUrlAbs() {
        headImgUrlAbs = Const.URL_userHead + headImageurl;
        return headImgUrlAbs;
    }
    private String userName;
    private String phoneNum;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getBabyId() {
        return babyId;
    }

    public void setBabyId(int babyId) {
        this.babyId = babyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getBabyRelation() {
        return babyRelation;
    }

    public void setBabyRelation(String babyRelation) {
        this.babyRelation = babyRelation;
    }
}
