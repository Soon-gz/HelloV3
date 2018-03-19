package com.abings.baby.teacher.ui.main.fm.school;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.event.EventDetailActivity;
import com.abings.baby.teacher.ui.event.EventListMvpView;
import com.abings.baby.teacher.ui.event.EventListPresenter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/12/20.
 * description :
 */

public class SchoolDynamicFragment extends SchoolRecyclerViewFragment implements EventListMvpView {
    @Inject
    EventListPresenter presenter;

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(((BaseLibActivity) getActivity()).getActivityComponent(), getActivity()).inject(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        presenter.attachView(this);
//        refreshSchoolDynamic();
//        pageModel=new PageModel();
//        presenter.selectSchoolOnTeacherPage(Const.SCHOOL_DYNAMIC,pageModel.getPageNum());
    }
    @Override
    public void initListData() {
        pageModel = new PageModel();
        if (classesIDS.equals(SchoolMasterChooseActivity.RESULT_DATA)){
            presenter.selectSchoolOnTeacherPage(Const.SCHOOL_DYNAMIC, pageModel.getPageNum());
        }else {
            presenter.selectEventBySchoolAdminPage(classesIDS,Const.SCHOOL_DYNAMIC, pageModel.getPageNum());
        }
    }
    @Override
    public void initAdapter() {
        bAdapter = new SchoolRVAdapter(getContext(), bListData, EventDetailActivity.class);
        bAdapter.setLoadingView(R.layout.footer_more);
        bAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (pageModel.getPageNum() != pageModel.getPages()) {
                    if (classesIDS.equals(SchoolMasterChooseActivity.RESULT_DATA)){
                        presenter.selectSchoolOnTeacherPage(Const.SCHOOL_DYNAMIC, pageModel.getPageNum()+1);
                    }else {
                        presenter.selectEventBySchoolAdminPage(classesIDS,Const.SCHOOL_DYNAMIC, pageModel.getPageNum()+1);
                    }
                }
                else {
                    bAdapter.setLoadEndView(R.layout.footer_loadend);
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
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
            LogZS.i("更新动态数据："+busObject.toString());
            mSwipeRefreshLayout.setRefreshing(true);
            classesIDS = busObject.getMsg();
            pageModel.setPageNum(1);
            if (busObject.getMsg().equals(SchoolMasterChooseActivity.RESULT_DATA)){
                presenter.selectSchoolOnTeacherPage(Const.SCHOOL_DYNAMIC, pageModel.getPageNum());
            }else {
                presenter.selectEventBySchoolAdminPage(busObject.getMsg(),Const.SCHOOL_DYNAMIC, pageModel.getPageNum());
            }
        }
    }

    @Override
    public void showData(Object o) {

    }

    public void refreshSchoolDynamic() {
        bListData.clear();
        pageModel = new PageModel();
        presenter.selectSchoolOnTeacherPage(Const.SCHOOL_DYNAMIC, pageModel.getPageNum());
    }

    @Override
    public void showEventData(List<EventModel> list) {

    }

    @Override
    public void showListData(JSONArray jsonArray) {
        if(pageModel.getPageNum()==1){
            bListData.clear();
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            List<String> sList1=new ArrayList<>();
//            List<String> sList2= Arrays.asList((jsonObject.getString("imageurl").split(",")));
//            for(String s:sList2){
//                sList1.add(Const.URL_dynamicImgs + s);
//            }
            SchoolItem schoolItem = new SchoolItem(SchoolItem.TYPE_DYNAMIC, jsonObject.getString("description"),sList1,jsonObject.getString("headImageurl"));
            if (null != jsonObject.get("coverImageurl")) { //是视频
                schoolItem.setVideoImageUrl(Const.URL_dynamicFirstFrame + jsonObject.get("coverImageurl"));
                schoolItem.setVideoUrl(jsonObject.getString("imageurl")==null?"":jsonObject.getString("imageurl"));
            } else if(null!=jsonObject.getString("imageurl")) {
                List<String> sList2 = Arrays.asList((jsonObject.getString("imageurl").split(",")));
                for (String s : sList2) {
                    sList1.add(Const.URL_dynamicImgs + s);
                }
            }
            schoolItem.setId(jsonObject.getInteger("dynamicId"));
            schoolItem.setPublish(DateUtil.getDescriptionTimeFromTimestampInSchool(jsonObject.getLong("createTime")));
            schoolItem.setFromname(jsonObject.getString("teacherName"));
            schoolItem.setCreator(ZSApp.getInstance().getTeacherId().equals(""+jsonObject.getInteger("creatorId")));
            bListData.add(schoolItem);
        }
//        bAdapter.setNewData(bListData);
        bAdapter.notifyDataSetChanged();
        if(pageModel.getPageNum() != pageModel.getPages()){
            bAdapter.setLoadingView(R.layout.footer_more);
        }else {
            bAdapter.setLoadEndView(R.layout.footer_loadend);
        }
        mSwipeRefreshLayout.setRefreshing(false);
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
        if (message.equals("reflushfr"))
            refreshSchoolDynamic();
    }

    public void showError(String err) {
        super.showError(err);
        bAdapter.setLoadEndView(R.layout.footer_loadend);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
