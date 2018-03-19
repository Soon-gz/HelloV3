package com.hellobaby.library.ui.slide;

import android.os.Parcel;
import android.os.Parcelable;

public class SlideBean implements Parcelable {
    private String url;
    private int width;
    private int height;


    public SlideBean() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public boolean isLongImage() {
        if (width <= 0) {
            return false;
        } else {
            return (height / width > 2);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    protected SlideBean(Parcel in) {
        this.url = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Creator<SlideBean> CREATOR = new Creator<SlideBean>() {
        @Override
        public SlideBean createFromParcel(Parcel source) {
            return new SlideBean(source);
        }

        @Override
        public SlideBean[] newArray(int size) {
            return new SlideBean[size];
        }
    };
}