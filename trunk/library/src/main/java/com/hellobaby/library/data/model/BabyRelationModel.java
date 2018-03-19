package com.hellobaby.library.data.model;

import java.io.Serializable;

/**
 * Created by ShuWen on 2017/3/13.
 */

public class BabyRelationModel implements Serializable{

    /**
     * pickUpId : 4
     * creatorId : 1
     * babyId : 1
     * qrcode : ECCBC87E4B5CE2FE28308FD9F2A7BAF3892
     * userName : wocao1
     * phoneNum : 135555566634
     * relation : grandpa1
     * headImageurl : null
     * createTime : 1489397318000
     */

    private int pickUpId;
    private int creatorId;
    private int babyId;
    private String qrcode;
    private String userName;
    private String phoneNum;
    private String relation;
    private String headImageurl;
    private long createTime;

    public int getPickUpId() {
        return pickUpId;
    }

    public void setPickUpId(int pickUpId) {
        this.pickUpId = pickUpId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getBabyId() {
        return babyId;
    }

    public void setBabyId(int babyId) {
        this.babyId = babyId;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
