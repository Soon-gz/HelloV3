package com.hellobaby.library.data.model;

import java.util.List;

/**
 * Created by ShuWen on 2017/6/6.
 */

public class InfoPersonMsgModel {

    /**
     * careCount : 0
     * headImageurl : 7811fe95168945ada11d7ef5977858a1.jpg
     * userName : 爸爸
     * list : [{"subAlbumId":6,"commonId":4,"createTime":1496650660000,"type":2,"commentNum":null,"likeNum":null,"creatorId":5,"title":"电脑","content":"哈哈哈哈唉","imageId":43,"coverImageurl":null,"imageurl":"47d7fd1b94d04725a9c54e373208fab0.jpg","infoId":null,"detailsUrl":null,"infoImageurls":null,"state":null},{"subAlbumId":5,"commonId":5,"createTime":1496650626000,"type":3,"commentNum":null,"likeNum":null,"creatorId":5,"title":null,"content":"哈哈哈","imageId":2,"coverImageurl":"7ba29684a32d44ca9f3c0f8ed9b1070d.jpg","imageurl":"f46d065b1623468f901b20806fd6303d.mp4","infoId":null,"detailsUrl":null,"infoImageurls":null,"state":null},{"subAlbumId":4,"commonId":4,"createTime":1496650157000,"type":2,"commentNum":null,"likeNum":null,"creatorId":5,"title":"电脑","content":"哈哈哈哈唉","imageId":40,"coverImageurl":null,"imageurl":"db090b3f5c8e4c369a0d87439dc9c876.jpg,ca285bff8536456083360a05ee951fe8.jpg,06ab1970bf5f46bb8ab833a29b65ff97.jpg","infoId":null,"detailsUrl":null,"infoImageurls":null,"state":null}]
     * fansCount : 0
     */

    private int careCount;
    private String headImageurl;
    private String name;
    private int fansCount;
    private int state;
    private int relationId;
    /**
     * subAlbumId : 6
     * commonId : 4
     * createTime : 1496650660000
     * type : 2
     * commentNum : null
     * likeNum : null
     * creatorId : 5
     * title : 电脑
     * content : 哈哈哈哈唉
     * imageId : 43
     * coverImageurl : null
     * imageurl : 47d7fd1b94d04725a9c54e373208fab0.jpg
     * infoId : null
     * detailsUrl : null
     * infoImageurls : null
     * state : null
     */

    private List<ListBean> list;

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCareCount() {
        return careCount;
    }

    public void setCareCount(int careCount) {
        this.careCount = careCount;
    }

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

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private int subAlbumId;
        private int commonId;
        private long createTime;
        private int type;
        private int commentNum;
        private int likeNum;
        private int creatorId;
        private String title;
        private String content;
        private int imageId;
        private String coverImageurl;
        private String imageurl;
        private int infoId;
        private String detailsUrl;
        private String infoImageurls;
        private int state;
        private int isLike;

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public int getSubAlbumId() {
            return subAlbumId;
        }

        public void setSubAlbumId(int subAlbumId) {
            this.subAlbumId = subAlbumId;
        }

        public int getCommonId() {
            return commonId;
        }

        public void setCommonId(int commonId) {
            this.commonId = commonId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public int getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(int creatorId) {
            this.creatorId = creatorId;
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

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
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

        public int getInfoId() {
            return infoId;
        }

        public void setInfoId(int infoId) {
            this.infoId = infoId;
        }

        public String getDetailsUrl() {
            return detailsUrl;
        }

        public void setDetailsUrl(String detailsUrl) {
            this.detailsUrl = detailsUrl;
        }

        public String getInfoImageurls() {
            return infoImageurls;
        }

        public void setInfoImageurls(String infoImageurls) {
            this.infoImageurls = infoImageurls;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }

    @Override
    public String toString() {
        return "InfoPersonMsgModel{" +
                "careCount=" + careCount +
                ", headImageurl='" + headImageurl + '\'' +
                ", userName='" + name + '\'' +
                ", fansCount=" + fansCount +
                ", state=" + state +
                ", relationId=" + relationId +
                ", list=" + list +
                '}';
    }
}
