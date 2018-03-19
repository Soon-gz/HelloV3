package com.abings.baby.ui.measuredata;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.MeasureModel;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.BottomPickerDateDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AddMeasureDataActivity extends BaseTitleActivity implements LineCharMvpView {
    @Inject
    LineCharPresenter presenter;
    String isHeight;
    @BindView(R.id.addMeasure_tv_birth)
    EditText birth;
    @BindView(R.id.addMeasure_tv_now)
    EditText now;
    @BindView(R.id.addMeasure_tv_measuredate)
    TextView measuredate;
    boolean isNew;
    boolean isEdit;
    MeasureModel measureModel;

    //身高体重都是height
    String userId, babyId, nowHeight, inputDate, birthDate, birthHeight;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_add_measure_data;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        setBtnLeftClickFinish();
        Intent intent = getIntent();
        isEdit = intent.hasExtra("MeasureModel");
        measureModel = (MeasureModel) intent.getSerializableExtra("MeasureModel");
        isHeight = intent.getStringExtra("isHeight");
        isNew = intent.hasExtra("isNew");
        if (isNew) {
            birth.setVisibility(View.VISIBLE);
        }
        if (isHeight.equals("1")) {
            setTitleText("身高");
        } else {
            setTitleText("体重");
            birth.setHint("出生体重 (kg)");
            now.setHint("现在体重 (kg)");
        }
        if (isEdit)
            measuredate.setVisibility(View.GONE);
        measuredate.setText(DateUtil.getFormatTimeFromTimestamp(System.currentTimeMillis(),"yyyy年MM月dd日"));
        userId = ZSApp.getInstance().getUserId();
        babyId = ZSApp.getInstance().getBabyId();
        birthDate = ZSApp.getInstance().getBirthday();
        setBtnRightDrawableRes(R.drawable.commit_icon);
        bIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowHeight = now.getText().toString();
                inputDate = DateUtil.upServerDateFormat(measuredate.getText().toString());
                birthHeight = birth.getText().toString();
                if (isNew) {
                    if (isHeight.equals("1")) {
                        presenter.initHeight(userId, babyId, birthHeight, nowHeight, birthDate, inputDate);
                    } else {
                        presenter.initWeight(userId, babyId, birthHeight, nowHeight, birthDate, inputDate);
                    }
                } else {
                    if (isHeight.equals("1")) {
                        if (!isEdit)
                            presenter.insertHeight(userId, babyId, nowHeight, inputDate);
                        else
                            presenter.updateHeightById(measureModel.getHeightId() + "", nowHeight);
                    } else {
                        if (!isEdit)
                            presenter.insertWeight(userId, babyId, nowHeight, inputDate);
                        else
                            presenter.updateWeightById(measureModel.getWeightId() + "", nowHeight);
                    }
                }
                Intent intent1 = new Intent();
                intent1.putExtra("isOk", "OK");
                setResult(RESULT_OK, intent1);
                finish();
            }
        });
    }

    @OnClick(R.id.addMeasure_tv_measuredate)
    public void birthdayOnClick() {
        String birthday = measuredate.getText().toString().trim();
        BottomDialogUtils.getHeightBottomDatePickerDialog(this, birthday, true, ZSApp.getInstance().getBirthday(), new BottomPickerDateDialog.BottomOnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth, String showDate) {
                measuredate.setText(showDate);
            }
        });
    }

    @Override
    public void setLastData(JSONObject jsonObject) {

    }

    @Override
    public void selectHisHeight(List<MeasureModel> models) {

    }

    @Override
    public void showData(Object o) {

    }
}
