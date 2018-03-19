package com.abings.baby.ui.measuredata.teachingplan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.model.ReviewModel;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

/**
 */

public interface TeachingPlanMvpView<T> extends MvpView<T> {
    public void showTeachingPlanList(List<TeachingPlanModel> jsonObject);
}
