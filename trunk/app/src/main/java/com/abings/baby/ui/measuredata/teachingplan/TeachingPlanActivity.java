package com.abings.baby.ui.measuredata.teachingplan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.abings.baby.R;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.ui.slide.SlideActivity;
import com.hellobaby.library.ui.slide.SlideBean;
import com.hellobaby.library.utils.PagingScrollHelper;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/1/17.
 */

public class TeachingPlanActivity extends BaseTitleActivity implements TeachingPlanMvpView, PagingScrollHelper.onPageChangeListener {
    @Inject
    TeachingPlanPresenter presenter;
    @BindView(R.id.tp_rv)
    RecyclerView recyclerView;
    List<TeachingPlanModel> teachingPlanModels = new ArrayList<>();
    protected RecyclerViewAdapterTeachPlanningList adapter;
    PagingScrollHelper scrollHelper = new PagingScrollHelper();
    LinearLayoutManager layoutManager;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_teachingplan;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        iniAdapter();
        Date date = new Date();
        setTitleText("本周");
        setBtnLeftClickFinish();
        presenter.selectTeachingplan3FromUserId();
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showTeachingPlanList(List list) {
        adapter.setNewData(list);
        recyclerView.scrollToPosition(1);
    }

    private void iniAdapter() {
        if (adapter == null) {
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerViewAdapterTeachPlanningList(bContext, teachingPlanModels, false);
            adapter.setOnItemClickListener(new OnItemClickListeners<TeachingPlanModel>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, TeachingPlanModel data2, int position) {
                    List<SlideBean> waterFallItemList = new ArrayList<>();
                    for (TeachingPlanModel data : teachingPlanModels) {
                        SlideBean waterFallItem = new SlideBean();
                        waterFallItem.setUrl(data.getImageurlAbs());
                        waterFallItemList.add(waterFallItem);
                    }
                    Intent intent = new Intent(bContext, SlideActivity.class);
                    intent.putParcelableArrayListExtra(SlideActivity.kDatas, (ArrayList<? extends Parcelable>) waterFallItemList);
                    intent.putExtra(SlideActivity.kCurrentPosition, position);
                    bContext.startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
            scrollHelper.setUpRecycleView(recyclerView);
            scrollHelper.setOnPageChangeListener(this);
        }
    }

    @Override
    public void onPageChange(int index) {
        switch (index){
            case -1: setTitleText("上周");
                break;
            case 0: setTitleText("本周");
                break;
            case 1: setTitleText("下周");
                break;
        }
    }
}
