package com.hellobaby.library.data.model;

import java.io.Serializable;

/**
 * Created by ShuWen on 2017/5/25.
 */

public class InfomationModel implements Serializable {

    /**
     * createTime : 1496650157000
     * infoImageurls :
     * subAlbumId : 4
     * state : 2  // 1 表示公司发布  2 表示家长发布
     * creatorId : 5
     * headImageurl : 7811fe95168945ada11d7ef5977858a1.jpg
     * detailsUrl :
     * infoId : 0
     * type : 2
     * commentNum :
     * content : 哈哈哈哈唉
     * title : 电脑
     * imageId : 40
     * likeNum :
     * name : 爸爸
     * commonId : 4
     * coverImageurl :
     * imageurl : db090b3f5c8e4c369a0d87439dc9c876.jpg,ca285bff8536456083360a05ee951fe8.jpg,06ab1970bf5f46bb8ab833a29b65ff97.jpg
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

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "InfomationModel{" +
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
                '}';
    }
}
