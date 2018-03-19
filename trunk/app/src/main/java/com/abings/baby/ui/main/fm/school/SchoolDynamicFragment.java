package com.abings.baby.ui.main.fm.school;

import com.abings.baby.R;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.event.EventDetailActivity;
import com.abings.baby.ui.event.EventListMvpView;
import com.abings.baby.ui.event.EventListPresenter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.main.fm.school.SchoolItem;
import com.hellobaby.library.ui.main.fm.school.SchoolRecyclerViewFragment;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.baseadapter.OnLoadMoreListener;

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
        DaggerUtils.getActivityComponent((((BaseLibActivity)getActivity()).getActivityComponent()),getActivity()).inject(this);
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        presenter.attachView(this);
        pageModel=new PageModel();
//        presenter.selectSchooltByClassToOneBaby(Const.SCHOOL_DYNAMIC);
    }

    @Override
    public void initAdapter() {
        bAdapter=new SchoolRVAdapter(getContext(),bListData, EventDetailActivity.class);
        bAdapter.setLoadingView(R.layout.footer_more);
        bAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if(pageModel.getPageNum()!=pageModel.getPages())
                    presenter.selectSchooltByClassToOneBabyPage(Const.SCHOOL_DYNAMIC,pageModel.getPageNum()+1);
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
        presenter.selectSchooltByClassToOneBabyPage(Const.SCHOOL_DYNAMIC,pageModel.getPageNum());
    }


    @Override
    public void showData(Object o) {

    }


    @Override
    public void showEventData(List<EventModel> list) {

    }

    @Override
    public void showEventDetail(EventModel eventModel, String isjoin) {

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
            bListData.add(schoolItem);
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
    public void setPageModel(PageModel pageModel) {
        this.pageModel=pageModel;
        if(pageModel.getPageNum() != pageModel.getPages()){
            bAdapter.setLoadingView(R.layout.footer_more);
        }else {
            bAdapter.setLoadEndView(R.layout.footer_loadend);
        }
    }
}
