package com.abings.baby.teacher.ui.msg;

import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.ui.base.MvpView;

public interface MsgCenterMvp extends MvpView {
    void showMsgList(JSONArray jsonArray);
    void showMsgSendSuccess();
}
