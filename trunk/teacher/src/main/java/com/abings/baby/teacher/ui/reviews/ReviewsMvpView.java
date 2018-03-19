package com.abings.baby.teacher.ui.reviews;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.model.BabyAttendancesModel;
import com.hellobaby.library.data.model.ReviewModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2017/1/1.
 */

public interface ReviewsMvpView extends MvpView {
    void showJSONObject(JSONObject jsonObject);

    void showJSONArray(JSONArray jsonObject);

    void updateFinish();

    void LeaveByClassId(List<BabyAttendancesModel> reviewModels);

    void insertRecord();
}
