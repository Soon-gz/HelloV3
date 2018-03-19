package com.hellobaby.library.data.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/13.
 */

public class TimeCardModel implements Serializable{
    protected final static int TC_TPYE_ARRIVAL=0;
    protected final static int TC_TPYE_LEAVE=1;
    public final static int TC_TPYE_USER=1;
    public final static int TC_TPYE_AGENT=2;
    public final static int TC_TPYE_CARD=3;
    /**
     * eventType : 1
     * personType : 1
     * time : 1489395568000
     * headImageUrl : 9c8dc29bc11f4805870a20b4f0a03581.png
     * relation :
     * phoneNum :
     * timeCardImgUrl : ccccc.jpg
     * timeStr : 2017-03-13 16:59:28
     */

    private int eventType;
    private int personType;
    private long time;
    private String headImageUrl;
    private String relation;
    private String phoneNum;
    private String timeCardImgUrl;
    private String timeStr;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventType() {
        return eventType==TC_TPYE_ARRIVAL?"到校":"离校";
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getPersonType() {
        return personType;
    }

    public void setPersonType(int personType) {
        this.personType = personType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getTimeCardImgUrl() {
        return timeCardImgUrl;
    }

    public void setTimeCardImgUrl(String timeCardImgUrl) {
        this.timeCardImgUrl = timeCardImgUrl;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}
