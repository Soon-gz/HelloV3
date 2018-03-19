package com.abings.baby.teacher.ui.msg;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.abings.baby.teacher.ui.msg.build.MsgBuildActivity;
import com.abings.baby.teacher.ui.msg.fm.InBoxFragment;
import com.abings.baby.teacher.ui.msg.fm.OutBoxFragment;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.BadgeViewModel;
import com.hellobaby.library.ui.msg.AllDeleteChangeStateInterface;
import com.hellobaby.library.ui.msg.BaseMsgActivity;
import com.hellobaby.library.ui.msg.fm.BaseMsgListFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zwj on 2016/11/28.
 * description :
 */

public class MsgActivity<T> extends BaseMsgActivity<T> {

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
    public void showData(T t) {

    }

    @Override
    public void showError(String err) {

    }

    @Override
    protected void initDaggerInject() {
        BadgeViewModel b=new BadgeViewModel();
        b.setType(2);
        EventBus.getDefault().post(b);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EventBus.getDefault().post("reflush");
    }
}
