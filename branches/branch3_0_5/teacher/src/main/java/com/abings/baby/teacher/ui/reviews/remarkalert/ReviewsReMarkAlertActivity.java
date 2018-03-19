package com.abings.baby.teacher.ui.reviews.remarkalert;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.reviews.ReviewsMvpView;
import com.abings.baby.teacher.ui.reviews.ReviewsPresenter;
import com.abings.baby.teacher.ui.reviews.remark.ReviewsReMarkActivity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.model.ReviewModel;
import com.hellobaby.library.ui.remarkalter.BaseRemarkAlertActivity;
import com.hellobaby.library.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/12/2.
 * description : 评语输入
 */

public class ReviewsReMarkAlertActivity extends BaseRemarkAlertActivity {
    public static final String kReMarkItem = "ReviewModel";
    ReviewModel reMarkItem = new ReviewModel();

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        bCivHead.setImageResource(R.drawable.main_normaluser_icon);
        setBtnRightDrawableRes(R.drawable.title_tick_black);
        reMarkItem = (ReviewModel) getIntent().getSerializableExtra(kReMarkItem);


        /**
         *     CircleImageView bCivHead;//头像
         TextView bTvName;//姓名
         TextView bTvScore;//顶部的分数
         RecyclerView bRecyclerView;
         EditText bEtRemark;//评语输入框
         TextView bTvCreateTime;//评价时间
         */
        if (getIntent().getBooleanExtra("isFinish",false)) {
            bIvRight.setVisibility(View.GONE);
            bEtRemark.setClickable(false);
        }
        bTvScore.setText(String.valueOf(reMarkItem.getScoreTotal()));
        bTvName.setText(reMarkItem.getBabyName());
        bEtRemark.setText(reMarkItem.getRemark());
//        bTvCreateTime.setText("2016年12月23日 16:02:20");//TODO 这个什么情况
        bTvCreateTime.setVisibility(View.GONE);
        ImageLoader.loadHeadTarget(this, reMarkItem.getHeadImageurlAbs(), bCivHead);
        //头像
//        bCivHead
        List<Item> list = new ArrayList<>();
        list.add(new Item("吃饭", reMarkItem.getEating()));
        list.add(new Item("喝水", reMarkItem.getDrinking()));
        list.add(new Item("午休", reMarkItem.getNoonbreak()));
        list.add(new Item("上厕所", reMarkItem.getToilet()));
        list.add(new Item("活动", reMarkItem.getActivity()));
        bAdapter.setNewData(list);
    }

    @Override
    protected void initDaggerInject() {

    }

    protected void btnRightOnClick(View v) {
        Intent intent = getIntent();
        reMarkItem.setRemark(bEtRemark.getText().toString());
        reMarkItem.setCreateId(Integer.parseInt(ZSApp.getInstance().getTeacherId()));
        intent.putExtra(kReMarkItem, reMarkItem);
        intent.putExtra("posion", getIntent().getIntExtra("posion", 0));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showData(Object o) {

    }
}
