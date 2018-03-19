package com.abings.baby.teacher.ui.event;

import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.EventJoinModel;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */

public interface EventDetailMvpView extends MvpView {
    void showEvent(EventModel eventModel);
    void showJoinDetail(List<EventJoinModel> list);
}
