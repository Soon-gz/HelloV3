package com.abings.baby.ui.album;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zwj on 2016/10/28.
 * description :
 */

public class WaterFallItem implements Parcelable {
    public static final String kName = "WaterFallItem";
    private String url;//图片链接
    private String id;//图片的id
    private int width;//图片宽度
    private int height;//图片高度
    private boolean isSelected = false;
    private int postion;
    private int type = 0;

    public WaterFallItem() {
    }

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

    public int getType() {
        return type;
    }

    public int getLongImageHeight() {
        return getLongImageHeight(width);
    }

    /**
     * @param bitmapW 外部传值
     * @return
     */
    public int getLongImageHeight(int bitmapW) {
        if (bitmapW >= 400) {
            return 3200;
        } else {
            return bitmapW * 8;
        }
    }


    /**
     * 长图
     *
     * @return
     */
    public boolean isTypeLongImage() {
        return 1 == type;
    }
    public void setTypeLongImage() {
        this.type = 1;
    }
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.id);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeInt(this.postion);
        dest.writeInt(this.type);
    }

    protected WaterFallItem(Parcel in) {
        this.url = in.readString();
        this.id = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.isSelected = in.readByte() != 0;
        this.postion = in.readInt();
        this.type = in.readInt();
    }

    public static final Creator<WaterFallItem> CREATOR = new Creator<WaterFallItem>() {
        @Override
        public WaterFallItem createFromParcel(Parcel source) {
            return new WaterFallItem(source);
        }

        @Override
        public WaterFallItem[] newArray(int size) {
            return new WaterFallItem[size];
        }
    };
}
