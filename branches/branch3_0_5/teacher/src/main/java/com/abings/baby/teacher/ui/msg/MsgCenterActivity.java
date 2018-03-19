package com.abings.baby.teacher.ui.msg;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.msg.build.MsgBuildActivity;
import com.abings.baby.teacher.ui.msg.fm.InBoxFragment;
import com.abings.baby.teacher.ui.msg.fm.OutBoxFragment;
import com.hellobaby.library.ui.msg.BaseMsgActivity;

public class MsgCenterActivity extends BaseMsgActivity {


    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent,this).inject(this);
    }

    @Override
    protected Fragment getOutBox() {
        return new OutBoxFragment();
    }

    @Override
    protected Fragment getInBox() {
        return new InBoxFragment();
    }

    @Override
    protected void buildNewMsg() {
        Intent intent = new Intent(bContext,MsgBuildActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showData(Object t) {

    }

    @Override
    public void showError(String err) {

    }

}
