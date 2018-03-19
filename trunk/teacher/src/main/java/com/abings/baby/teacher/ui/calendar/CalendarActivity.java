package com.abings.baby.teacher.ui.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.abings.baby.teacher.ui.publishteachingplan.TeachingPlanListActivity;
import com.alibaba.fastjson.JSONArray;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.hellobaby.library.data.model.ScheduleIdModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;


public class CalendarActivity extends BaseTitleActivity implements CalendarMvpView{
    private CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("yyyy年MM月", Locale.getDefault());
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    RecyclerView recyclerView;
    protected RecyclerViewAdapterCalendar adapter;
    protected List<ScheduleIdModel> mDatas;
    List<Event> events=new ArrayList<>();
    @Inject
    CalendarPresenter presenter;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_classhelper;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        final List<String> mutableBookings = new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.cha_rv_list);
        setBtnLeftClickFinish();
        setBtnRightDrawableRes(R.drawable.teachingp_icon);
        bIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(bContext, TeachingPlanListActivity.class));
            }
        });
        iniAdapter();
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, mutableBookings);
        setTitleText(dateFormatForMonth.format(new Date()));
        compactCalendarView = (CompactCalendarView)findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        String []tag={"日","一","二","三","四","五","六"};
        compactCalendarView.setDayColumnNames(tag);
        compactCalendarView.invalidate();
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                presenter.selectScheduleDetailsByDay(dateClicked.getTime()+"");
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setTitleText(dateFormatForMonth.format(firstDayOfNewMonth));
                presenter.selectSchedule(firstDayOfNewMonth.getYear()+1900+"", firstDayOfNewMonth.getMonth()==0?12+"":firstDayOfNewMonth.getMonth()+1+"");
                presenter.selectScheduleDetailsByDay(firstDayOfNewMonth.getTime()+"");
            }
        });
        Date date=new Date();
        presenter.selectSchedule(date.getYear()+1900+"", date.getMonth()==0?12+"":date.getMonth()+1+"");
    }
    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    @Override
    public void showData(Object o) {

    }

    private void iniAdapter() {
        mDatas=new ArrayList<>();
        if (adapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerViewAdapterCalendar(this,mDatas,false);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void addEvent(JSONArray jsonArray) {
        List<Long> times=JSONArray.parseArray(jsonArray.toJSONString(),Long.class);
        events.clear();
        compactCalendarView.removeAllEvents();
        for (Long time:times){
            events.add(new Event(this.getResources().getColor(R.color.gray6c), time, "Event at " + new Date(time) ));
        }
        compactCalendarView.addEvents(events);
    }

    @Override
    public void addEventItem(JSONArray times) {
        mDatas.clear();
        mDatas=JSONArray.parseArray(times.toJSONString(),ScheduleIdModel.class);
        adapter.setNewData(mDatas);
    }
}
