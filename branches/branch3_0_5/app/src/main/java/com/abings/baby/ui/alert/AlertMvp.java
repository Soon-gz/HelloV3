package com.abings.baby.ui.alert;

import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.ui.base.MvpView;

public interface AlertMvp extends MvpView {
    void showMsgList(JSONArray jsonArray);
}
