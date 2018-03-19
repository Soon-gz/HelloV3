package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/12/8.
 */

public class PatchBean {

    /**
     * app_v : 1.0
     * path_v : 1.0
     * url :
     * isNeedUpdate : false
     */

    private String app_v;
    private String path_v;
    private String url;
    private boolean isNeedUpdate;

    public String getApp_v() {
        return app_v;
    }

    public void setApp_v(String app_v) {
        this.app_v = app_v;
    }

    public String getPath_v() {
        return path_v;
    }

    public void setPath_v(String path_v) {
        this.path_v = path_v;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isIsNeedUpdate() {
        return isNeedUpdate;
    }

    public void setIsNeedUpdate(boolean isNeedUpdate) {
        this.isNeedUpdate = isNeedUpdate;
    }
}
