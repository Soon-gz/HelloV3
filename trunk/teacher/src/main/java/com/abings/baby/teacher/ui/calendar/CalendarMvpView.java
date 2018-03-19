package com.abings.baby.teacher.ui.calendar;

import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.ui.base.MvpView;

import java.util.ArrayList;
import java.util.List;


public interface CalendarMvpView<T> extends MvpView<T> {
    public void addEvent(JSONArray times);
    public void addEventItem(JSONArray times);
}
