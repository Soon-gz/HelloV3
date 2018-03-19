package com.abings.baby.teacher.ui.publishteachingplan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.ui.slide.SlideActivity;
import com.hellobaby.library.ui.slide.SlideBean;
import com.hellobaby.library.utils.DeviceUtils;
import com.hellobaby.library.utils.GravitySnapHelper;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * 教学计划列表
 */
public class TeachingPlanListActivity extends BaseTitleActivity implements TeachingPlanMvpView,SwipeRefreshLayout.OnRefreshListener{
    public SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    TeachingPlanPresenter presenter;
    private RecyclerView recyclerView;
    private List<TeachingPlanModel> bList=new ArrayList<>();
    private RecyclerViewAdapterPlanList adapter;

    @Override
    protected int getContentViewLayoutID() {
       return R.layout.activity_teachingplanlist;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.tp_swipeRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(com.hellobaby.library.R.color.colorPrimary, com.hellobaby.library.R.color.colorAccent, com.hellobaby.library.R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        setTitleText("教学计划");
        setBtnLeftClickFinish();
        setBtnRightDrawableRes(R.drawable.title_add);
        bIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeachingPlanListActivity.this, PublishTeachingPlanActivity.class);
                startActivityForResult(intent,100);
            }
        });
        iniAdapter();
        presenter.selectTeachingplanByTeacherId();
    }
    private void iniAdapter() {
        if (adapter == null) {
            adapter=new RecyclerViewAdapterPlanList(this,bList,false);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView = (RecyclerView)findViewById(R.id.techerplan_rv);
            recyclerView.setLayoutManager(layoutManager);
            adapter.setOnItemClickListener(new OnItemClickListeners<TeachingPlanModel>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, TeachingPlanModel data2, int position) {
                    Intent intent = new Intent(bContext, TeachingPlanDetailActivity.class);
                    intent.putExtra("tp_model",data2);
                    bContext.startActivityForResult(intent, Const.NORMAL_ACTIVITY_RESULT);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSwipeRefreshLayout.setRefreshing(true);
        presenter.selectTeachingplanByTeacherId();
    }

    @Override
    public void showTeachingPlanList(List list) {
        adapter.setNewData(list);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void publishPlanSuccess() {

    }

    @Override
    public void publishDeleteSuccess(int posion) {

    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void onRefresh() {
        presenter.selectTeachingplanByTeacherId();
    }

    @Override
    public void showError(String err) {
        super.showError(err);
        bList.clear();
        adapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
