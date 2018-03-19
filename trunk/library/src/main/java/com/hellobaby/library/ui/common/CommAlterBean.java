package com.hellobaby.library.ui.common;

import java.io.Serializable;

/**
 * Created by zwj on 2016/10/26.
 * description :
 */

public class CommAlterBean implements Serializable {
    private static final long serialVersionUID = -565021146515051507L;
    public static final String kName = "CommAlterBean";
    private String title;//标题
    private String oldValue;//旧值
    private String newValue;//新值
    private int viewId;//当前控件的id,用于requestCode和resultCode
    private String hintValue;//提示语
    private int inputLength = -1;//文字长度

    private int inputType = -1;//输入的类型，默认为文本
    private final int inputType_email = -10;//输入类型为邮箱

    public CommAlterBean() {
    }

    /**
     *
     * @param viewId 对应控件的id
     */
    public CommAlterBean(int viewId) {
        this("",viewId);
    }
    /**
     * @param oldValue
     * @param viewId
     */
    public CommAlterBean(String oldValue, int viewId) {
        this.oldValue = oldValue;
        this.viewId = viewId & 0x0000ffff;
    }

    public String getOldValue() {
        if (oldValue == null) {
            this.oldValue = "";
        }
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue.trim();
    }

    public int getViewId() {
        return viewId & 0x0000ffff;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public String getHintValue() {
        if (hintValue == null) {
            this.hintValue = "";
        }
        return hintValue;
    }

    public void setHintValue(String hintValue) {
        this.hintValue = hintValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    /**
     * 设置为邮箱
     */
    public void setInputTypeEmail() {
        this.inputType = inputType_email;
    }

    /**
     * 是否为邮箱
     */
    public boolean isInputTypeEmail() {
        return this.inputType == inputType_email;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public int getInputLength() {
        return inputLength;
    }

    public void setInputLength(int inputLength) {
        this.inputLength = inputLength;
    }

    @Override
    public String toString() {
        return "CommAlterBean{" +
                "title='" + title + '\'' +
                ", oldValue='" + oldValue + '\'' +
                ", newValue='" + newValue + '\'' +
                ", viewId=" + viewId +
                ", hintValue='" + hintValue + '\'' +
                ", inputType=" + inputType +
                '}';
    }
}
