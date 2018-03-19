package com.abings.baby.teacher.ui.event;

import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.base.MvpView;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */

public interface EventListMvpView extends MvpView {
    public void showEventData(List<EventModel> list);
    public void showListData(JSONArray jsonArray);
    public void setPageModel(PageModel pageModel);
}
