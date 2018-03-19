package com.hellobaby.library.ui.alert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.SystemMessageModel;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.utils.DateUtil;

import java.text.ParseException;

/**
 * 通知详情界面
 */
public class AlertDetailActivity extends BaseLibTitleActivity {
    SystemMessageModel data;
    TextView title;
    TextView time;
    TextView content;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_alertdetail;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setBtnLeftClickFinish();
        data=(SystemMessageModel) getIntent().getSerializableExtra("SystemMessageModel");
        title=(TextView)findViewById(R.id.activityad_tv_title) ;
        time=(TextView)findViewById(R.id.activityad_tv_time) ;
        content=(TextView)findViewById(R.id.activityad_tv_content) ;
        title.setText(data.getTitle());
        try {
            time.setText("来自于"+data.getSenderName()+" "+ DateUtil.getFormatTimeFromTimestamp(DateUtil.longDay2time(data.getSendTime()),"yyyy年MM月dd日 hh:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        content.setText(data.getContent());
    }

    @Override
    protected void btnRightOnClick(View v) {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showError(String err) {

    }
}
