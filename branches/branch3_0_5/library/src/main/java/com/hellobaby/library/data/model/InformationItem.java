package com.hellobaby.library.data.model;

import java.io.Serializable;
import java.util.List;

public class InformationItem implements Serializable {
    private List<String> photos;
    private String pk_message_id;
    private String sender_name;
    private String message_type;
    private String title;
    private String content;
    private String create_datetime;
    private String video;
    private String url;

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getPk_message_id() {
        return pk_message_id;
    }

    public void setPk_message_id(String pk_message_id) {
        this.pk_message_id = pk_message_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
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

    public String getCreate_datetime() {
        return create_datetime;
    }

    public void setCreate_datetime(String create_datetime) {
        this.create_datetime = create_datetime;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
