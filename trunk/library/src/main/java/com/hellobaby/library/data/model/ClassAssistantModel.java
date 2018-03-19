package com.hellobaby.library.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hellobaby.library.utils.DateUtil;

/**
 * Created by zwj on 2017/6/5.
 * 课堂助手
 */

public class ClassAssistantModel implements Parcelable{

    /**
     * assistId : 1
     * creatorId : 2
     * title : title
     * content : content
     * detailsUrl : null
     * assistImageurls : img1,img2
     * createTime : 1496655736000
     * type : 2
     * createrName : 智塾科技
     */
    public static String kName = "ClassAssistantModel";
    private String title;
    private int assistId;
    private int creatorId;
    private String content;
    private String detailsUrl;
    private String assistImageurls;
    private long createTime;
    private int type;
    private String createrName;

    public ClassAssistantModel() {
    }

    public ClassAssistantModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAssistId() {
        return assistId;
    }

    public void setAssistId(int assistId) {
        this.assistId = assistId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    public String getAssistImageurls() {
        return assistImageurls;
    }

    public void setAssistImageurls(String assistImageurls) {
        this.assistImageurls = assistImageurls;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getCreateTimeFormat() {
        return DateUtil.long2ClassAssistantTime(createTime);
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    @Override
    public String toString() {
        return "ClassAssistantModel{" +
                "title='" + title + '\'' +
                ", assistId=" + assistId +
                ", creatorId=" + creatorId +
                ", content='" + content + '\'' +
                ", detailsUrl=" + detailsUrl +
                ", assistImageurls='" + assistImageurls + '\'' +
                ", createTime=" + createTime +
                ", type=" + type +
                ", createrName='" + createrName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.assistId);
        dest.writeInt(this.creatorId);
        dest.writeString(this.content);
        dest.writeString(this.detailsUrl);
        dest.writeString(this.assistImageurls);
        dest.writeLong(this.createTime);
        dest.writeInt(this.type);
        dest.writeString(this.createrName);
    }

    protected ClassAssistantModel(Parcel in) {
        this.title = in.readString();
        this.assistId = in.readInt();
        this.creatorId = in.readInt();
        this.content = in.readString();
        this.detailsUrl = in.readString();
        this.assistImageurls = in.readString();
        this.createTime = in.readLong();
        this.type = in.readInt();
        this.createrName = in.readString();
    }

    public static final Creator<ClassAssistantModel> CREATOR = new Creator<ClassAssistantModel>() {
        @Override
        public ClassAssistantModel createFromParcel(Parcel source) {
            return new ClassAssistantModel(source);
        }

        @Override
        public ClassAssistantModel[] newArray(int size) {
            return new ClassAssistantModel[size];
        }
    };
}
