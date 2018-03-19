package com.hellobaby.library.data.model;

/**
 * Created by Administrator on 2017/4/25.
 */

public class BadgeViewModel {
    int type;
    Boolean isShow=false;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }
}
