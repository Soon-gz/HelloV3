package com.abings.baby.ui.measuredata;


import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.MeasureModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

public interface LineCharMvpView extends MvpView {
    /**
     * 填入最新数据
     */
    public void setLastData(JSONObject jsonObject);
    /**
     * 历史数据填入
     */
    public void selectHisHeight(List<MeasureModel> models);
}
