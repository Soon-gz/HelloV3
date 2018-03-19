package com.hellobaby.library.ui.slide;

import android.os.Parcel;
import android.os.Parcelable;

public class SlideBean implements Parcelable {
    private String url;

    public SlideBean() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }

    protected SlideBean(Parcel in) {
        url = in.readString();
    }

    public static final Creator<SlideBean> CREATOR = new Creator<SlideBean>() {
        @Override
        public SlideBean createFromParcel(Parcel in) {
            return new SlideBean(in);
        }

        @Override
        public SlideBean[] newArray(int size) {
            return new SlideBean[size];
        }
    };

}