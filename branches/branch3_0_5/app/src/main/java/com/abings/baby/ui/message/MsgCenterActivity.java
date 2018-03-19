package com.abings.baby.ui.message;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.message.build.MsgBuildActivity;
import com.abings.baby.ui.message.fm.InBoxFragment;
import com.abings.baby.ui.message.fm.OutBoxFragment;
import com.hellobaby.library.Const;
import com.hellobaby.library.ui.msg.BaseMsgActivity;

import org.greenrobot.eventbus.EventBus;

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
        startActivityForResult(intent, Const.NORMAL_ACTIVITY_RESULT);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EventBus.getDefault().post("reflush");
    }
}
