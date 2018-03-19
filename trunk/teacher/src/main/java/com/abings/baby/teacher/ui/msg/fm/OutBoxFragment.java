package com.abings.baby.teacher.ui.msg.fm;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.msg.MsgCenterMvp;
import com.abings.baby.teacher.ui.msg.MsgCenterPresenter;
import com.abings.baby.teacher.ui.msg.detail.MsgDetailActivity;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.MessageModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.msg.AllDeleteChangeStateInterface;
import com.hellobaby.library.ui.msg.fm.BaseMsgListFragment;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hellobaby.library.Const.NORMAL_ACTIVITY_RESULT;

/**
 * Created by zwj on 2016/11/29.
 * description :发件箱
 */

public class OutBoxFragment extends BaseMsgListFragment implements MsgCenterMvp {
    @Inject
    MsgCenterPresenter presenter;
    List<MessageModel> list=new ArrayList<>();

    @BindView(R.id.unread_all)
    TextView unread_all;
    @BindView(R.id.unread_number)
    TextView unread_number;

    HashMap<String,TextView> imageViewHashMap = new HashMap<>();

    public OutBoxFragment(AllDeleteChangeStateInterface anInterface) {
        super(anInterface);
    }


    @Override
    protected List<MessageModel> getData() {
        return list;
    }

    @Override
    protected List<MessageModel> getUnreadData() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        EventBus.getDefault().register(this);
        bAdapter.setOnItemClickListener(new OnItemClickListeners<MessageModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, MessageModel data, int position) {
                Intent intent = new Intent(getContext(), MsgDetailActivity.class);
                intent.putExtra(MsgDetailActivity.kIsInBox, false);
                intent.putExtra("MsgModel",data);
                //传递其它参数过去
                startActivityForResult(intent,NORMAL_ACTIVITY_RESULT);
            }
        });
        presenter.attachView(this);
        presenter.getSendMessageListByTeacherId();

        unread_all.setVisibility(View.GONE);
        unread_number.setVisibility(View.GONE);

    }

    @Override
    public void resetAdapterItemClick() {
        bAdapter.setOnItemClickListener(new OnItemClickListeners<MessageModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, MessageModel data, int position) {
                Intent intent = new Intent(getContext(), MsgDetailActivity.class);
                intent.putExtra(MsgDetailActivity.kIsInBox, false);
                intent.putExtra("MsgModel",data);
                //传递其它参数过去
                startActivityForResult(intent,NORMAL_ACTIVITY_RESULT);
            }
        });

        Iterator<TextView> textViewIterator = getTextveiws().values().iterator();
        while (textViewIterator.hasNext()){
            textViewIterator.next().setClickable(true);
        }

    }

    @Override
    public HashMap<String, TextView> getTextveiws() {
        return imageViewHashMap;
    }

    @Override
    protected void singleDeleteMsg(MessageModel messageModel) {
        presenter.deleteSendMessageOfTeacher(messageModel.getMessageId()+"");
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
            presenter.deleteAllSendMessageOfTeacher(messageIds.toString());
        }else {
            ToastUtils.showErrToast(getActivity(),"未选择需要删除的邮件~");
        }

    }


    @Override
    protected void recyclerViewConvert(ViewHolder holder, final MessageModel data) {
        CircleImageView circleIcon = holder.getView(R.id.libMsgList_civ_icon);
        circleIcon.setVisibility(View.GONE);
        TextView tvIcon = holder.getView(R.id.libMsgList_tv_icon);
        tvIcon.setVisibility(View.VISIBLE);
        tvIcon.setText(data.getReadNum()+"/"+data.getTotalNum());
        if (!imageViewHashMap.containsKey(data.getMessageId()+"")){
            imageViewHashMap.put(""+data.getMessageId(),tvIcon);
        }
        if(data.getReadNum()!=data.getTotalNum()) {
            tvIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), UnReadActivity.class);
                    intent.putExtra("messageId", data.getMessageId() + "");
                    startActivityForResult(intent, NORMAL_ACTIVITY_RESULT);
                }
            });
        }else {
//            showMsg("已完成");
        }
        TextView name = holder.getView(com.hellobaby.library.R.id.libMsgList_tv_person);//来自于谁或者发给谁
        TextView time = holder.getView(com.hellobaby.library.R.id.libMsgList_tv_time);//创建时间
        TextView title = holder.getView(com.hellobaby.library.R.id.libMsgList_tv_subject);//创建标题
        TextView content = holder.getView(com.hellobaby.library.R.id.libMsgList_tv_content);//消息内容
        name.setText(data.getClassName());
        title.setText(data.getTitle());
        content.setText(data.getContent());
        try {
            time.setText(DateUtil.getDescriptionTimeFromTimestamp(DateUtil.longDay2time(data.getSendTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(((BaseLibActivity)getActivity()).getActivityComponent(),getActivity()).inject(this);
    }
    @Override
    public void showMsgList(JSONArray jsonArray) {
        list.clear();
        if(jsonArray!=null) {
            bottom_check_frame.setVisibility(View.VISIBLE);
            list_is_null_tv.setText("");
            list = JSONArray.parseArray(jsonArray.toJSONString(), MessageModel.class);
            bAdapter.setNewData(JSONArray.parseArray(jsonArray.toJSONString(), MessageModel.class));
        }else {
            bottom_check_frame.setVisibility(View.GONE);
            list_is_null_tv.setText("没有邮件");
            bAdapter.setNewData(new ArrayList());
        }
        setOutUpDate();
    }

    @Override
    public void showMsgSendSuccess() {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.getSendMessageListByTeacherId();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public  void onEvent(String message){
        presenter.getSendMessageListByTeacherId();
    }


}
