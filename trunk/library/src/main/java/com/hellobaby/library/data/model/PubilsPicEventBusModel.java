package com.hellobaby.library.data.model;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class PubilsPicEventBusModel implements Serializable {
    String title;
    String content;
    List<String> imageList;
    String isPublic;
    String existCommonId;

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getExistCommonId() {
        return existCommonId;
    }

    public void setExistCommonId(String existCommonId) {
        this.existCommonId = existCommonId;
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

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
