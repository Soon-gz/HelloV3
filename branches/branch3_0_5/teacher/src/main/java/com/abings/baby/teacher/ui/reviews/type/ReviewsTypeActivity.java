package com.abings.baby.teacher.ui.reviews.type;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.abings.baby.teacher.ui.reviews.ReviewsMvpView;
import com.abings.baby.teacher.ui.reviews.ReviewsPresenter;
import com.abings.baby.teacher.ui.reviews.mark.ReviewsMarkActivity;
import com.abings.baby.teacher.ui.reviews.remark.ReviewsReMarkActivity;
import com.abings.baby.teacher.widget.custom.CircleTextView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.BottomPickerDateDialog;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by zwj on 2016/12/2.
 * description : 评语类型界面
 */

public class ReviewsTypeActivity extends BaseTitleActivity implements ReviewsMvpView{
    @Inject
    ReviewsPresenter presenter;
    @BindView(R.id.reviewsType_rv)
    RecyclerView recyclerView;
    private BaseAdapter<Item> baseAdapter;
    private List<Item> list= new ArrayList<>();
    String day="";
    int[] colorArray;
    int[] colorArrayFF;
    private String currentDate;
    public final static String kFinish = "isFinish";
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_reviews_type;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        colorArray = getResources().getIntArray(R.array.teachColorArray);
        colorArrayFF = getResources().getIntArray(R.array.teachColorArrayFF);
        setBtnLeftClickFinish();
        setBtnRightDrawableRes(R.drawable.select_date_black);
        LinearLayoutManager layoutManager = new LinearLayoutManager(bContext);
        recyclerView.setLayoutManager(layoutManager);
        baseAdapter = new BaseAdapter<Item>(bContext, list, false) {
            @Override
            protected void convert(ViewHolder holder, Item data) {
                CircleTextView ctv = holder.getView(R.id.itemReviewsType_ctv_icon);
                ctv.setText(data.name);
                ctv.setBackgroundColor(data.circleColor);
                LinearLayout linearLayout = holder.getView(R.id.itemReviewsType_ll_root);
                linearLayout.setBackgroundColor(data.bgColor);
                TextView tvComplete = holder.getView(R.id.itemReviewsType_tv_complete);
                if ("评语".equals(data.name)) {
                    int count = mDatas.size() - 1;
                    int completeCount = 0;
                    for (int i = 0; i < count; i++) {
                        if (mDatas.get(i).isComplete()) {
                            completeCount++;
                        }
                    }
                    tvComplete.setText(completeCount + "/" + count);
                } else {
                    tvComplete.setText(data.isComplete() ? "已完成" : "未完成");
                }
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.recycler_item_reviews_type;
            }
        };
        recyclerView.setAdapter(baseAdapter);
        baseAdapter.setOnItemClickListener(new OnItemClickListeners<Item>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, Item data, int position) {
                boolean isFinish = false;
                if ("评语".equals(data.name)) {
                    int count = list.size() - 1;
                    int completeCount = 0;
                    for (int i = 0; i < count; i++) {
                        if (list.get(i).isComplete()) {
                            completeCount++;
                        }
                    }
                    if (completeCount < count) {
                        showToast("完成以上评价后再来写评语");
                        return;
                    }
                    Intent intent = new Intent(bContext, ReviewsReMarkActivity.class);
                    intent.putExtra("day",day);

                    if(data.isComplete()||((!"".equals(day))&&(!day.equals(currentDate)))){
                        //完成
                        isFinish = true;
                    }
                    intent.putExtra(kFinish,isFinish);
                    intent.putExtra("class",getIntent().getStringExtra("class"));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(bContext, ReviewsMarkActivity.class);
                    intent.putExtra("type",position);
                    intent.putExtra("day",day);
                    if(data.isComplete()||((!"".equals(day))&&(!day.equals(currentDate)))){
                        //完成
                        isFinish = true;
                    }
                    intent.putExtra(kFinish,isFinish);
                    intent.putExtra("class",getIntent().getStringExtra("class"));
                    startActivityForResult(intent, ReviewsMarkActivity.RESULT_CODE);
                }

            }
        });
        presenter.SelectTevaluationComplete(day,getIntent().getStringExtra("class"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ReviewsMarkActivity.RESULT_CODE) {
            presenter.SelectTevaluationComplete("",getIntent().getStringExtra("class"));
        }
    }

    @Override
    public void showData(Object o) {

    }


    @Override
    public void showJSONObject(JSONObject jsonObject) {
        list= new ArrayList<>();
        list.add(new Item("吃饭", colorArrayFF[0], colorArray[0], (jsonObject.getInteger("isEating")+"").equals("0")?"0":"1"));
        list.add(new Item("喝水", colorArrayFF[1], colorArray[1], (jsonObject.getInteger("isDrinking")+"").equals("0")?"0":"1"));
        list.add(new Item("午休", colorArrayFF[2], colorArray[2], (jsonObject.getInteger("isNoonBreak")+"").equals("0")?"0":"1"));
        list.add(new Item("上厕所", colorArrayFF[3], colorArray[3], (jsonObject.getInteger("isToilet")+"").equals("0")?"0":"1"));
        list.add(new Item("活动", colorArrayFF[4], colorArray[4], (jsonObject.getInteger("isActivity")+"").equals("0")?"0":"1"));
        list.add(new Item("评语", colorArrayFF[5], colorArray[5], (jsonObject.getInteger("isRemark")+"").equals("0")?"0":"1"));
        currentDate = jsonObject.getString("currentDate");
        baseAdapter.setNewData(list);
        if(!day.equals(""))
            setTitleText(day);
        else
            setTitleText(currentDate);
    }

    @Override
    public void showJSONArray(JSONArray jsonObject) {

    }

    @Override
    public void updateFinish() {

    }

    private class Item {
        public String name;
        public int circleColor;
        public int bgColor;
        public String complete;

        public Item(String name, int circleColor, int bgColor, String complete) {
            this.name = name;
            this.circleColor = circleColor;
            this.bgColor = bgColor;
            this.complete = complete;
        }

        /**
         * 完成
         *
         * @return
         */
        public boolean isComplete() {
            return "1".equals(complete);
        }
    }
    protected void btnRightOnClick(View v) {
        BottomDialogUtils.getBottomDatePickerDialog(this,day,true,new BottomPickerDateDialog.BottomOnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth, String showDate) {
                // 修改
                day = DateUtil.upServerDateFormat(showDate);
                presenter.SelectTevaluationComplete(day,getIntent().getStringExtra("class"));
            }
        });
    }
}
