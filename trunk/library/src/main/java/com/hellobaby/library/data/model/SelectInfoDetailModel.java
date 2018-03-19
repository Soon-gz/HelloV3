package com.hellobaby.library.data.model;

import java.util.List;

/**
 * Created by ShuWen on 2017/6/19.
 */

public class SelectInfoDetailModel {

    /**
     * createTime : 1497317985000
     * infoImageurls :
     * subAlbumId : 13
     * state : 2
     * creatorId : 5
     * detailsUrl :
     * infoId : 0
     * type : 2
     * commentNum : 9
     * content : 多张
     * title : 咨询图片
     * imageId : 50
     * likeNum : 2
     * commonId : 10
     * coverImageurl :
     * imageurl : 7ce1d7e13a384ac896bb884e7e6a263e.jpg,b81f6aef005542478e64f4329f87ae03.jpg,364e19baa76b4428ac5341002e142ea8.jpg,74d6e384088046d5b1bd353a7a000f57.jpg
     * commentList : [{"createTime":1497248752000,"toReplyUtype":"","topicType":2,"tInfoCommId":2,"toReplyUid":"","name":"朱鹏","topicId":13,"commentContent":"123321123123123","headImageurl":"f450c3b6d5c14d15b2ee7f444a70d005.jpg","commentUtype":1,"commentUid":11},{"createTime":1497248750000,"toReplyUtype":"","topicType":2,"tInfoCommId":3,"toReplyUid":"","name":"朱鹏","topicId":13,"commentContent":"111","headImageurl":"f450c3b6d5c14d15b2ee7f444a70d005.jpg","commentUtype":1,"commentUid":11},{"createTime":1497249306000,"toReplyUtype":2,"toName":"老师14","topicType":2,"tInfoCommId":4,"toReplyUid":1,"name":"朱鹏","topicId":13,"commentContent":"444","headImageurl":"f450c3b6d5c14d15b2ee7f444a70d005.jpg","commentUtype":1,"commentUid":11},{"createTime":1497249281000,"toReplyUtype":2,"toName":"老师14","topicType":2,"tInfoCommId":5,"toReplyUid":1,"topicId":13,"commentContent":"555","commentUtype":2,"commentUid":11}]
     */

    private long createTime;
    private String infoImageurls;
    private int subAlbumId;
    private int state;
    private int creatorId;
    private String headImageurl;
    private String detailsUrl;
    private int infoId;
    private int type;
    private String commentNum;
    private String content;
    private String title;
    private int imageId;
    private String likeNum;
    private String name;
    private int commonId;
    private String coverImageurl;
    private String imageurl;
    private int isLike;
    /**
     * createTime : 1497248752000
     * toReplyUtype :
     * topicType : 2
     * tInfoCommId : 2
     * toReplyUid :
     * name : 朱鹏
     * topicId : 13
     * commentContent : 123321123123123
     * headImageurl : f450c3b6d5c14d15b2ee7f444a70d005.jpg
     * commentUtype : 1
     * commentUid : 11
     */

    private List<CommentModel> commentList;


    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getInfoImageurls() {
        return infoImageurls;
    }

    public void setInfoImageurls(String infoImageurls) {
        this.infoImageurls = infoImageurls;
    }

    public int getSubAlbumId() {
        return subAlbumId;
    }

    public void setSubAlbumId(int subAlbumId) {
        this.subAlbumId = subAlbumId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public int getCommonId() {
        return commonId;
    }

    public void setCommonId(int commonId) {
        this.commonId = commonId;
    }

    public String getCoverImageurl() {
        return coverImageurl;
    }

    public void setCoverImageurl(String coverImageurl) {
        this.coverImageurl = coverImageurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public List<CommentModel> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentModel> commentList) {
        this.commentList = commentList;
    }


    @Override
    public String toString() {
        return "SelectInfoDetailModel{" +
                "createTime=" + createTime +
                ", infoImageurls='" + infoImageurls + '\'' +
                ", subAlbumId=" + subAlbumId +
                ", state=" + state +
                ", creatorId=" + creatorId +
                ", headImageurl='" + headImageurl + '\'' +
                ", detailsUrl='" + detailsUrl + '\'' +
                ", infoId=" + infoId +
                ", type=" + type +
                ", commentNum='" + commentNum + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", imageId=" + imageId +
                ", likeNum='" + likeNum + '\'' +
                ", name='" + name + '\'' +
                ", commonId=" + commonId +
                ", coverImageurl='" + coverImageurl + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", isLike=" + isLike +
                ", commentList=" + commentList +
                '}';
    }
}
