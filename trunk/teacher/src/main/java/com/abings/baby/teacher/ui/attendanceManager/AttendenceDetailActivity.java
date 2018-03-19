package com.abings.baby.teacher.ui.attendanceManager;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.StringUtils;

import butterknife.BindView;

public class AttendenceDetailActivity extends BaseTitleActivity {

    public static final String TEACHER_NAME = "teacherName";

    @BindView(R.id.teacher_attendance_detail_pic)
    ImageView teacher_detail_pic;
    @BindView(R.id.teacher_attendance_detail_name)
    TextView detail_name;
    @BindView(R.id.teacher_attendance_detail_rel)
    TextView detail_rel;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_attendence_detail;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setBtnLeftClickFinish();

        String name = getIntent().getStringExtra(TEACHER_NAME);
        if (StringUtils.isEmpty(name)){
            name = ZSApp.getInstance().getTeacherModel().getTeacherName();
        }
        String state = getIntent().getStringExtra("state");
        String imageUrl = getIntent().getStringExtra("headImg");

        ImageLoader.loadHeadTarget(bContext,imageUrl,teacher_detail_pic);
        detail_name.setText(name);
        detail_rel.setText(state);
    }

    @Override
    public void showData(Object o) {

    }
}
