package com.hellobaby.timecard.ui.setting;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hellobaby.timecard.R;
import com.hellobaby.timecard.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseSettingActivity extends BaseActivity {



    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_choose_setting;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.teacher_setting,R.id.baby_setting})
    public void settingClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.teacher_setting:
                intent = new Intent(bContext,TeacherSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.baby_setting:
                intent = new Intent(bContext,SettingActivity.class);
                startActivity(intent);
                break;
        }
        finishDefault();
    }

    @Override
    public void showData(Object o) {

    }
}
