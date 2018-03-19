package com.hellobaby.library.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/1.
 */

public class EventModel implements Serializable {
    /**
     * eventId : 28
     * creatorId : 1
     * contactorId : 1
     * classId : 1
     * eventTitle : 回到家不对劲
     * eventStartTime : 1483286400000
     * eventEndTime : 1483372800000
     * eventAddress : 不会的
     * eventFee : 500
     * eventPeople : 25
     * eventDeadlineTime : 1483286400000
     * createTime : 1483341751000
     * eventDetails : 不对劲绝对不会大家
     * teacherName : 老师1
     * headImageurl : /asd
     * phoneNum : 18989619202
     */

    private int eventId;
    private int creatorId;
    private int contactorId;
    private int classId;
    private String eventTitle;
    private long eventStartTime;
    private long eventEndTime;
    private String eventAddress;
    private double eventFee;
    private int eventPeople;
    private long eventDeadlineTime;
    private long createTime;
    private String eventDetails;
    private String teacherName;
    private String headImageurl;
    private String phoneNum;
    private String isjoin;
    private int eventBaby;
    /**
     * eventFee : 12
     * babyNumber : 5
     * parentsNumber : 8
     * isJoin : null
     */

//    @SerializedName("eventFee")
//    private int eventFeeX;
    private int babyNumber;
    private int parentsNumber;
    private Object isJoin;

    public int getEventBaby() {
        return eventBaby;
    }

    public void setEventBaby(int eventBaby) {
        this.eventBaby = eventBaby;
    }
    //    private String eventPeople;

    public String getIsjoin() {
        return isjoin;
    }

    public void setIsjoin(String isjoin) {
        this.isjoin = isjoin;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getContactorId() {
        return contactorId;
    }

    public void setContactorId(int contactorId) {
        this.contactorId = contactorId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public long getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(long eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public long getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(long eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public double getEventFee() {
        return eventFee;
    }

    public void setEventFee(double eventFee) {
        this.eventFee = eventFee;
    }

    public int getEventPeople() {
        return eventPeople;
    }

    public void setEventPeople(int eventPeople) {
        this.eventPeople = eventPeople;
    }

    public long getEventDeadlineTime() {
        return eventDeadlineTime;
    }

    public void setEventDeadlineTime(long eventDeadlineTime) {
        this.eventDeadlineTime = eventDeadlineTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

//    public int getEventFeeX() {
//        return eventFeeX;
//    }
//
//    public void setEventFeeX(int eventFeeX) {
//        this.eventFeeX = eventFeeX;
//    }

    public int getBabyNumber() {
        return babyNumber;
    }

    public void setBabyNumber(int babyNumber) {
        this.babyNumber = babyNumber;
    }

    public int getParentsNumber() {
        return parentsNumber;
    }

    public void setParentsNumber(int parentsNumber) {
        this.parentsNumber = parentsNumber;
    }

    public Object getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(Object isJoin) {
        this.isJoin = isJoin;
    }
}
