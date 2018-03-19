package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/6/12.
 */

public class CommentModel {

    /**
     * createTime : 1497249281000
     * toReplyUtype : 2
     * toName : 老大一号
     * topicType : 2
     * tInfoCommId : 5
     * toReplyUid : 1
     * name : 朱鹏
     * topicId : 13
     * commentContent : 555
     * headImageurl : f450c3b6d5c14d15b2ee7f444a70d005.jpg
     * commentUtype : 2
     * commentUid : 11
     * topicType=2    资讯1 相册2
     topicId=13    对应id
     commentUtype=1     评论或回复的人的类型 1：家长 2：老师
     commentContent=123321123123123
     toReplyId  被回复的id
     toReplyType   被回复的类型 1：家长 2：老师
     如果为评论则 toReplyId和toReplyType为空
     */

    private long createTime;
    private String toReplyUtype;
    private String toName;
    private int topicType;
    private int tInfoCommId;
    private String toReplyUid;
    private String name;
    private int topicId;
    private String commentContent;
    private String headImageurl;
    private int commentUtype;
    private int commentUid;

    public int gettInfoCommId() {
        return tInfoCommId;
    }

    public void settInfoCommId(int tInfoCommId) {
        this.tInfoCommId = tInfoCommId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getToReplyUtype() {
        return toReplyUtype;
    }

    public void setToReplyUtype(String toReplyUtype) {
        this.toReplyUtype = toReplyUtype;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public int getTopicType() {
        return topicType;
    }

    public void setTopicType(int topicType) {
        this.topicType = topicType;
    }

    public int getTInfoCommId() {
        return tInfoCommId;
    }

    public void setTInfoCommId(int tInfoCommId) {
        this.tInfoCommId = tInfoCommId;
    }

    public String getToReplyUid() {
        return toReplyUid;
    }

    public void setToReplyUid(String toReplyUid) {
        this.toReplyUid = toReplyUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public int getCommentUtype() {
        return commentUtype;
    }

    public void setCommentUtype(int commentUtype) {
        this.commentUtype = commentUtype;
    }

    public int getCommentUid() {
        return commentUid;
    }

    public void setCommentUid(int commentUid) {
        this.commentUid = commentUid;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "createTime=" + createTime +
                ", toReplyUtype='" + toReplyUtype + '\'' +
                ", toName='" + toName + '\'' +
                ", topicType=" + topicType +
                ", tInfoCommId=" + tInfoCommId +
                ", toReplyUid='" + toReplyUid + '\'' +
                ", name='" + name + '\'' +
                ", topicId=" + topicId +
                ", commentContent='" + commentContent + '\'' +
                ", headImageurl='" + headImageurl + '\'' +
                ", commentUtype=" + commentUtype +
                ", commentUid=" + commentUid +
                '}';
    }
}
