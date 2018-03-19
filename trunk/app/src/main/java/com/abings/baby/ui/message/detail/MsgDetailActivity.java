package com.abings.baby.ui.message.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.abings.baby.ZSApp;
import com.abings.baby.data.injection.DaggerUtils;
import com.hellobaby.library.data.model.MessageModel;
import com.hellobaby.library.ui.msg.detail.BaseMsgDetailActivity;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.KeyWordUtils;
import com.hellobaby.library.widget.BottomDialogUtils;

import java.text.ParseException;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zwj on 2016/11/29.
 * description : 收发件中的详情界面
 * 传值说明: TODO
 * 1.收件箱(true)or发件箱(false)
 * 2.消息标题
 * 3.收发件人
 * 4.消息的时间 2016年11月29日 16:22
 * 5.消息内容
 */

public class MsgDetailActivity extends BaseMsgDetailActivity implements MsgDetailMvp{
    MessageModel messageModel;
    @Inject
    MsgDetailPresenter presenter;
    boolean isInBox;
    public static final String kIsInBox = "isInBox";

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(getActivityComponent(),this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        presenter.attachView(this);
        messageModel=(MessageModel)getIntent().getSerializableExtra("MsgModel");
        isInBox = getIntent().getBooleanExtra(kIsInBox, true);
        bTvMsgTitle.setText(messageModel.getTitle());
        String prefix = isInBox ? "发件人：" : "收件人：";
        if(null!=messageModel.getuName()&&!messageModel.getuName().equals("")) {
            setInOutBoxMsgDetailSubTitle(prefix,prefix+messageModel.getuName());
        }else
            setInOutBoxMsgDetailSubTitle(prefix,prefix+messageModel.getTeacherName());
        try {
            setTitleText(DateUtil.getFormatTimeFromTimestamp(DateUtil.longDay2time(messageModel.getSendTime()),"yyyy年MM月dd日 HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        bTvMsgContent.setText(messageModel.getContent());
        bTvMsgContent.setText(KeyWordUtils.matcherSearchPhone(messageModel.getContent(),bContext));
        setBtnRightDrawableRes(com.hellobaby.library.R.drawable.title_more_black);
    }

    @Override
    protected void btnRightOnClick(View v) {
        String[] items = {"删除", "取消"};
        BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                if (position == 1) {
                    return;
                } else if (position == 0) {
                   if(isInBox){
                       presenter.deleteReceiveMessageOfUser(messageModel.getMessageId()+"");
                   }else{
                       presenter.deleteSendMessageOfUser(messageModel.getMessageId()+"");
                   }
                    finish();
                }
            }
        });
    }
}
