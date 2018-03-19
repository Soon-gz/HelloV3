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
import com.hellobaby.library.data.model.BabyAttendancesModel;
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
 */

public class ReviewsReMarkActivity extends ReviewsMarkActivity implements ReviewsMvpView {
    @Inject
    ReviewsPresenter presenter;
    @BindView(R.id.reviewsMark_ll_explain)
    LinearLayout llExplain;//右上角的说明，在最后的评价的时候，隐藏
    @BindView(R.id.reviewsMark_tv_className)
    TextView tvClassName;
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
        tvClassName.setText(getIntent().getStringExtra("classname"));
        isFinish = getIntent().getBooleanExtra(ReviewsTypeActivity.kFinish,false);

        if(isFinish){
            bIvRight.setVisibility(View.GONE);
        }

        adapter = new BaseAdapter<ReviewModel>(bContext, list, false) {
            @Override
            protected void convert(ViewHolder holder, final ReviewModel data) {


                list.get(mPosion).setCreateId(Integer.parseInt(ZSApp.getInstance().getTeacherId()));
                TextView bTvScore = holder.getView(R.id.itemReviewsReMark_tv_score);
                bTvScore.setText(data.getScoreTotal()+"");//分数
                TextView tvName = holder.getView(R.id.itemReviewsReMark_tv_name);
                tvName.setText(data.getBabyName());
                TextView tvContent = holder.getView(R.id.itemReviewsReMark_tv_content);

                tvContent.setText(data.getRemark());
                ImageView ivHead = holder.getView(R.id.itemReviewsReMark_civ_head);
//                ImageLoader.load(bContext,R.drawable.head_holder,ivHead);
                ivHead.setImageResource(R.drawable.head_holder);
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
        List<ReviewModel> listd = JSONArray.parseArray(jsonObject.toJSONString(), ReviewModel.class);

        List<BabyAttendancesModel> reviewModels = (List<BabyAttendancesModel>) getIntent().getSerializableExtra("BabyAttendancesModel");
        List<Integer> babyIds = new ArrayList<>();
        if (reviewModels != null) {
            for (BabyAttendancesModel model : reviewModels) {
                babyIds.add(model.getBabyId());
            }
        }
        if(!isFinish){
            for (ReviewModel remodel:listd) {
                String re = "";
                if (babyIds.size() > 0 && babyIds.contains(remodel.getBabyId())) {
                    int index = babyIds.indexOf(remodel.getBabyId());
                    if(reviewModels.get(index).isReviewsStateAttend()){
//                        宝宝来了：
                        re = "宝宝今天表现不错，明天继续加油";
                    }else if (reviewModels.get(index).isReviewsStateAbsence()) {
//                        宝宝没来：
                        re = "下次缺席，爸爸妈妈记得请假哦";
                    } else {
//                        宝宝请假：
                        re = "宝宝今天请假，期待明天有好的表现";
                    }
                }else {
                    int scoreTotal = Integer.valueOf(remodel.getScoreTotal());
                    if(scoreTotal<=5){
                        re = "宝宝今天表现很好,加油";
                    }else if(scoreTotal<= 10){
                        re = "宝宝今天棒棒哒,加油";
                    }else {
                        re = "宝宝今天棒棒哒,继续保持";
                    }
                }
                remodel.setCreateId(Integer.parseInt(ZSApp.getInstance().getTeacherId()));
                remodel.setRemark(re);
            }
        }


        adapter.setNewData(listd);
    }

    @Override
    public void updateFinish() {
        setResult(ReviewsMarkActivity.RESULT_CODE);
        presenter.insertRecord(getIntent().getStringExtra("class"));
    }

    @Override
    public void insertRecord() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            int pos = data.getIntExtra("posion", 0);
            list.get(pos).setRemark(((ReviewModel) data.getSerializableExtra("ReviewModel")).getRemark());
            adapter.notifyItemChanged(pos);
        }
    }
    protected void btnRightOnClick(View v) {
        presenter.insertTevaluationComments(getIntent().getStringExtra("day"),JSONArray.toJSONString(list));
    }
}
