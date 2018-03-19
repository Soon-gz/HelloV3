package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/2/15.
 */

public class AdviceModel {
    private String content;
    private String phoneNum;
    private  String email;
    private String creatorId;
    private String type;
    private String device;

    public AdviceModel(String content, String phoneNum, String email, String creatorId, String type, String device) {
        this.content = content;
        this.phoneNum = phoneNum;
        this.email = email;
        this.creatorId = creatorId;
        this.type = type;
        this.device = device;
    }

    public AdviceModel() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatatorId() {
        return creatorId;
    }

    public void setCreatatorId(String creatatorId) {
        this.creatorId = creatatorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
