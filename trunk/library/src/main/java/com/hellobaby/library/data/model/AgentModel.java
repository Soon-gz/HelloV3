package com.hellobaby.library.data.model;

/**
 * Created by Administrator on 2017/3/14.
 */

public class AgentModel {

    /**
     * babyId : 1
     * type : 0
     * userName : testUsername
     * phoneNum : 15088689998
     * agentTime : 2017-03-10
     * description :
     * creatorId : 2
     */

    private String babyId;
    private String type;
    private String userName;
    private String phoneNum;
    private String agentTime;
    private String description;
    private String creatorId;

    public String getBabyId() {
        return babyId;
    }

    public void setBabyId(String babyId) {
        this.babyId = babyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAgentTime() {
        return agentTime;
    }

    public void setAgentTime(String agentTime) {
        this.agentTime = agentTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
}
