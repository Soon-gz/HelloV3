package com.abings.baby.teacher.ui.msg.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hellobaby.library.data.model.MessageModel;
import com.hellobaby.library.ui.msg.detail.BaseMsgDetailActivity;
import com.hellobaby.library.utils.DateUtil;

import java.text.ParseException;

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

public class MsgDetailActivity extends BaseMsgDetailActivity {
    MessageModel messageModel;
    public static final String kIsInBox = "isInBox";

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        messageModel=(MessageModel)getIntent().getSerializableExtra("MsgModel");
        boolean isInBox = getIntent().getBooleanExtra(kIsInBox, true);
        bTvMsgTitle.setText(messageModel.getTitle());
        String prefix = isInBox ? "发件人：" : "收件人：";
        if(null!=messageModel.getuName()&&!messageModel.getuName().equals("")) {
            setInOutBoxMsgDetailSubTitle(prefix,prefix+messageModel.getuName());
        }else
            setInOutBoxMsgDetailSubTitle(prefix,prefix+messageModel.getClassName());
        try {
            setTitleText(DateUtil.getFormatTimeFromTimestamp(DateUtil.longDay2time(messageModel.getSendTime()),"yyyy年MM月dd日 HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bTvMsgContent.setText(messageModel.getContent());
    }
}
