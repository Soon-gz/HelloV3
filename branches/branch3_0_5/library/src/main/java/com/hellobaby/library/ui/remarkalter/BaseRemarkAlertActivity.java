package com.hellobaby.library.ui.remarkalter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zwj on 2016/12/2.
 * description : 评语输入
 * 1.这个界面的星星评价不能修改
 * 2.老师端可以输入评语，家长端不可以
 */

public abstract class BaseRemarkAlertActivity<T> extends BaseLibTitleActivity<T> {


    protected CircleImageView bCivHead;//头像
    protected TextView bTvName;//姓名
    protected TextView bTvScore;//顶部的分数
    protected RecyclerView bRecyclerView;
    protected EditText bEtRemark;//评语输入框
    protected TextView bTvCreateTime;//评价时间
    protected BaseAdapter<Item> bAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_reviews_remark_alter;
    }


    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setBtnLeftClickFinish();
        initView();
        initRecyclerView();
    }

//    protected List<Item> getScoreList() {
//        List<Item> list = new ArrayList<>();
//        list.add(new Item("吃饭", 3));
//        list.add(new Item("喝水", 2));
//        list.add(new Item("午休", 2));
//        list.add(new Item("上厕所", 2));
//        list.add(new Item("活动", 2));
//        return list;
//    }

    /**
     * 配置RecyclerView
     */
    private void initRecyclerView() {
//        List<Item> list = getScoreList();

        bAdapter = new BaseAdapter<Item>(bContext, new ArrayList<Item>(), false) {
            @Override
            protected void convert(ViewHolder holder, final Item data) {
                TextView tvType = holder.getView(R.id.itemReviewsReMarkAlter_tv_type);
                tvType.setText(data.type);
                ImageView ivOneStar = holder.getView(R.id.itemReviewsReMarkAlter_iv_oneStar);
                ImageView ivTwoStar = holder.getView(R.id.itemReviewsReMarkAlter_iv_twoStar);
                ImageView ivThreeStar = holder.getView(R.id.itemReviewsReMarkAlter_iv_threeStar);
                if (data.starNum >= 1) {
                    ivOneStar.setSelected(true);
                    if (data.starNum >= 2) {
                        ivTwoStar.setSelected(true);
                        if (data.starNum == 3) {
                            ivThreeStar.setSelected(true);
                        }
                    }
                }
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.librecycler_item_reviews_remark_alter;
            }

        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(bContext);
        bRecyclerView.setLayoutManager(layoutManager);
        bRecyclerView.setAdapter(bAdapter);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        bCivHead = (CircleImageView) findViewById(R.id.reviewsReMarkAlert_civ_head);
        bTvName = (TextView) findViewById(R.id.reviewsReMarkAlert_tv_name);
        bRecyclerView = (RecyclerView) findViewById(R.id.reviewsReMarkAlert_rv);
        bEtRemark = (EditText) findViewById(R.id.reviewsReMarkAlert_et_remark);
        bTvCreateTime = (TextView) findViewById(R.id.reviewsReMarkAlert_tv_createTime);
        ViewStub viewStub = (ViewStub) findViewById(R.id.reviewsReMarkAlert_viewStub);
        bTvScore = (TextView) viewStub.inflate().findViewById(R.id.includeStar_tv_score);
    }


    protected class Item {
        private String type;
        private int starNum;

        public Item(String type, int starNum) {
            this.type = type;
            this.starNum = starNum;
        }
    }
}
