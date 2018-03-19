package com.abings.baby.teacher.ui.main.fm.school;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.event.EventDetailActivity;
import com.abings.baby.teacher.ui.event.EventListMvpView;
import com.abings.baby.teacher.ui.event.EventListPresenter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.main.fm.school.SchoolItem;
import com.hellobaby.library.ui.main.fm.school.SchoolRecyclerViewFragment;
import com.hellobaby.library.utils.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/12/20.
 * description :
 */

public class SchoolNewsFragment extends SchoolRecyclerViewFragment implements EventListMvpView{
    @Inject
    EventListPresenter presenter;
    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(((BaseLibActivity)getActivity()).getActivityComponent(),getActivity()).inject(this);
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        presenter.attachView(this);
        presenter.selectSchoolOnTeacher(Const.SCHOOL_NEWS);
    }

    @Override
    public void initAdapter() {
        bAdapter=new SchoolRVAdapter(getContext(),bListData, EventDetailActivity.class);
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showEventData(List<EventModel> list) {

    }

    @Override
    public void showListData(JSONArray jsonArray) {
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            String images=jsonObject.getString("newInfoImageurls");
            List<String> sList1 = new ArrayList<>();
            SchoolItem schoolItem=new SchoolItem(SchoolItem.TYPE_NEWS, jsonObject.getString("title"),Arrays.asList(images.split(",")),jsonObject.getString("headImageurl"));
            schoolItem.setId(jsonObject.getInteger("newsinfoId"));
            schoolItem.setNewsUrl(jsonObject.getString("detailsUrl"));
            schoolItem.setPublish(DateUtil.getDescriptionTimeFromTimestamp(jsonObject.getLong("createTime")));
            schoolItem.setFromname(jsonObject.getString("teacherName"));
            bListData.add(schoolItem);
        }
//        bAdapter.setNewData(bListData);
        bAdapter.notifyDataSetChanged();
    }
}
