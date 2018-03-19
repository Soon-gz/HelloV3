package com.abings.baby.ui.event;

import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */

public interface EventListMvpView extends MvpView {
    public void showEventData(List<EventModel> list);
    public void showEventDetail(EventModel eventModel,String isjoin);
    public void showListData(JSONArray jsonArray);
}
