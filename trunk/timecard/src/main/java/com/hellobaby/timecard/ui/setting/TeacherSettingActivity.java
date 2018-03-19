package com.hellobaby.timecard.ui.setting;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hellobaby.library.utils.SharedPreferencesUtils;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.timecard.KeyConst;
import com.hellobaby.timecard.R;
import com.hellobaby.timecard.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class TeacherSettingActivity extends BaseActivity {

    @BindView(R.id.edit_go_work_HH)
    EditText edit_go_work_HH;
    @BindView(R.id.edit_go_work_mm)
    EditText edit_go_work_mm;
    @BindView(R.id.edit_off_work_HH)
    EditText edit_off_work_HH;
    @BindView(R.id.edit_off_work_mm)
    EditText edit_off_work_mm;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_teacher_setting;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
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

    }

    private String[] getStringSpilt(String divierTimeTeacher, String s) {
        return divierTimeTeacher.split(s);
    }

    @OnClick(R.id.btn_sure)
    public void sureClick(View view){
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

        showTCMDialog("保存设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.setParam(bContext, KeyConst.kTeacherGoWorkTime,goWorkTime);
                SharedPreferencesUtils.setParam(bContext, KeyConst.kTeacherOffWorkTime,offWorkTime);
                showToast("上下班考勤时间设置成功！");
                finish();
            }
        });

    }


    @Override
    public void showData(Object o) {

    }
}
