package com.abings.baby.teacher.ui.msg;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.abings.baby.teacher.ui.msg.build.MsgBuildActivity;
import com.abings.baby.teacher.ui.msg.fm.InBoxFragment;
import com.abings.baby.teacher.ui.msg.fm.OutBoxFragment;
import com.hellobaby.library.Const;
import com.hellobaby.library.ui.msg.BaseMsgActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zwj on 2016/11/28.
 * description :
 */

public class MsgActivity<T> extends BaseMsgActivity<T> {


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
    public void showData(T t) {

    }

    @Override
    public void showError(String err) {

    }

    @Override
    protected void initDaggerInject() {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EventBus.getDefault().post("reflush");
    }
}
