package com.abings.baby.ui.message;

import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

public interface MsgCenterMvp extends MvpView {
    void showMsgList(JSONArray jsonArray);
    void showMsgSendSuccess();
    void selectTeacher(List<TeacherModel> TeacherModel);
}
