package com.abings.baby.ui.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.message.build.MsgBuildActivity;
import com.abings.baby.ui.message.fm.InBoxFragment;
import com.abings.baby.ui.message.fm.OutBoxFragment;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.BadgeViewModel;
import com.hellobaby.library.ui.msg.AllDeleteChangeStateInterface;
import com.hellobaby.library.ui.msg.BaseMsgActivity;
import com.hellobaby.library.ui.msg.fm.BaseMsgListFragment;

import org.greenrobot.eventbus.EventBus;

public class MsgCenterActivity extends BaseMsgActivity {


    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent,this).inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BadgeViewModel badgeViewModel=new BadgeViewModel();
        badgeViewModel.setType(3);
        EventBus.getDefault().post(badgeViewModel);
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
