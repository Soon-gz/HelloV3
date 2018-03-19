package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/6/26.
 */

public class InfoChildHeartModel {
    private int position;
    private int isLike;
    private int likeNum;
    private String commentNum;
    private boolean isLikeClick = false;

    public InfoChildHeartModel(int position, int isLike,int likeNum,boolean isLikeClick) {
        this.position = position;
        this.isLike = isLike;
        this.likeNum = likeNum;
        this.isLikeClick = isLikeClick;
    }
    public InfoChildHeartModel(int position,String commentNum,boolean isLikeClick) {
        this.position = position;
        this.commentNum = commentNum;
        this.isLikeClick = isLikeClick;
    }

    public boolean isLikeClick() {
        return isLikeClick;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public int getPosition() {
        return position;
    }

    public int getIsLike() {
        return isLike;
    }
}
