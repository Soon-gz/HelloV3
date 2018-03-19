package com.hellobaby.library.data.model;

/**
 * 作者：ShuWen
 * 时间： 2018/1/3.10:56
 * 描述：用于eventbus，标注从哪来到哪去！eventbus真操蛋。
 */

public class EventBusObject {
    private String fromWhere;
    private String toWhere;
    private String msg;

    public String getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
    }

    public String getToWhere() {
        return toWhere;
    }

    public void setToWhere(String toWhere) {
        this.toWhere = toWhere;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "EventBusObject{" +
                "msg='" + msg + '\'' +
                ", toWhere='" + toWhere + '\'' +
                ", fromWhere='" + fromWhere + '\'' +
                '}';
    }
}
