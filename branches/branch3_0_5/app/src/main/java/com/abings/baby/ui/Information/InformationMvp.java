package com.abings.baby.ui.Information;

import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

public interface InformationMvp extends MvpView {
    void showListData(List<InformationModel> InformationModel);
    void showMsgFinish(String msg);
}
