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
//        presenter.selectSchooltByClassToOneBaby(Const.SCHOOL_COOKBOOK);
        pageModel=new PageModel();
    }

    @Override
    public void initAdapter() {
        bAdapter=new SchoolRVAdapter(getContext(),bListData, EventDetailActivity.class);
        bAdapter.setLoadingView(R.layout.footer_more);
        bAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if(pageModel.getPageNum()!=pageModel.getPages())
                    presenter.selectSchooltByClassToOneBabyPage(Const.SCHOOL_COOKBOOK,pageModel.getPageNum()+1);
                else        {
                    bAdapter.setLoadEndView(R.layout.footer_loadend);
                }
            }
        });
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void initListData() {
        pageModel=new PageModel();
        presenter.selectSchooltByClassToOneBabyPage(Const.SCHOOL_COOKBOOK,pageModel.getPageNum());
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
            if(!caidan.trim().equals(""))
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
