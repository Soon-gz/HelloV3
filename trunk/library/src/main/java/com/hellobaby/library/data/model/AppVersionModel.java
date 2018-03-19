package com.hellobaby.library.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zwj on 2017/5/15.
 * description :
 */

public class AppVersionModel implements Parcelable {


    public static String kName = "AppVersionModel";
    /**
     * versionId : 1
     * version : 3.1.4
     * type : 3
     * forceFlag : false
     * remark : 备注
     */

    private String versionId;
    private String version;
    private String type;
    /**
     * 1:true;0:false; 是否必须升级
     */
    private boolean forceFlag = false;
    private String remark;
    private String downloadUrl;


    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public boolean isForceFlag() {
        return forceFlag;
    }

    public void setForceFlag(boolean forceFlag) {
        this.forceFlag = forceFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "AppVersionModel{" +
                "versionId='" + versionId + '\'' +
                ", version='" + version + '\'' +
                ", type='" + type + '\'' +
                ", forceFlag=" + forceFlag +
                ", remark='" + remark + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.versionId);
        dest.writeString(this.version);
        dest.writeString(this.type);
        dest.writeByte(this.forceFlag ? (byte) 1 : (byte) 0);
        dest.writeString(this.remark);
        dest.writeString(this.downloadUrl);
    }

    public AppVersionModel() {
    }

    protected AppVersionModel(Parcel in) {
        this.versionId = in.readString();
        this.version = in.readString();
        this.type = in.readString();
        this.forceFlag = in.readByte() != 0;
        this.remark = in.readString();
        this.downloadUrl = in.readString();
    }

    public static final Creator<AppVersionModel> CREATOR = new Creator<AppVersionModel>() {
        @Override
        public AppVersionModel createFromParcel(Parcel source) {
            return new AppVersionModel(source);
        }

        @Override
        public AppVersionModel[] newArray(int size) {
            return new AppVersionModel[size];
        }
    };
}
