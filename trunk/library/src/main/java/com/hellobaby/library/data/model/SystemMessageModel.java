package com.hellobaby.library.data.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/9.
 */

public class SystemMessageModel implements Serializable{

    /**
     * sysmsgId : 3
     * headImageurl : b1d237c64cad491b941aba60996a17c0.bmp
     * senderName : 智塾科技
     * title : toAll company02
     * content : toAll company02toAll company02
     * sendTime : 1483432860000
     * isRead : 1
     */

    private int sysmsgId;
    private String headImageurl;
    private String senderName;
    private String title;
    private String content;
    private String sendTime;
    private int isRead;

    public int getSysmsgId() {
        return sysmsgId;
    }

    public void setSysmsgId(int sysmsgId) {
        this.sysmsgId = sysmsgId;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }
}
