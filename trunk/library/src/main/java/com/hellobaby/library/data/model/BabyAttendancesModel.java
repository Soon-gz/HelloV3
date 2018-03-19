package com.hellobaby.library.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/1/4.
 */

public class BabyAttendancesModel implements Parcelable {
    /**
     * teacherId : 1
     * babyId : 1
     * babyName : jason
     * headImageurl : baby001.jpg
     * state : 1
     */

    private int teacherId;
    private int babyId;
    private String babyName;
    private String headImageurl;
    private int state;
    private int type;
    private int morningState;
    private int afternoonState;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getBabyId() {
        return babyId;
    }

    public void setBabyId(int babyId) {
        this.babyId = babyId;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * 到
     */
    public boolean isStateAttend() {
        return this.state == 1;
    }
    /**
     * 未到
     */
    public boolean isStateAbsence() {
        return this.state == 2;
    }

    /**
     * 请假
     */
    public boolean isStateVacation() {
        return this.state == 3;
    }


    /**
     * 到  评价状态
     */
    public boolean isReviewsStateAttend() {
        return (this.morningState == 1) || (this.afternoonState == 1);
    }
    /**
     * 未到   评价状态
     */
    public boolean isReviewsStateAbsence() {
        return (this.morningState == 2) && (this.afternoonState == 2);
    }

    /**
     * 请假   评价状态
     */
    public boolean isReviewsStateVacation() {
        return ((this.morningState != 1) && (this.afternoonState != 1))&&((this.morningState == 3) || (this.afternoonState == 3));
    }

    public int getMorningState() {
        return morningState;
    }

    public void setMorningState(int morningState) {
        this.morningState = morningState;
    }

    public int getAfternoonState() {
        return afternoonState;
    }

    public void setAfternoonState(int afternoonState) {
        this.afternoonState = afternoonState;
    }

    @Override
    public String toString() {
        return "BabyAttendancesModel{" +
                "teacherId=" + teacherId +
                ", babyId=" + babyId +
                ", babyName='" + babyName + '\'' +
                ", headImageurl='" + headImageurl + '\'' +
                ", state=" + state +
                ", type=" + type +
                ", morningState=" + morningState +
                ", afternoonState=" + afternoonState +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.teacherId);
        dest.writeInt(this.babyId);
        dest.writeString(this.babyName);
        dest.writeString(this.headImageurl);
        dest.writeInt(this.state);
        dest.writeInt(this.type);
        dest.writeInt(this.morningState);
        dest.writeInt(this.afternoonState);
    }

    public BabyAttendancesModel() {
    }

    protected BabyAttendancesModel(Parcel in) {
        this.teacherId = in.readInt();
        this.babyId = in.readInt();
        this.babyName = in.readString();
        this.headImageurl = in.readString();
        this.state = in.readInt();
        this.type = in.readInt();
        this.morningState = in.readInt();
        this.afternoonState = in.readInt();
    }

    public static final Creator<BabyAttendancesModel> CREATOR = new Creator<BabyAttendancesModel>() {
        @Override
        public BabyAttendancesModel createFromParcel(Parcel source) {
            return new BabyAttendancesModel(source);
        }

        @Override
        public BabyAttendancesModel[] newArray(int size) {
            return new BabyAttendancesModel[size];
        }
    };
}
