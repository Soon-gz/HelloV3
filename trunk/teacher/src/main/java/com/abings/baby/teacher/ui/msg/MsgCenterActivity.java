package com.abings.baby.teacher.ui.msg;

import android.content.Intent;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.msg.build.MsgBuildActivity;
import com.abings.baby.teacher.ui.msg.fm.InBoxFragment;
import com.abings.baby.teacher.ui.msg.fm.OutBoxFragment;
import com.hellobaby.library.ui.msg.AllDeleteChangeStateInterface;
import com.hellobaby.library.ui.msg.BaseMsgActivity;
import com.hellobaby.library.ui.msg.fm.BaseMsgListFragment;

public class MsgCenterActivity extends BaseMsgActivity {


    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent,this).inject(this);
    }


    @Override
    protected BaseMsgListFragment getOutBox(AllDeleteChangeStateInterface anInterface) {
        return new OutBoxFragment(anInterface);
    }

    @Override
    protected BaseMsgListFragment getInBox(AllDeleteChangeStateInterface anInterface) {
        return new InBoxFragment(anInterface);
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
