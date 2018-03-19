package com.abings.baby.ui.attendance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.TimeCardModel;
import com.hellobaby.library.utils.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;

public class AttendanceDetailActivity extends BaseTitleActivity {
    TimeCardModel data = new TimeCardModel();
    TextView name;
    TextView rel;
    ImageView head;
    ImageView pic;
    @BindView(R.id.replace_call_ll)
    RelativeLayout call;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_attendance_detail;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setBtnLeftClickFinish();
        data = (TimeCardModel) getIntent().getSerializableExtra("TimeCardModel");
        name = (TextView) findViewById(R.id.attendance_detail_name);
        rel = (TextView) findViewById(R.id.attendance_detail_rel);
        head = (ImageView) findViewById(R.id.attendance_detail_head);
        pic = (ImageView) findViewById(R.id.attendance_detail_pic);
        showModelData();
    }

    private void showModelData() {
        setTitleText(data.getTimeStr() != null ? data.getTimeStr().substring(0,data.getTimeStr().lastIndexOf(":")) + " " + data.getEventType() : "");
        if (data.getPersonType() == TimeCardModel.TC_TPYE_USER) {
            name.setText(data.getName() != null ? data.getName() : "");
            rel.setText(data.getRelation() != null ? data.getRelation() : "");
            ImageLoader.loadHeadTarget(this, Const.URL_userHead + data.getHeadImageUrl(), head);
            data.setPhoneNum(ZSApp.getInstance().getLoginUser().getPhoneNum());
            call.setEnabled(false);
        } else if (data.getPersonType() == TimeCardModel.TC_TPYE_AGENT) {
            name.setText(data.getName() != null ? data.getName() : "");
            rel.setText(data.getPhoneNum() != null ? data.getPhoneNum() : "");
            head.setImageResource(R.drawable.head_dai);
//            ImageLoader.load(this,R.drawable.head_dai,head);
        } else if (data.getPersonType() == TimeCardModel.TC_TPYE_CARD) {
            name.setText(data.getName() != null ? data.getName() : "");
            rel.setText(data.getRelation() != null ? data.getRelation() : "");
            ImageLoader.loadHeadTarget(this, Const.URL_pickHead + data.getHeadImageUrl(), head);
        }
        ImageLoader.loadHeadTarget(this, Const.URL_timeCardImg + data.getTimeCardImgUrl(), pic);
    }

    @Override
    public void showData(Object o) {

    }

    @OnClick(R.id.replace_call_ll)
    public void OnClick() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri uri = Uri.parse("tel:" + data.getPhoneNum());
        intent.setData(uri);
        this.startActivity(intent);
    }
}