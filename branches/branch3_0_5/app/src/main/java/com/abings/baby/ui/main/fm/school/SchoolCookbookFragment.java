package com.abings.baby.ui.main.fm.school;

import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.event.EventDetailActivity;
import com.abings.baby.ui.event.EventListMvpView;
import com.abings.baby.ui.event.EventListPresenter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.main.fm.school.SchoolItem;
import com.hellobaby.library.ui.main.fm.school.SchoolRecyclerViewFragment;
import com.hellobaby.library.utils.DateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/12/20.
 * description :
 */

public class SchoolCookbookFragment extends SchoolRecyclerViewFragment implements EventListMvpView {
    @Inject
    EventListPresenter presenter;
    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent((((BaseLibActivity) getActivity()).getActivityComponent()), getActivity()).inject(this);
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        presenter.attachView(this);
        presenter.selectSchooltByClassToOneBaby(Const.SCHOOL_COOKBOOK);
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
    public void showEventDetail(EventModel eventModel, String isjoin) {

    }

    @Override
    public void showListData(JSONArray jsonArray) {

        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
//            bListData.add(new SchoolItem(SchoolItem.TYPE_COOKBOOK, "10月14日 星期五",String.format(cook, "皮蛋瘦肉粥，馒头","猕猴桃","肉沫蒸蛋，炒山药，海带汤","酸奶，小面包"),"teacher0103.png"));
            String caidan="";
            JSONArray jsonArray1=jsonObject.getJSONArray("trecipeDay");
            Map<Integer,String> cookmap=new HashMap<>();
            for(int j=0;j<jsonArray1.size();j++) {
                JSONObject sonjsonObject=jsonArray1.getJSONObject(j);
                cookmap.put(sonjsonObject.getInteger("type"),sonjsonObject.getString("recipe"));
            }
            if(cookmap.containsKey(1)){
                caidan=caidan+"早餐："+cookmap.get(1)+"\n";
            }
            if(cookmap.containsKey(2)){
                caidan=caidan+"早点："+cookmap.get(2)+"\n";
            }
            if(cookmap.containsKey(3)){
                caidan=caidan+"午餐："+cookmap.get(3)+"\n";
            }
            if(cookmap.containsKey(4)){
                caidan=caidan+"午点："+cookmap.get(4)+"\n";
            }
            if(!caidan.equals("")){
                caidan=caidan.substring(0,caidan.length()-1);
            }
            SchoolItem schoolItem;
            schoolItem = new SchoolItem(SchoolItem.TYPE_COOKBOOK, DateUtil.long2cookTime(jsonObject.getLong("recipeDate")),caidan,jsonObject.getString("headImageurl"));
            schoolItem.setPublish(DateUtil.getDescriptionTimeFromTimestamp(jsonObject.getLong("createTime")));
            schoolItem.setFromname(jsonObject.getString("teacherName"));
            schoolItem.setPublish(DateUtil.getDescriptionTimeFromTimestamp(jsonObject.getLong("createTime")));
            bListData.add(schoolItem);
        }
        bAdapter.notifyDataSetChanged();
    }

}
