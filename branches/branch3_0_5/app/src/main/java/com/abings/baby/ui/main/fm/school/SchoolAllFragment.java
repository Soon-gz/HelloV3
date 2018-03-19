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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/12/20.
 * description :
 */

public class SchoolAllFragment extends SchoolRecyclerViewFragment implements EventListMvpView {
    String cook = "早餐：%s\n早点：%s\n午餐：%s\n午点：%s";
    String eventstring = "活动地点：%s\n活动时间：%s";
    @Inject
    EventListPresenter presenter;
    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent((((BaseLibActivity) getActivity()).getActivityComponent()), getActivity()).inject(this);
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        bAdapter.setIsAll(true);
        presenter.attachView(this);
        presenter.selectSchooltByClassToOneBaby(Const.SCHOOL_ALL);
        bAdapter.notifyDataSetChanged();
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
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if(jsonObject.containsKey("dynamicId")) {
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
                    schoolItem.setVideoUrl(url.substring(url.lastIndexOf("/")+1,url.length()));
                }
                schoolItem.setId(jsonObject.getInteger("dynamicId"));
//            schoolItem.setNewsUrl(jsonObject.getString("detailsUrl"));
                schoolItem.setPublish(DateUtil.getDescriptionTimeFromTimestamp(jsonObject.getLong("createTime")));
                schoolItem.setFromname(jsonObject.getString("teacherName"));
                bListData.add(schoolItem);
            }
            else if(jsonObject.containsKey("newsinfoId")) {
                String images=jsonObject.getString("newInfoImageurls");
                List<String> sList1 = new ArrayList<>();
                SchoolItem schoolItem=new SchoolItem(SchoolItem.TYPE_NEWS, jsonObject.getString("title"),Arrays.asList(images.split(",")),jsonObject.getString("headImageurl"));
                schoolItem.setId(jsonObject.getInteger("newsinfoId"));
                schoolItem.setNewsUrl(jsonObject.getString("detailsUrl"));
                schoolItem.setPublish(DateUtil.getDescriptionTimeFromTimestamp(jsonObject.getLong("createTime")));
                schoolItem.setFromname(jsonObject.getString("teacherName"));
                bListData.add(schoolItem);
            }else if(jsonObject.containsKey("recipeId")) {
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
            }else if(jsonObject.containsKey("eventId")) {
                EventModel event=JSONObject.parseObject(jsonObject.toJSONString(),EventModel.class);
                SchoolItem schoolitem=new SchoolItem(SchoolItem.TYPE_EVENT, event.getEventTitle(),String.format(eventstring, event.getEventAddress(), DateUtil.getFormatTimeFromTimestamp(event.getEventStartTime(),"yyyy年MM月dd日")),event,event.getHeadImageurl());
                schoolitem.setFromname(event.getTeacherName());
                schoolitem.setPublish(DateUtil.getDescriptionTimeFromTimestamp(event.getCreateTime()));
                bListData.add(schoolitem);
            }
        }
        bAdapter.notifyDataSetChanged();
    }
}
