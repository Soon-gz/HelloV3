package com.hellobaby.library.ui.main.fm.school;

import com.hellobaby.library.data.model.EventItem;
import com.hellobaby.library.data.model.EventModel;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by zwj on 2016/12/20.
 * description :
 */

public class SchoolItem implements Serializable{
    public final static String TYPE_NEWS = "1";
    public final static String TYPE_DYNAMIC = "2";
    public final static String TYPE_COOKBOOK= "3";
    public final static String TYPE_EVENT = "4";
    private String type;
    private String name;
    private String url;//
    private String newsUrl;//新闻的链接
    private List<String> listUrl;//图片展示列表
    private String videoImageUrl;//视频缩略图链接
    private String videoUrl;//视频链接
    private EventModel object;//活动
    private String photo;//头像
    private String fromname;//来自于谁
    private String publish;//发布时间
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public EventModel getObject() {
        return object;
    }

    public void setObject(EventModel object) {
        this.object = object;
    }

    public SchoolItem() {
    }

//    public SchoolItem(String type, String name, String url, String newsUrl) {
//        this.type = type;
//        this.name = name;
//        this.url = url;
//        this.newsUrl = newsUrl;
//    }
    public SchoolItem(String type, String name, String url,String photo) {
        this.type = type;
        this.name = name;
        this.url = url;
        this.photo=photo;
    }
    public SchoolItem(String type, String name, List<String> listUrl,String photo) {
        this.type = type;
        this.name = name;
        this.listUrl = listUrl;
        this.photo=photo;
    }
    public SchoolItem(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public SchoolItem(String type, String name, String url,EventModel object,String photo) {
        this.type = type;
        this.url=url;
        this.name = name;
        this.object=object;
        this.photo=photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public List<String> getListUrl() {
        return listUrl;
    }

    public void setListUrl(List<String> listUrl) {
        this.listUrl = listUrl;
    }

    public String getVideoImageUrl() {
        return videoImageUrl;
    }

    public void setVideoImageUrl(String videoImageUrl) {
        this.videoImageUrl = videoImageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "SchoolItem{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", newsUrl='" + newsUrl + '\'' +
                ", listUrl=" + listUrl +
                ", videoImageUrl='" + videoImageUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", object=" + object +
                ", photo='" + photo + '\'' +
                '}';
    }
}
