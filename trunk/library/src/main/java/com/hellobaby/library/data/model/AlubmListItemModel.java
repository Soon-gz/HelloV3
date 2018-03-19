package com.hellobaby.library.data.model;

/**
 * Created by Administrator on 2017/6/1.
 */

public class AlubmListItemModel {
    public boolean isSelected;
    /**
     * commonId : 1
     * imageurl : 61326dff43b24e50aed6f4dad09102d2.jpeg
     * title : wocahahaha
     * content : wobeixiugaile
     */

    private int commonId;
    private String imageurl;
    private String title;
    private String content;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getCommonId() {
        return commonId;
    }

    public void setCommonId(int commonId) {
        this.commonId = commonId;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
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
}
