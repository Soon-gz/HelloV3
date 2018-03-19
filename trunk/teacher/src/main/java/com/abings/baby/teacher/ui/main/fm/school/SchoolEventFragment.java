package com.abings.baby.teacher.ui.main.fm.school;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.event.EventDetailActivity;
import com.abings.baby.teacher.ui.event.EventListMvpView;
import com.abings.baby.teacher.ui.event.EventListPresenter;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.EventBusObject;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.main.fm.school.SchoolItem;
import com.hellobaby.library.ui.main.fm.school.SchoolRecyclerViewFragment;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.baseadapter.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        DaggerUtils.getActivityComponent(((BaseLibActivity) getActivity()).getActivityComponent(), getActivity()).inject(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        presenter.attachView(this);
//        refreshSchoolEvent();
//        pageModel=new PageModel();
//        presenter.selectEventOnTeacherPage(Const.SCHOOL_EVENT,pageModel.getPageNum());
    }

    public void refreshSchoolEvent() {
        pageModel = new PageModel();
        if (classesIDS.equals(SchoolMasterChooseActivity.RESULT_DATA)){
            presenter.selectEventOnTeacherPage(Const.SCHOOL_EVENT, pageModel.getPageNum());
        }else {
            presenter.selectEventBySchoolAdminPage(classesIDS,Const.SCHOOL_EVENT, pageModel.getPageNum());
        }
    }
    @Override
    public void initListData() {
        pageModel = new PageModel();
        if (classesIDS.equals(SchoolMasterChooseActivity.RESULT_DATA)){
            presenter.selectEventOnTeacherPage(Const.SCHOOL_EVENT, pageModel.getPageNum());
        }else {
            presenter.selectEventBySchoolAdminPage(classesIDS,Const.SCHOOL_EVENT, pageModel.getPageNum());
        }
    }
    @Override
    public void initAdapter() {
        if(pageModel.getPageNum()==1){
            bListData.clear();
        }
        bAdapter = new SchoolRVAdapter(getContext(), bListData, EventDetailActivity.class);
        bAdapter.setLoadingView(R.layout.footer_more);
        bAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (pageModel.getPageNum() != pageModel.getPages()) {
                    if (classesIDS.equals(SchoolMasterChooseActivity.RESULT_DATA)){
                        presenter.selectEventOnTeacherPage(Const.SCHOOL_EVENT, pageModel.getPageNum() + 1);
                    }else {
                        presenter.selectEventBySchoolAdminPage(classesIDS,Const.SCHOOL_EVENT, pageModel.getPageNum()+1);
                    }
                }
                else {
                    bAdapter.setLoadEndView(R.layout.footer_loadend);
                }
            }
        });
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 作者：ShuWen
     * 日期：2018/1/3  14:39
     * 描述：从SchoolMasterChooseActivity选择后回调更新数据
     *
     * @return [返回类型说明]
     */
    @Subscribe
    public void onEvent(EventBusObject busObject){
        if (busObject.getFromWhere().equals(SchoolMasterChooseActivity.class.getSimpleName())
                && busObject.getToWhere().equals(SchoolMasterChooseActivity.TO_WHERE)){
            LogZS.i("更新活动数据："+busObject.toString());
            mSwipeRefreshLayout.setRefreshing(true);
            pageModel.setPageNum(1);
            classesIDS = busObject.getMsg();
            if (busObject.getMsg().equals(SchoolMasterChooseActivity.RESULT_DATA)){
                refreshSchoolEvent();
            }else {
                presenter.selectEventBySchoolAdminPage(busObject.getMsg(),Const.SCHOOL_EVENT, pageModel.getPageNum());
            }
        }
    }

    @Override
    public void showEventData(List<EventModel> list) {
        if (pageModel.getPageNum() == 1){
            bListData.clear();
        }
        for (EventModel event : list) {
            SchoolItem schoolitem = new SchoolItem(SchoolItem.TYPE_EVENT, event.getEventTitle(), String.format(eventstring, event.getEventAddress(), DateUtil.getFormatTimeFromTimestamp(event.getEventStartTime(), "yyyy年MM月dd日")), event, event.getHeadImageurl());
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
    public void showListData(JSONArray jsonArray) {

    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
        if(pageModel.getPageNum() != pageModel.getPages()){
            bAdapter.setLoadingView(R.layout.footer_more);
        }else {
            bAdapter.setLoadEndView(R.layout.footer_loadend);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(String message) {
        if (message.equals("reflushfr")||message.equals("reflushfre"))
            try {
                Thread.sleep(Const.THREAD_SLEEPTIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        refreshSchoolEvent();
    }

    public void showError(String err) {
        super.showError(err);
        bAdapter.setLoadEndView(R.layout.footer_loadend);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
