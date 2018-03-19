package com.abings.baby.ui.measuredata.teachingplan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;

import com.abings.baby.R;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.ui.slide.SlideActivity;
import com.hellobaby.library.ui.slide.SlideBean;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.PagingScrollHelper;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.BottomPickerDateDialog;
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

public class TeachingPlanActivity extends BaseTitleActivity implements TeachingPlanMvpView,PagingScrollHelper.onPageChangeListener{
    @Inject
    TeachingPlanPresenter presenter;
    @BindView(R.id.tp_rv)
    RecyclerView recyclerView;
    List<TeachingPlanModel> teachingPlanModels=new ArrayList<>();
    protected RecyclerViewAdapterTeachPlanningList adapter;
    PagingScrollHelper scrollHelper = new PagingScrollHelper();
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
        Date date=new Date();
        setTitleText("教学计划");
        setBtnLeftClickFinish();
        presenter.selectTeachingplanFromUserId(DateUtil.getFormatTimeFromTimestamp(date.getTime(),DateUtil.FORMAT_DATE));
        setBtnRightDrawableRes(R.drawable.select_date_black);
        bIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialogUtils.getBottomDatePickerDialog(bContext, null, true, new BottomPickerDateDialog.BottomOnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth, String showData) {
                        String yMd = year+"-"+monthOfYear+"-"+dayOfMonth;
                        presenter.selectTeachingplanFromUserId(yMd);
                    }
                });
            }
        });
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showTeachingPlanList(List list) {
        adapter.setNewData(list);
    }

    private void iniAdapter() {
        if (adapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerViewAdapterTeachPlanningList(bContext,teachingPlanModels, false);
            adapter.setOnItemClickListener(new OnItemClickListeners<TeachingPlanModel>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, TeachingPlanModel data2, int position) {
                    List<SlideBean> waterFallItemList = new ArrayList<>();
                    for (TeachingPlanModel data:teachingPlanModels) {
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

    }
}
