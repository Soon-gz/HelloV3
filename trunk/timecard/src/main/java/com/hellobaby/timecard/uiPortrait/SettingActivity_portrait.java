package com.hellobaby.timecard.uiPortrait;

import android.content.Context;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hellobaby.library.utils.SharedPreferencesUtils;
import com.hellobaby.library.utils.StringUtils;
import com.hellobaby.timecard.KeyConst;
import com.hellobaby.timecard.R;
import com.hellobaby.timecard.ZSApp;
import com.hellobaby.timecard.ui.base.BaseActivity;
import com.lantouzi.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity_portrait extends BaseActivity {

    @BindView(R.id.setting_wv_diver)
    WheelView mWheelView;
    @BindView(R.id.setting_iv_hand)
    ImageView ivHand;
    @BindView(R.id.setting_iv_autoTime)
    ImageView ivAutoTime;
    @BindView(R.id.edit_go_work_HH)
    EditText edit_go_work_HH;
    @BindView(R.id.edit_go_work_mm)
    EditText edit_go_work_mm;
    @BindView(R.id.edit_off_work_HH)
    EditText edit_off_work_HH;
    @BindView(R.id.edit_off_work_mm)
    EditText edit_off_work_mm;
    @BindView(R.id.uuid_edit)
    EditText uuid_edit;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting_portrait;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        //是不是根据时间来判断
        boolean isAutoTime = (boolean) SharedPreferencesUtils.getParam(bContext, KeyConst.kIsAutoTime,true);
        isAutoTime(isAutoTime);

        final List<String> items = new ArrayList<>();
        //0点到下午23点的时间选择
        for (int i = 0; i < 24; i++) {
            items.add(String.valueOf(i)+":00");
        }
        mWheelView.setItems(items);
        mWheelView.selectIndex((Integer) SharedPreferencesUtils.getParam(bContext, KeyConst.kAutoTime,12));

        ivHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoTime(false);
            }
        });
        ivAutoTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoTime(true);
            }
        });

        //上班时间
        String goWorkTime = (String) SharedPreferencesUtils.getParam(bContext, KeyConst.kTeacherGoWorkTime, "9:00");
        String[] goWorkTimeTeacherSpilt = getStringSpilt(goWorkTime,":");
        edit_go_work_HH.setText(goWorkTimeTeacherSpilt[0]);
        edit_go_work_mm.setText(goWorkTimeTeacherSpilt[1]);

        //下班时间
        String offWorkTime = (String) SharedPreferencesUtils.getParam(bContext, KeyConst.kTeacherOffWorkTime, "17:00");
        String[] offWorkTimeTeacherSpilt = getStringSpilt(offWorkTime,":");
        edit_off_work_HH.setText(offWorkTimeTeacherSpilt[0]);
        edit_off_work_mm.setText(offWorkTimeTeacherSpilt[1]);

        //机器码初始化
        String uuid = Settings.System.getString(getContentResolver(),KeyConst.kUUID);
//        String uuid = (String) SharedPreferencesUtils.getParam(bContext,KeyConst.kUUID,"");
        uuid_edit.setText(uuid);
    }

    private String[] getStringSpilt(String divierTimeTeacher, String s) {
        return divierTimeTeacher.split(s);
    }


    @OnClick({R.id.btn_sure,R.id.main_iv_setting})
    public void sureClick(View view){
        switch (view.getId()){
            case R.id.btn_sure:
                String uuid = Settings.System.getString(getContentResolver(),KeyConst.kUUID);
//                String uuid = (String) SharedPreferencesUtils.getParam(bContext,KeyConst.kUUID,"");
                final String uuid_ed = uuid_edit.getText().toString().trim();
                String ts = "保存设置";
                if (!uuid.equals(uuid_ed)){
                    ts = "您修改了机器码，保存设置?\n需要重启才能生效！";
                }

                String goWorkTimeHH = edit_go_work_HH.getText().toString().trim();
                String goWorkTimemm = edit_go_work_mm.getText().toString().trim();


                String offWorkTimeHH = edit_off_work_HH.getText().toString().trim();
                String offWorkTimemm = edit_off_work_mm.getText().toString().trim();

                if (goWorkTimeHH.equals("")){
                    showError("上班时间小时不能为空！");
                    return;
                }
                if (goWorkTimemm.equals("")){
                    showError("上班时间分钟不能为空，可设置为 00！");
                    return;
                }
                if (offWorkTimeHH.equals("")){
                    showError("下班时间小时不能为空！");
                    return;
                }
                if (offWorkTimemm.equals("")){
                    showError("下班时间分钟数不能为空，可设置为 00！");
                    return;
                }

                int goWorkTimeHHInt = Integer.parseInt(goWorkTimeHH);
                int goWorkTimemmInt = Integer.parseInt(goWorkTimemm);
                int offWorkTimeHHInt = Integer.parseInt(offWorkTimeHH);
                int offWorkTimemmInt = Integer.parseInt(offWorkTimemm);

                //下班时间不能小于上班时间
                if (offWorkTimeHHInt < goWorkTimeHHInt){
                    showError("下班时间不能小于上班时间！");
                    return;
                }
                //分钟数不能大于60
                if (goWorkTimemmInt > 60 || offWorkTimemmInt > 60){
                    showError("设置时间分钟数不能大于60！");
                    return;
                }
                //小时数不能大于24
                if (offWorkTimeHHInt > 24 || goWorkTimeHHInt > 24){
                    showError("设置时间小时数不能大于24！");
                    return;
                }
                String goWorkTimemmIntStr = goWorkTimemmInt+"";
                String offWorkTimemmIntStr = offWorkTimemmInt+"";
                if (goWorkTimemmInt == 0){
                    goWorkTimemmIntStr = "00";
                }
                if (offWorkTimemmInt == 0){
                    offWorkTimemmIntStr = "00";
                }

                if (offWorkTimemmInt == 0){

                }

                final String goWorkTime = goWorkTimeHHInt+":"+goWorkTimemmIntStr;
                final String offWorkTime = offWorkTimeHHInt+":"+offWorkTimemmIntStr;

                showTCMDialog(ts, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferencesUtils.setParam(bContext, KeyConst.kTeacherGoWorkTime,goWorkTime);
                        SharedPreferencesUtils.setParam(bContext, KeyConst.kTeacherOffWorkTime,offWorkTime);
                        SharedPreferencesUtils.setParam(bContext, KeyConst.kIsAutoTime,ivAutoTime.isSelected());
                        SharedPreferencesUtils.setParam(bContext, KeyConst.kAutoTime,mWheelView.getSelectedPosition());
//                        SharedPreferencesUtils.setParam(bContext,KeyConst.kUUID,uuid_ed);
                        Settings.System.putString(getContentResolver(),KeyConst.kUUID,uuid_ed);
                        ZSApp.getInstance().setChangeUUID(true);
                        showToast("考勤设置成功！");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(uuid_edit,InputMethodManager.SHOW_FORCED);
                        imm.hideSoftInputFromWindow(uuid_edit.getWindowToken(), 0);
                        finish();
                    }
                });
                break;
            case R.id.main_iv_setting:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(uuid_edit,InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(uuid_edit.getWindowToken(), 0);
                finish();
                break;
        }

    }

    private void isAutoTime(boolean isAutoTime){
        ivAutoTime.setSelected(isAutoTime);
        ivHand.setSelected(!isAutoTime);
    }

    @Override
    public void showData(Object o) {

    }
}
