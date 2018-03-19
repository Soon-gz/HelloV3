package com.abings.baby.ui.attendance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.ToastUtils;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LeaveActivity extends BaseTitleActivity implements AttendanceMvpView{
    @Inject
    AttendancePresenter presenter;
    @BindView(R.id.leave_type1)
    ImageView leave_type1;
    @BindView(R.id.leave_type2)
    ImageView leave_type2;
    @BindView(R.id.leave_activity_starttime)
    TextView tvSTime;
    @BindView(R.id.leave_activity_endtime)
    TextView tvETime;
    @BindView(R.id.leave_activity_common)
    EditText tvCommon;
    Long sT;
    Long eT;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_leave;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        leave_type1.setSelected(true);
        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnLeftClickFinish();
            }
        });
        setBtnRightDrawableRes(R.drawable.commit_icon);
        setRightBtnVisible();
    }

    @OnClick({R.id.leave_type1, R.id.leave_type2})
    public void buttonSelect(View view){
        if(view.getId()==R.id.leave_type1){
            leave_type1.setSelected(true);
            leave_type2.setSelected(false);
        }else if(view.getId()==R.id.leave_type2){
            leave_type2.setSelected(true);
            leave_type1.setSelected(false);
        }
    }
    @Override
    public void showData(Object o) {

    }
    @OnClick({R.id.leave_activity_starttime, R.id.leave_activity_endtime})
    public void onTimeClick(final View view) {
//        BottomDialogUtils.getEvnetBottomDatePickerDialog(this, null, false, new BottomPickerDateDialog.BottomOnDateChangedListener() {
//
//            @Override
//            public void onDateChanged(DatePicker view2, int year, int monthOfYear, int dayOfMonth, String showData) {
//                switch (view.getId()) {
//                    case R.id.leave_activity_starttime:
//                        tvSTime.setText("起始时间 " + showData);
//                        tvSTime.setHint(showData);
//                        setRightBtnVisible();
//                        break;
//                    case R.id.leave_activity_endtime:
//                        tvETime.setText("结束时间 " + showData);
//                        tvETime.setHint(showData);
//                        setRightBtnVisible();
//                        break;
//                }
//            }
//        });
        switch (view.getId()) {
                    case R.id.leave_activity_starttime:
                        BottomDialogUtils.getBottomTimePicker(this, new OnDateSetListener() {
                            @Override
                            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                                tvSTime.setText(DateUtil.getFormatTimeFromTimestamp(millseconds,"yyyy-MM-dd HH:mm"));
                                tvSTime.setHint(DateUtil.getFormatTimeFromTimestamp(millseconds,"yyyy-MM-dd HH:mm:ss"));
                                sT=millseconds;
                            }
                        }).show(getSupportFragmentManager(),"all");
                        setRightBtnVisible();
                        break;
                    case R.id.leave_activity_endtime:
                        BottomDialogUtils.getBottomTimePicker(this, new OnDateSetListener() {
                            @Override
                            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                                tvETime.setText(DateUtil.getFormatTimeFromTimestamp(millseconds,"yyyy-MM-dd HH:mm"));
                                tvETime.setHint(DateUtil.getFormatTimeFromTimestamp(millseconds,"yyyy-MM-dd HH:mm:ss"));
                                eT=millseconds;
                            }
                        }).show(getSupportFragmentManager(),"all");
                        setRightBtnVisible();
                        break;
        }

    }

    private void setRightBtnVisible() {
        if(!tvSTime.getText().toString().equals("")&&!tvETime.getText().toString().equals("")){
            bIvRight.setVisibility(View.VISIBLE);
        }else {
            bIvRight.setVisibility(View.GONE);
        }
    }
@OnTextChanged(R.id.leave_activity_common)
public void OnTextChange(){
    setRightBtnVisible();
}
    @Override
    protected void btnRightOnClick(View v) {
        super.btnRightOnClick(v);
        if(sT>eT){
            ToastUtils.showErrToast(this,"结束时间必须大于起始时间");
            return ;
        }
        String type="";
        if(leave_type1.isSelected()){
            type="0";
        }else if(leave_type2.isSelected()){
            type="1";
        }
        presenter.insertLeave(tvSTime.getHint().toString(),
                tvETime.getHint().toString(),tvCommon.getText().toString(),type);
    }

    @Override
    public void showMsg(String msg) {
        super.showMsg(msg);
//        EventBus.getDefault().post(new AttendanceNormalBack(msg,AttendanceNormalBack.SUCCEED));
        finish();
    }

    @Override
    public void setBtnLeftClickFinish() {
//        super.setBtnLeftClickFinish();
            if (tvETime.getText().toString().trim().length() > 0 ||tvSTime.getText().toString().trim().length() > 0||tvCommon.getText().toString().trim().length() > 0) {
                BottomDialogUtils.getBottomExitEditDialog(this);
            } else {
                finish();
            }
    }
    @Override
    public void onBackPressed() {
        setBtnLeftClickFinish();
    }
    @Override
    public void showJsonObject(JSONObject jsonObject) {

    }

    @Override
    public void showJsonArray(JSONArray jsonArr) {

    }
}
