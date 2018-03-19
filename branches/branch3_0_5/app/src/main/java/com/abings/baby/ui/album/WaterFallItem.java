package com.abings.baby.ui.album;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zwj on 2016/10/28.
 * description :
 */

public class WaterFallItem implements Parcelable{
    public static final String kName = "WaterFallItem";
    private String url;//图片链接
    private String id;//图片的id
    private int width;//图片宽度
    private int height;//图片高度
    private boolean isSelected = false;
    private int postion;

    public WaterFallItem() {
    }

//    public WaterFallItem(String url, String id, int width, int height) {
//        this.url = url;
//        this.id = id;
//        this.width = width;
//        this.height = height;
//    }




    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(id);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeInt(postion);
    }
    protected WaterFallItem(Parcel in) {
        url = in.readString();
        id = in.readString();
        width = in.readInt();
        height = in.readInt();
        isSelected = in.readByte() != 0;
        postion = in.readInt();
    }

    public static final Creator<WaterFallItem> CREATOR = new Creator<WaterFallItem>() {
        @Override
        public WaterFallItem createFromParcel(Parcel in) {
            return new WaterFallItem(in);
        }

        @Override
        public WaterFallItem[] newArray(int size) {
            return new WaterFallItem[size];
        }
    };

}
