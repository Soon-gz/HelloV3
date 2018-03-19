package com.hellobaby.library.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 */

public class TimeCardHisModel implements Serializable {
    //"dataType": 2,		0没数据，1请假，2有数据   (先判断这个)
    protected final static int TCH_TPYE_NODATA = 0;
    public final static int TCH_TPYE_VOCATION = 1;
    protected final static int TCH_TPYE_HASDATA = 2;
    int dataType;
    String dataTime;
    @SerializedName("timeCards")
    List<TimeCardModel> timeCardModels;

    public int getDataType() {
        return dataType;
    }

    public String getDataTypeStr() {
        if (dataType == TCH_TPYE_NODATA) {
            return "";
        } else if (dataType == TCH_TPYE_VOCATION) {
            return "宝宝当天请假";
        } else if (dataType == TCH_TPYE_HASDATA) {
            return "";
        }
        return "";
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public List<TimeCardModel> getTimeCardModels() {
        return timeCardModels;
    }

    public void setTimeCardModels(List<TimeCardModel> timeCardModels) {
        this.timeCardModels = timeCardModels;
    }
}
