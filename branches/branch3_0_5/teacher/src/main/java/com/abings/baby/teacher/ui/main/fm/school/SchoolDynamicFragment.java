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
        DaggerUtils.getActivityComponent(((BaseLibActivity)getActivity()).getActivityComponent(),getActivity()).inject(this);
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        presenter.attachView(this);
        presenter.selectSchoolOnTeacher(Const.SCHOOL_DYNAMIC);
    }

    @Override
    public void initAdapter() {
        bAdapter = new SchoolRVAdapter(getContext(), bListData, EventDetailActivity.class);
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showError(String err) {

    }

    @Override
    public void showEventData(List<EventModel> list) {

    }

    @Override
    public void showListData(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            List<String> sList1=new ArrayList<>();
            if(null!=jsonObject.get("imageurl")) {
                sList1 = (List<String>)jsonObject.get("imageurl");
                for (int j = 0; j < sList1.size(); j++) {
                    sList1.set(j, Const.URL_dynamicImgs + sList1.get(j));
                }
            }
            SchoolItem schoolItem = new SchoolItem(SchoolItem.TYPE_DYNAMIC, jsonObject.getString("description"),sList1,jsonObject.getString("headImageurl"));
//            SchoolItem schoolItem= new SchoolItem(SchoolItem.TYPE_DYNAMIC, jsonObject.getString("title"),Arrays.asList(images.split(","),jsonObject.getString("headImageurl"));
            if(null!=jsonObject.get("coverImageurl"))
                schoolItem.setVideoImageUrl(Const.URL_dynamicFirstFrame+((List<String>) jsonObject.get("coverImageurl")).get(0));
            if (jsonObject.getString("schoolType").equals("22.0")&&null!=jsonObject.get("imageurl")) {
                String url=((List<String>) jsonObject.get("imageurl")).get(0);
                    schoolItem.setVideoUrl(url.substring(url.lastIndexOf("/") + 1, url.length()));
            }
            schoolItem.setId(jsonObject.getInteger("dynamicId"));
//            schoolItem.setNewsUrl(jsonObject.getString("detailsUrl"));
            schoolItem.setPublish(DateUtil.getDescriptionTimeFromTimestamp(jsonObject.getLong("createTime")));
            schoolItem.setFromname(jsonObject.getString("teacherName"));
            bListData.add(schoolItem);
        }
//        bAdapter.setNewData(bListData);
        bAdapter.notifyDataSetChanged();
    }
}
