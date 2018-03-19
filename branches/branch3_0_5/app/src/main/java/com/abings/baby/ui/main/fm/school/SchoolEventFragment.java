package com.abings.baby.ui.main.fm.school;

import com.abings.baby.ZSApp;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.event.EventDetailActivity;
import com.abings.baby.ui.event.EventListMvpView;
import com.abings.baby.ui.event.EventListPresenter;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.main.fm.school.SchoolItem;
import com.hellobaby.library.ui.main.fm.school.SchoolRecyclerViewFragment;
import com.hellobaby.library.utils.DateUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/12/20.
 * description :
 */

public class SchoolEventFragment extends SchoolRecyclerViewFragment implements EventListMvpView {
    @Inject
    EventListPresenter presenter;
    String eventstring = "活动地点：%s\n活动时间：%s";
    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(((BaseLibActivity)getActivity()).getActivityComponent(),getActivity()).inject(this);
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        presenter.attachView(this);
        presenter.selectEventByClassToOneBaby(Const.SCHOOL_EVENT);
    }

    @Override
    public void initAdapter() {
        bAdapter=new SchoolRVAdapter(getContext(),bListData, EventDetailActivity.class);
    }

    @Override
    public void showEventData(List<EventModel> list) {
        for(EventModel event:list){
            SchoolItem schoolitem=new SchoolItem(SchoolItem.TYPE_EVENT, event.getEventTitle(),String.format(eventstring, event.getEventAddress(), DateUtil.getFormatTimeFromTimestamp(event.getEventStartTime(),"yyyy年MM月dd日")),event,event.getHeadImageurl());
            schoolitem.setFromname(event.getTeacherName());
            schoolitem.setPublish(DateUtil.getDescriptionTimeFromTimestamp(event.getCreateTime()));
            bListData.add(schoolitem);
        }
        bAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEventDetail(EventModel eventModel, String isjoin) {

    }

    @Override
    public void showListData(JSONArray jsonArray) {

    }

    @Override
    public void showData(Object o) {

    }
}
