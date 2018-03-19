package com.hellobaby.timecard.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hellobaby.library.utils.SharedPreferencesUtils;
import com.hellobaby.timecard.KeyConst;
import com.hellobaby.timecard.R;
import com.hellobaby.timecard.ui.base.BaseActivity;
import com.lantouzi.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2017/3/13.
 * description :
 */

public class SettingActivity extends BaseActivity {

    private WheelView mWheelView;
    private ImageView ivHand;
    private ImageView ivAutoTime;
    private Button btnEnsure;

    @Override
    public void showData(Object o) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        ivHand = (ImageView) findViewById(R.id.setting_iv_hand);
        ivAutoTime = (ImageView) findViewById(R.id.setting_iv_autoTime);

        mWheelView = (WheelView) findViewById(R.id.setting_wv_diver);

        btnEnsure = (Button) findViewById(R.id.setting_btn_ensure);
        //是不是根据时间来判断
        boolean isAutoTime = (boolean) SharedPreferencesUtils.getParam(bContext, KeyConst.kIsAutoTime,true);
        isAutoTime(isAutoTime);

        final List<String> items = new ArrayList<>();
        //0点到下午23点的时间选择
        for (int i = 0; i < 24; i++) {
            items.add(String.valueOf(i)+":00");
        }
        mWheelView.setItems(items);
        mWheelView.selectIndex((Integer) SharedPreferencesUtils.getParam(bContext, KeyConst.kAutoTime,12));

        ivHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoTime(false);
            }
        });
        ivAutoTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoTime(true);
            }
        });
        btnEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTCMDialog("保存设置", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferencesUtils.setParam(bContext, KeyConst.kIsAutoTime,ivAutoTime.isSelected());
                        SharedPreferencesUtils.setParam(bContext, KeyConst.kAutoTime,mWheelView.getSelectedPosition());
                        finish();
                    }
                });
            }
        });
    }

    private void isAutoTime(boolean isAutoTime){
        ivAutoTime.setSelected(isAutoTime);
        ivHand.setSelected(!isAutoTime);
    }
}
