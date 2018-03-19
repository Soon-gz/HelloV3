package com.abings.baby.ui.main.fm.school;

import android.view.View;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.event.EventDetailActivity;
import com.abings.baby.ui.event.EventListMvpView;
import com.abings.baby.ui.event.EventListPresenter;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.main.fm.school.SchoolItem;
import com.hellobaby.library.ui.main.fm.school.SchoolRecyclerViewFragment;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.DeviceUtils;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.baseadapter.OnLoadMoreListener;

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
        pageModel=new PageModel();
//        presenter.selectEventByClassToOneBaby(Const.SCHOOL_EVENT);
    }

    @Override
    public void initAdapter() {
        bAdapter=new SchoolRVAdapter(getContext(),bListData, EventDetailActivity.class);
        bAdapter.setLoadingView(R.layout.footer_more);
        bAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if(pageModel.getPageNum()!=pageModel.getPages())
                presenter.selectEventByClassToOneBabyPage(Const.SCHOOL_EVENT,pageModel.getPageNum()+1);
                else {
                    bAdapter.setLoadEndView(R.layout.footer_loadend);
                }

            }
        });
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void initListData() {
        pageModel=new PageModel();
        presenter.selectEventByClassToOneBabyPage(Const.SCHOOL_EVENT,pageModel.getPageNum());
    }

    @Override
    public void showEventData(List<EventModel> list) {
        if(pageModel.getPageNum()==1){
            bListData.clear();
        }
        for(EventModel event:list){
            SchoolItem schoolitem=new SchoolItem(SchoolItem.TYPE_EVENT, event.getEventTitle(),String.format(eventstring, event.getEventAddress(), DateUtil.getFormatTimeFromTimestamp(event.getEventStartTime(),"yyyy年MM月dd日")),event,event.getHeadImageurl());
            schoolitem.setFromname(event.getTeacherName());
            schoolitem.setPublish(DateUtil.getDescriptionTimeFromTimestamp(event.getCreateTime()));
            bListData.add(schoolitem);
        }
        bAdapter.notifyDataSetChanged();
        if(pageModel.getPageNum() != pageModel.getPages()){
            bAdapter.setLoadingView(R.layout.footer_more);
        }else {
            bAdapter.setLoadEndView(R.layout.footer_loadend);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showEventDetail(EventModel eventModel, String isjoin) {

    }

    @Override
    public void showListData(JSONArray jsonArray) {

    }

    @Override
    public void setPageModel(PageModel pageModel) {
            this.pageModel=pageModel;
        if(pageModel.getPageNum() != pageModel.getPages()){
            bAdapter.setLoadingView(R.layout.footer_more);
        }else {
            bAdapter.setLoadEndView(R.layout.footer_loadend);
        }
    }

    @Override
    public void showData(Object o) {

    }
}
