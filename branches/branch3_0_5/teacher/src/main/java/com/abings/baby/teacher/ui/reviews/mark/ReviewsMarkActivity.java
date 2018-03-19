package com.abings.baby.teacher.ui.reviews.mark;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.abings.baby.teacher.ui.reviews.ReviewsMvpView;
import com.abings.baby.teacher.ui.reviews.ReviewsPresenter;
import com.abings.baby.teacher.ui.reviews.type.ReviewsTypeActivity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.model.ReviewModel;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by zwj on 2016/12/2.
 * description : 评价评分
 * TODO 根据不同的类型进行不通的标题
 */

public class ReviewsMarkActivity extends BaseTitleActivity implements ReviewsMvpView{
    @Inject
    ReviewsPresenter presenter;
    @BindView(R.id.reviewsMark_rv)
    protected RecyclerView recyclerView;
    @BindView(R.id.reviewsMark_tv_className)
    protected TextView tvClassName;
    public static final int RESULT_CODE = 111;
    BaseAdapter<ReviewModel> adapter;
    List<ReviewModel> list = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_reviews_mark;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        switch (getIntent().getIntExtra("type",0)){
            case ReviewModel.TYPE_EAT:
                setTitle("吃饭");
                break;
            case ReviewModel.TYPE_DRINK:
                setTitle("喝水");
                break;
            case ReviewModel.TYPE_NOONBREAK:
                setTitle("午休");
                break;
            case ReviewModel.TYPE_TOILET:
                setTitle("上厕所");
                break;
            case ReviewModel.TYPE_ACTIVITY:
                setTitle("活动");
                break;
        }
        setBtnLeftClickFinish();
        setBtnRightDrawableRes(R.drawable.title_tick_black);
        if(getIntent().getBooleanExtra(ReviewsTypeActivity.kFinish,false)){
            bIvRight.setVisibility(View.GONE);
        }
        adapter = new BaseAdapter<ReviewModel>(bContext, list, false) {
            @Override
            protected void convert(ViewHolder holder, final ReviewModel data) {
                ImageView head = holder.getView(R.id.itemReviewsMark_civ_head);
                ImageLoader.loadHeadTarget(bContext, data.getHeadImageurlAbs(),head);
                TextView tvName = holder.getView(R.id.itemReviewsMark_tv_name);
                final ImageView ivOne = holder.getView(R.id.itemReviewsMark_iv_one);
                final ImageView ivTwo = holder.getView(R.id.itemReviewsMark_iv_two);
                final ImageView ivThree = holder.getView(R.id.itemReviewsMark_iv_three);
                tvName.setText(data.getBabyName());
                setStart(data.getScore(), ivOne, ivTwo, ivThree);
                ivOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.setScore(1);
                        setStart(data.getScore(), ivOne, ivTwo, ivThree);
                    }
                });
                ivTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.setScore(2);
                        setStart(data.getScore(), ivOne, ivTwo, ivThree);
                    }
                });
                ivThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.setScore(3);
                        setStart(data.getScore(), ivOne, ivTwo, ivThree);
                    }
                });
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.recycler_item_reviews_mark;
            }

            private void setStart(int num, ImageView ivOne, ImageView ivTwo, ImageView ivThree) {
                if (num == 1) {
                    ivOne.setSelected(true);
                    ivTwo.setSelected(false);
                    ivThree.setSelected(false);
                } else if (num == 2) {
                    ivOne.setSelected(true);
                    ivTwo.setSelected(true);
                    ivThree.setSelected(false);
                } else if (num == 3) {
                    ivOne.setSelected(true);
                    ivTwo.setSelected(true);
                    ivThree.setSelected(true);
                }
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(bContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        presenter.selectTevaluationScore(getIntent().getStringExtra("day"),getIntent().getStringExtra("class"),getIntent().getIntExtra("type",0)+"");
    }

    @Override
    public void showData(Object o) {

    }
    @Override
    public void showJSONObject(JSONObject jsonObject) {

    }

    @Override
    public void showJSONArray(JSONArray jsonObject) {
        List<ReviewModel> reviewModelList = JSONArray.parseArray(jsonObject.toJSONString(), ReviewModel.class);
        //默认评分为3分
        if(!getIntent().getBooleanExtra(ReviewsTypeActivity.kFinish,false)){
            for (ReviewModel reviewModel: reviewModelList) {
                reviewModel.setScore(3);
            }
        }
        adapter.setNewData(reviewModelList);
    }

    @Override
    public void updateFinish() {
        setResult(ReviewsMarkActivity.RESULT_CODE);
        finish();
    }

    protected void btnRightOnClick(View v) {
        String babyids="";
        String scores="";
        for(ReviewModel reviewModel:list){
            babyids=babyids+reviewModel.getBabyId()+",";
            scores=scores+reviewModel.getScore()+",";
        }
        if(!babyids.equals(""))
            babyids= babyids.substring(0,babyids.length()-1);
        if(!scores.equals(""))
            scores= scores.substring(0,scores.length()-1);
            presenter.insertTevaluationScore(getIntent().getStringExtra("day"),getIntent().getIntExtra("type",0)+"",scores,babyids);
    }
}
