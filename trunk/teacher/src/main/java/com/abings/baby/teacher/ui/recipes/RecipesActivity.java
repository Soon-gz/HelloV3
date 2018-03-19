package com.abings.baby.teacher.ui.recipes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.main.fm.school.SchoolItem;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnLoadMoreListener;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 教师食谱
 */
public class RecipesActivity extends BaseTitleActivity implements RecipesMvp,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.recipes_rv)
    RecyclerView recyclerView;
    public PageModel pageModel = new PageModel();
    private List<SchoolItem> mContentList = new ArrayList<>();
    BaseAdapter<SchoolItem> baseAdapter;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    RecipesPresenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_recipes;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        pageModel = new PageModel();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(com.hellobaby.library.R.id.school_swipeRefresh);
        setTitleText("食谱");
        setBtnLeftClickFinish();
        initAdapter();
        initListData();
        mSwipeRefreshLayout.setColorSchemeResources(com.hellobaby.library.R.color.colorPrimary, com.hellobaby.library.R.color.colorAccent, com.hellobaby.library.R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public void initAdapter() {
        baseAdapter = new BaseAdapter<SchoolItem>(bContext, mContentList, true) {
            @Override
            protected void convert(ViewHolder holder, SchoolItem data) {
                holder.setText(R.id.recipesItem_tv_title, data.getName());
                holder.setText(R.id.recipesItem_tv_content, data.getUrl());
                holder.setText(R.id.from_name, "来自于 "+data.getFromname());
                holder.setText(R.id.publish_time, data.getPublish());
                ImageView im = holder.getView(R.id.recipesItem_iv_head);
                ImageLoader.loadHeadTarget(mContext, Const.URL_schoolHead + data.getPhoto(), im);
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.fragment_recipes;
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        baseAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (pageModel.getPageNum() != pageModel.getPages())
                    presenter.getRecipesPage(pageModel.getPageNum() + 1);
                else {
                    baseAdapter.setLoadEndView(R.layout.footer_loadend);
                }
            }
        });
        baseAdapter.setLoadingView(R.layout.footer_more);
        recyclerView.setAdapter(baseAdapter);


        mSwipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void showData(Object o) {

    }

    @Override
    public void showListData(JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.size(); i++) {
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
            mContentList.add(schoolItem);
        }
        baseAdapter.notifyDataSetChanged();
        if (pageModel.getPageNum() != pageModel.getPages()) {
            baseAdapter.setLoadingView(R.layout.footer_more);
        } else {
            baseAdapter.setLoadEndView(R.layout.footer_loadend);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
        if (pageModel.getPageNum() != pageModel.getPages()) {
            baseAdapter.setLoadingView(R.layout.footer_more);
        } else {
            baseAdapter.setLoadEndView(R.layout.footer_loadend);
        }
    }

    @Override
    public void onRefresh() {
        initListData();
    }

    public void initListData() {
        mContentList.clear();
        pageModel=new PageModel();
        presenter.getRecipesPage(pageModel.getPageNum());
//        presenter.getRecipes();
    }
}
