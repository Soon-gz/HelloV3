package com.hellobaby.timecard.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hellobaby.library.Const;

/**
 * Created by zwj on 2017/3/15.
 * description : 这里用到的userModel
 */

public class TCUserModel implements Parcelable {
    private String userName;
    private String phoneNum;
    private String relation;
    private String headImageurl;
    private String qrcode;


    /**
     * 接送人类型：1 主用户
     * 接送人类型：2 代接送人
     * 接送人类型：3 持接送卡
     */
    private Integer personType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public String getHeadImageurlAbs() {
        String url = "";
        if (isPersonTypeMaster()) {
            url = Const.URL_userHead;
        } else if (isPersonTypeAgent()) {
            //代接送
        } else if (isPersonTypeCard()) {
            url = Const.URL_pickHead;
        }
        return url + headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }

    /**
     * 主用户
     */
    public boolean isPersonTypeMaster() {
        return 1 == personType;
    }

    /**
     * 代接送人
     */
    public boolean isPersonTypeAgent() {
        return 2 == personType;
    }

    /**
     * 持接送卡
     */
    public boolean isPersonTypeCard() {
        return 3 == personType;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    @Override
    public String toString() {
        return "TCUserModel{" +
                "userName='" + userName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", relation='" + relation + '\'' +
                ", headImageurl='" + headImageurl + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", personType='" + personType + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.phoneNum);
        dest.writeString(this.relation);
        dest.writeString(this.headImageurl);
        dest.writeString(this.qrcode);
        dest.writeValue(this.personType);
    }

    public TCUserModel() {
    }

    protected TCUserModel(Parcel in) {
        this.userName = in.readString();
        this.phoneNum = in.readString();
        this.relation = in.readString();
        this.headImageurl = in.readString();
        this.qrcode = in.readString();
        this.personType = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<TCUserModel> CREATOR = new Creator<TCUserModel>() {
        @Override
        public TCUserModel createFromParcel(Parcel source) {
            return new TCUserModel(source);
        }

        @Override
        public TCUserModel[] newArray(int size) {
            return new TCUserModel[size];
        }
    };
}
