package com.abings.baby.teacher.ui.recipes;

import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.base.MvpView;

/**
 * Created by Administrator on 2017/1/6.
 */

public interface RecipesMvp extends MvpView {
    void showListData(JSONArray jsonArray);

    void setPageModel(PageModel pageModel);
}
