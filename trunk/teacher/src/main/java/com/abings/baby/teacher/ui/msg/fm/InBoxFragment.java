package com.abings.baby.teacher.ui.msg.fm;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.msg.MsgCenterMvp;
import com.abings.baby.teacher.ui.msg.MsgCenterPresenter;
import com.abings.baby.teacher.ui.msg.detail.MsgDetailActivity;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.MessageModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.msg.AllDeleteChangeStateInterface;
import com.hellobaby.library.ui.msg.fm.BaseMsgListFragment;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.SwipeItemLayout;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hellobaby.library.Const.NORMAL_ACTIVITY_RESULT;

/**
 * Created by zwj on 2016/11/29.
 * description :收件箱
 */

public class InBoxFragment extends BaseMsgListFragment implements MsgCenterMvp {
    @Inject
    MsgCenterPresenter presenter;
    List<MessageModel> list=new ArrayList<>();

    List<MessageModel> unReadList = new ArrayList<>();


    public InBoxFragment(AllDeleteChangeStateInterface anInterface) {
        super(anInterface);
    }

    @Override
    protected List<MessageModel> getData() {
        return list;
    }

    @Override
    protected List<MessageModel> getUnreadData() {
        return unReadList;
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
    public void resetAdapterItemClick() {
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
    }

    @Override
    protected void singleDeleteMsg(MessageModel messageModel) {
        presenter.deleteReceiveMessageOfTeacher(messageModel.getMessageId()+"");
    }

    @Override
    protected void delteAllMsg() {
        StringBuilder messageIds = new StringBuilder("[");
        int number = 0;
        for (int i=0;i<list.size();i++) {
            if (list.get(i).isSelected()){
                messageIds.append(list.get(i).getMessageId()+",");
                number++;
            }
        }
        if (messageIds.toString().endsWith(",")){
            messageIds.deleteCharAt(messageIds.lastIndexOf(","));
        }

        messageIds.append("]");
        LogZS.i("删除的ids："+messageIds.toString());

        if (number > 0){
            presenter.deleteAllReceiveMessageOfTeacher(messageIds.toString());
        }else {
            ToastUtils.showErrToast(getActivity(),"未选择需要删除的邮件~");
        }


    }


    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(((BaseLibActivity)getActivity()).getActivityComponent(),getActivity()).inject(this);
    }

    @Override
    public void showMsgList(JSONArray jsonArray) {
        list.clear();
        unReadList.clear();
        if(jsonArray!=null){
            bottom_check_frame.setVisibility(View.VISIBLE);
            list_is_null_tv.setText("");
            list = JSONArray.parseArray(jsonArray.toJSONString(),MessageModel.class);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getIsRead() == 0){
                    unReadList.add(list.get(i));
                }
            }
            bAdapter.setNewData(JSONArray.parseArray(jsonArray.toJSONString(),MessageModel.class));
            setUpdate(list.get(0));
            setUnreadNumber(unReadList.size());
        }else {
            bottom_check_frame.setVisibility(View.GONE);
            list_is_null_tv.setText("没有邮件");
            bAdapter.setNewData(new ArrayList());
            setUpdate(null);
            setUnreadNumber(0);
        }

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
