package com.hellobaby.library.data.model;

import com.hellobaby.library.Const;

import java.io.Serializable;

/**
 * Created by zwj on 2016/12/28.
 * description :
 */

public class AlbumModel implements Serializable{
    public static final Integer TYPE_TEXT=1;
    public static final Integer TYPE_ALBUM=2;
    public static final Integer TYPE_VIDEO=3;
    private String commonId;//相册的id
    private String title;//相册标题
    private String content;//相册内容
    private String id;//单张照片的id
    private String imageName;//单张图片上传
    private String imageId;//相册里图片的id
    private String imageNames;//服务器返回的图片名称列表
    private String imageIds;//服务器返回的图片id列表
    private String lastmodifyTime;//最后修改时间
    private Integer type;//类型 相册视频文字 type 1:纯文字 type 2:相册 type 3:小视频
    private String imageUrl;//相册封面
    private String videoImageUrl;//小视频封面
    private String videoUrl;//小视频url
    private String videoId;//小视频的id
    private String videoName;//小视频的名字
    private String userId;//创建人ID
    private String userName;//创建人名字

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVideoImageUrl() {
        return videoImageUrl;
    }
    public String getVideoImageUrlAbs() {
        return Const.URL_VideoFirstFrame+videoImageUrl;
    }

    public void setVideoImageUrl(String videoImageUrl) {
        this.videoImageUrl = videoImageUrl;
    }

    public String getLastmodifyTime() {
        return lastmodifyTime;
    }

    public void setLastmodifyTime(String lastmodifyTime) {
        this.lastmodifyTime = lastmodifyTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public String getImageUrlAbs() {
        return Const.URL_Album+imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
    public String getVideoUrlAbs() {
        return Const.URL_Video+videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCommonId() {
        return commonId;
    }

    public void setCommonId(String commonId) {
        this.commonId = commonId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageNames() {
        return imageNames;
    }

    public void setImageNames(String imageNames) {
        this.imageNames = imageNames;
    }

    public String getImageIds() {
        return imageIds;
    }

    public void setImageIds(String imageIds) {
        this.imageIds = imageIds;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    @Override
    public String toString() {
        return "AlbumModel{" +
                "commonId='" + commonId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", id='" + id + '\'' +
                ", imageName='" + imageName + '\'' +
                ", imageId='" + imageId + '\'' +
                ", imageNames='" + imageNames + '\'' +
                ", imageIds='" + imageIds + '\'' +
                ", lastmodifyTime=" + lastmodifyTime +
                ", type=" + type +
                ", imageUrl='" + imageUrl + '\'' +
                ", videoImageUrl='" + videoImageUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", videoId='" + videoId + '\'' +
                ", videoName='" + videoName + '\'' +
                '}';
    }
}
