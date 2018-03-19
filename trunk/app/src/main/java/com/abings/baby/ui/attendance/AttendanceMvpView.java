package com.abings.baby.ui.attendance;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.model.ReviewModel;
import com.hellobaby.library.ui.base.MvpView;


public interface AttendanceMvpView<T> extends MvpView<T> {
    void showJsonObject(JSONObject jsonObject);
    void showJsonArray(JSONArray jsonArr);
}
