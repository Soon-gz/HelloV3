package com.hellobaby.library.ui.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;

import butterknife.BindView;

/**
 * 活动详情界面
 */
public abstract class BaseEventDetailActivity extends BaseLibTitleActivity {
    public TextView activityed_tv_title;
    public TextView activityed_tv_time;
    public TextView activityed_tv_address;
    public TextView activityed_tv_fee;
    public TextView activityed_tv_people;
    public  TextView activityed_tv_deadline;
    public TextView activityed_tv_conp;
    public TextView activityed_tv_static;
    public TextView activityed_tv_detail;
    public Button eventdetail_bt_cancel;
//    public Button eventdetail_bt_join;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_eventdetail;
    }


    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        activityed_tv_title=(TextView) findViewById(R.id.activityed_tv_title);
        activityed_tv_time=(TextView) findViewById(R.id.activityed_tv_time);
        activityed_tv_address=(TextView) findViewById(R.id.activityed_tv_address);
        activityed_tv_fee=(TextView) findViewById(R.id.activityed_tv_fee);
        activityed_tv_people=(TextView) findViewById(R.id.activityed_tv_people);
        activityed_tv_deadline=(TextView) findViewById(R.id.activityed_tv_deadline);
        activityed_tv_conp=(TextView) findViewById(R.id.activityed_tv_conp);
        activityed_tv_detail=(TextView) findViewById(R.id.activityed_tv_detail);
        activityed_tv_static=(TextView) findViewById(R.id.activityed_tv_static);
        eventdetail_bt_cancel=(Button) findViewById(R.id.eventdetail_bt_cancel);
//        eventdetail_bt_join=(Button) findViewById(R.id.eventdetail_bt_join);
        setBtnLeftClickFinish();
        initData();
    }

    public void initData() {
    }

}
