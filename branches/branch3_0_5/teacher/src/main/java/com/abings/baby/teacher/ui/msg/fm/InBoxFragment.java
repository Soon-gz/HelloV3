package com.abings.baby.teacher.ui.msg.fm;

import android.content.Intent;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.msg.MsgCenterMvp;
import com.abings.baby.teacher.ui.msg.MsgCenterPresenter;
import com.abings.baby.teacher.ui.msg.detail.MsgDetailActivity;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.MessageModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.msg.fm.BaseMsgListFragment;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.hellobaby.library.Const.NORMAL_ACTIVITY_RESULT;

/**
 * Created by zwj on 2016/11/29.
 * description :收件箱
 */

public class InBoxFragment extends BaseMsgListFragment implements MsgCenterMvp {
    @Inject
    MsgCenterPresenter presenter;
    List<MessageModel> list=new ArrayList<>();
    @Override
    protected List<MessageModel> getData() {
        return list;
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        EventBus.getDefault().register(this);
        bAdapter.setOnItemClickListener(new OnItemClickListeners<MessageModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, MessageModel data, int position) {
                if(data.getIsRead()!=1)
                presenter.setMessageReadWithTeacherId(data.getMessageId()+"");
                Intent intent = new Intent(getContext(), MsgDetailActivity.class);
                intent.putExtra("MsgModel",data);
                intent.putExtra(MsgDetailActivity.kIsInBox, true);
                //传递其它参数过去
                startActivityForResult(intent,NORMAL_ACTIVITY_RESULT);
            }
        });
        presenter.attachView(this);
        presenter.getReceiveMessageListByTeacherId();
    }
    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(((BaseLibActivity)getActivity()).getActivityComponent(),getActivity()).inject(this);
    }
    @Override
    public void showMsgList(JSONArray jsonArray) {
        bAdapter.setNewData(JSONArray.parseArray(jsonArray.toJSONString(),MessageModel.class));
    }

    @Override
    public void showMsgSendSuccess() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.getReceiveMessageListByTeacherId();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public  void onEvent(String message){
        presenter.getReceiveMessageListByTeacherId();
    }
}
