package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/6/21.
 */

public class BaseInfoHintModel {

    /**
     * createTime : 1497947388000
     * toName : 朱鹏
     * headImageurl : 7811fe95168945ada11d7ef5977858a1.jpg
     * type : 2
     * commentUtype : 1
     * commentUid : 5
     * toReplyUtype : 1
     * tInfoCommId : 13
     * topicType : 2
     * toReplyUid : 11
     * name : 爸爸
     * commentContent : 你哪的
     * topicId : 23
     * coverImageUrl : 3ad8beec61634208bd672a48d877940f.jpg
     */

    private long createTime;
    private String toName;
    private String headImageurl;
    private int type;
    private int commentUtype;
    private int commentUid;
    private String toReplyUtype;
    private int tInfoCommId;
    private int topicType;
    private String toReplyUid;
    private String name;
    private String commentContent;
    private int topicId;
    private String coverImageUrl;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getToReplyUtype() {
        return toReplyUtype;
    }

    public void setToReplyUtype(String toReplyUtype) {
        this.toReplyUtype = toReplyUtype;
    }

    public int getTInfoCommId() {
        return tInfoCommId;
    }

    public void setTInfoCommId(int tInfoCommId) {
        this.tInfoCommId = tInfoCommId;
    }

    public int getTopicType() {
        return topicType;
    }

    public void setTopicType(int topicType) {
        this.topicType = topicType;
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

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }
}
