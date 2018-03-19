package com.hellobaby.library.data.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/8.
 */

public class MessageModel implements Serializable {

    /**
     * messageId : 5
     * headImageurl : 1362e8fa1f344cd6af4679b2f56ead3b.jpg
     * teacherName : honghongaa
     * sendTime : 2017-01-04 11:31:43
     * title : t2c0202
     * content : t2c02t2c02t2c02
     * isRead : 0
     */

    private int messageId;
    private String headImageurl;
    private String teacherName;
    private String sendTime;
    private String title;
    private String content;
    private String uName;
    private boolean isSelected = false;
    /**
     * className : 大班2
     * readNum : 1
     * totalNum : 3
     */

    private String className;
    private int readNum;
    private int totalNum;
    /**
     * 发件人的类型
     * 1:学校 2:教师 3:家长
     */
    private int sendType;

    public int getSendType() {
        return sendType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * 学校
     */
    public boolean getSendTypeSchool() {
        return sendType == 1;
    }

    /**
     * 教师
     *
     * @return
     */
    public boolean getSendTypeTeacher() {
        return sendType == 2;
    }

    /**
     * 用户
     *
     * @return
     */
    public boolean getSendTypeUser() {
        return sendType == 3;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    private int isRead;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public boolean isEmptyHeadImageurl() {
        return (headImageurl==null||headImageurl.isEmpty());
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
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

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "messageId=" + messageId +
                ", headImageurl='" + headImageurl + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", uName='" + uName + '\'' +
                ", isRead=" + isRead +
                '}';
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
