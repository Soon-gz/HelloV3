package com.abings.baby.teacher.ui.reviews.remark;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.reviews.ReviewsMvpView;
import com.abings.baby.teacher.ui.reviews.ReviewsPresenter;
import com.abings.baby.teacher.ui.reviews.mark.ReviewsMarkActivity;
import com.abings.baby.teacher.ui.reviews.remarkalert.ReviewsReMarkAlertActivity;
import com.abings.baby.teacher.ui.reviews.type.ReviewsTypeActivity;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.ReviewModel;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by zwj on 2016/12/2.
 * description : 评语
 * TODO 这里要做的网络请求，都放在父类的present中
 */

public class ReviewsReMarkActivity extends ReviewsMarkActivity implements ReviewsMvpView {
    @Inject
    ReviewsPresenter presenter;
    @BindView(R.id.reviewsMark_ll_explain)
    LinearLayout llExplain;//右上角的说明，在最后的评价的时候，隐藏
    List<ReviewModel> list = new ArrayList<>();
    BaseAdapter<ReviewModel> adapter;
    final static int REVIEW_OK = 100;
    private boolean isFinish = false;

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
        llExplain.setVisibility(View.GONE);
        setTitleText("评语");
        setBtnLeftClickFinish();
        setBtnRightDrawableRes(R.drawable.title_tick_black);
        isFinish = getIntent().getBooleanExtra(ReviewsTypeActivity.kFinish,false);

        if(isFinish){
            bIvRight.setVisibility(View.GONE);
        }
        adapter = new BaseAdapter<ReviewModel>(bContext, list, false) {
            @Override
            protected void convert(ViewHolder holder, final ReviewModel data) {

                String re = "";
                int scoreTotal = Integer.valueOf(data.getScoreTotal());
                if(scoreTotal<=5){
                    re = "宝宝今天表现很好,加油";
                }else if(scoreTotal<= 10){
                    re = "宝宝今天棒棒哒,加油";
                }else {
                    re = "宝宝今天棒棒哒,继续保持";
                }
                list.get(mPosion).setRemark(re);
                list.get(mPosion).setCreateId(Integer.parseInt(ZSApp.getInstance().getTeacherId()));
                TextView bTvScore = holder.getView(R.id.itemReviewsReMark_tv_score);
                bTvScore.setText(data.getScoreTotal()+"");//分数
                TextView tvName = holder.getView(R.id.itemReviewsReMark_tv_name);
                tvName.setText(data.getBabyName());
                TextView tvContent = holder.getView(R.id.itemReviewsReMark_tv_content);
                String reMark = data.getRemark();

                if(reMark!=null && "".equals(reMark)){
                    data.setRemark(re);
                }

                tvContent.setText(data.getRemark());
                ImageView ivHead = holder.getView(R.id.itemReviewsReMark_civ_head);
                ImageLoader.loadHeadTarget(bContext,data.getHeadImageurlAbs(), ivHead);
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.recycler_item_reviews_remark;
            }

        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(bContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListeners<ReviewModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, ReviewModel data, int position) {
                // 进入到评语界面
                Intent intent = new Intent(bContext, ReviewsReMarkAlertActivity.class);
                intent.putExtra("ReviewModel", (Serializable) data);
                intent.putExtra("day", getIntent().getStringExtra("day"));
                intent.putExtra("posion", position);
                intent.putExtra("isFinish", isFinish);
                startActivityForResult(intent, REVIEW_OK);
            }
        });
        presenter.selectTevaluationComments(getIntent().getStringExtra("day"), getIntent().getStringExtra("class"));
    }

    public void showJSONArray(JSONArray jsonObject) {
        adapter.setNewData(JSONArray.parseArray(jsonObject.toJSONString(), ReviewModel.class));
    }

    @Override
    public void updateFinish() {
        setResult(ReviewsMarkActivity.RESULT_CODE);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            list.set(data.getIntExtra("posion", 0), (ReviewModel) data.getSerializableExtra("ReviewModel"));
            adapter.notifyDataSetChanged();
        }
    }
    protected void btnRightOnClick(View v) {
        presenter.insertTevaluationComments(getIntent().getStringExtra("day"),JSONArray.toJSONString(list));
    }
}
