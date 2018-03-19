package com.abings.baby.ui.attendance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.model.AgentModel;
import com.hellobaby.library.utils.DESUtils;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.PasswordUtils;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.BottomPickerDateDialog;
import com.hellobaby.library.widget.PopupWindowQRCodeForReplace;
import com.hellobaby.library.widget.ToastUtils;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ReplaceActivity extends BaseTitleActivity implements AttendanceMvpView {
    @Inject
    AttendancePresenter presenter;
    @BindView(R.id.replace_type1)
    ImageView replace_type1;
    @BindView(R.id.replace_type2)
    ImageView replace_type2;
    @BindView(R.id.replace_date)
    TextView replace_date;
    @BindView(R.id.replace_common)
    EditText etCommon;
    @BindView(R.id.replace_name)
    EditText etName;
    @BindView(R.id.replace_phone)
    EditText etPhone;
    PopupWindowQRCodeForReplace popupWindow;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_replace;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        replace_type1.setSelected(true);
        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnLeftClickFinish();
            }
        });
        setBtnRightDrawableRes(R.drawable.commit_icon);
        setRightBtnVisible();
    }

    @OnClick({R.id.replace_type1, R.id.replace_type2})
    public void buttonSelect(View view) {
        if (view.getId() == R.id.replace_type1) {
            replace_type1.setSelected(true);
            replace_type2.setSelected(false);
        } else if (view.getId() == R.id.replace_type2) {
            replace_type2.setSelected(true);
            replace_type1.setSelected(false);
        }
    }

    @Override
    public void showData(Object o) {

    }

    @OnClick(R.id.replace_date)
    public void onTimeClick(final View view) {
        BottomDialogUtils.getEvnetBottomDatePickerDialog(this, null, false, new BottomPickerDateDialog.BottomOnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view2, int year, int monthOfYear, int dayOfMonth, String showData) {
                replace_date.setText(showData);
                replace_date.setHint(DateUtil.upServerDateFormat(showData));
                setRightBtnVisible();
            }
        });
    }

    private void setRightBtnVisible() {
        if (!replace_date.getText().toString().equals("") && !etName.getText().toString().equals("") && !etPhone.getText().toString().equals("")) {
            bIvRight.setVisibility(View.VISIBLE);
        } else {
            bIvRight.setVisibility(View.GONE);
        }
    }

    @OnTextChanged({R.id.replace_name, R.id.replace_phone, R.id.replace_date})
    public void OnTextChange() {
        setRightBtnVisible();
    }

    @Override
    protected void btnRightOnClick(View v) {
        super.btnRightOnClick(v);
        String type = "";
        if (replace_type1.isSelected()) {
            type = "0";
        } else if (replace_type2.isSelected()) {
            type = "1";
        }
        String isPhoneRet = PasswordUtils.isPhoneNum(etPhone.getText().toString().trim());
        if (isPhoneRet != null) {
            showToast(isPhoneRet);
            return;
        }
        String babyId = ZSApp.getInstance().getBabyId();
        AgentModel agentModel = new AgentModel();
        agentModel.setBabyId(ZSApp.getInstance().getBabyId());
        agentModel.setAgentTime(replace_date.getHint().toString());
        agentModel.setCreatorId(ZSApp.getInstance().getUserId());
        agentModel.setDescription(etCommon.getText().toString());
        agentModel.setPhoneNum(etPhone.getText().toString());
        agentModel.setType(type);
        agentModel.setUserName(etName.getText().toString());
        presenter.insertAgent(agentModel);
//        PopupWindowQRCodeForReplace.getInstance().showPopup(bContext, v, encode);
//        presenter.insertLeave(replace_date.getHint().toString(),
//                tvSTime.getHint().toString(),tvCommon.getText().toString(),type);
    }

    @Override
    public void showMsg(String msg) {
//        super.showMsg(msg);
        popupWindow = PopupWindowQRCodeForReplace.getInstance();
        popupWindow.showPopup(bContext, null, msg);
    }

    public void setBtnLeftClickFinish() {
//        super.setBtnLeftClickFinish();
        if (etName.getText().toString().trim().length() > 0 || replace_date.getText().toString().trim().length() > 0 || etCommon.getText().toString().trim().length() > 0 || etPhone.getText().toString().trim().length() > 0) {
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
