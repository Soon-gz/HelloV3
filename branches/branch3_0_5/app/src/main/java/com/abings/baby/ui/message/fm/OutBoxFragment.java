package com.abings.baby.ui.message.fm;

import android.content.Intent;
import android.widget.TextView;

import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.message.MsgCenterMvp;
import com.abings.baby.ui.message.MsgCenterPresenter;
import com.abings.baby.ui.message.detail.MsgDetailActivity;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.MessageModel;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.msg.fm.BaseMsgListFragment;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.hellobaby.library.Const.NORMAL_ACTIVITY_RESULT;

/**
 * Created by zwj on 2016/11/29.
 * description :发件箱
 */

public class OutBoxFragment extends BaseMsgListFragment implements MsgCenterMvp{
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
                Intent intent = new Intent(getContext(), MsgDetailActivity.class);
                intent.putExtra(MsgDetailActivity.kIsInBox, false);
                intent.putExtra("MsgModel",data);
                //传递其它参数过去
                startActivityForResult(intent,NORMAL_ACTIVITY_RESULT);
            }
        });
        presenter.attachView(this);
        presenter.getSendMessageListByUserId();
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
    public void selectTeacher(List<TeacherModel> TeacherModel) {

    }
    protected void recyclerViewConvert(ViewHolder holder, MessageModel data) {
        CircleImageView icon = holder.getView(com.hellobaby.library.R.id.libMsgList_civ_icon);
        TextView name = holder.getView(com.hellobaby.library.R.id.libMsgList_tv_person);//来自于谁或者发给谁
        TextView time = holder.getView(com.hellobaby.library.R.id.libMsgList_tv_time);//创建时间
        TextView title = holder.getView(com.hellobaby.library.R.id.libMsgList_tv_subject);//创建标题
        TextView content = holder.getView(com.hellobaby.library.R.id.libMsgList_tv_content);//消息内容
        if (null!=data.getuName()&&!data.getuName().equals(""))
            name.setText(data.getuName());
        else
            name.setText(data.getTeacherName());
        title.setText(data.getTitle());
        content.setText(data.getContent());
        try {
            time.setText(DateUtil.getDescriptionTimeFromTimestamp(DateUtil.longDay2time(data.getSendTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ImageLoader.loadHeadTarget(getContext(), Const.URL_TeacherHead+data.getHeadImageurl(),icon);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public  void onEvent(String message){
        presenter.getSendMessageListByUserId();
    }
}
