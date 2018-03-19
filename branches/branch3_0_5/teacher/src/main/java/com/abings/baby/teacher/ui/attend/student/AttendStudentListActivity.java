package com.abings.baby.teacher.ui.attend.student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.attend.AttendClassActivity;
import com.abings.baby.teacher.ui.attend.AttendClassMvpView;
import com.abings.baby.teacher.ui.attend.AttendClassPresenter;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.google.gson.Gson;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.BabyAttendancesModel;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by zwj on 2016/12/1.
 * description : 班级学生考勤
 */

public class AttendStudentListActivity extends BaseTitleActivity implements AttendClassMvpView {
    @Inject
    AttendClassPresenter presenter;
    @BindView(R.id.attendClassItem_tv_className)
    TextView tvClassName;
    @BindView(R.id.attendClassItem_rv)
    RecyclerView recyclerView;
    List<BabyAttendancesModel> list;
    BaseAdapter<BabyAttendancesModel> adapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_attend_studentlist;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        tvClassName.setText(getIntent().getStringExtra("classname"));
        setBtnLeftClickFinish();
        if (!getIntent().getBooleanExtra("isfinish", false)) {
            setBtnRightDrawableRes(R.drawable.title_tick_black);
        }
        list = new ArrayList<>();
        adapter = new BaseAdapter<BabyAttendancesModel>(bContext, list, false) {
            @Override
            protected void convert(ViewHolder holder, final BabyAttendancesModel data) {
                holder.setText(R.id.attendStudentItem_tv_name, data.getBabyName());
                final View vacation = holder.getView(R.id.attendStudentItem_view_vacation);
                final View absence = holder.getView(R.id.attendStudentItem_view_absence);
                final View attend = holder.getView(R.id.attendStudentItem_view_attend);
                final ImageView header = holder.getView(R.id.attendStudentItem_civ_header);
                ImageLoader.loadHead(mContext, Const.URL_BabyHead + data.getHeadImageurl(), header);
                vacation.setBackgroundResource(R.drawable.attend_circle_default_bg);
                attend.setBackgroundResource(R.drawable.attend_circle_default_bg);
                absence.setBackgroundResource(R.drawable.attend_circle_default_bg);
                if (data.getState() == 1) {
                    attend.setBackgroundResource(R.drawable.attend_circle_attend_bg);
                } else if (data.getState() == 2) {
                    vacation.setBackgroundResource(R.drawable.attend_circle_vacation_bg);
                } else if (data.getState() == 3) {
                    absence.setBackgroundResource(R.drawable.attend_circle_absence_bg);
                }
                data.setTeacherId(Integer.valueOf(ZSApp.getInstance().getTeacherId()));
                if (!getIntent().getBooleanExtra("isfinish", false)) {
                    vacation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            data.setState(2);
                            vacation.setBackgroundResource(R.drawable.attend_circle_vacation_bg);
                            attend.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            absence.setBackgroundResource(R.drawable.attend_circle_default_bg);
                        }
                    });
                    absence.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            data.setState(3);
                            vacation.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            attend.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            absence.setBackgroundResource(R.drawable.attend_circle_absence_bg);
                        }
                    });
                    attend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            data.setState(1);
                            vacation.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            attend.setBackgroundResource(R.drawable.attend_circle_attend_bg);
                            absence.setBackgroundResource(R.drawable.attend_circle_default_bg);
                        }
                    });
                }
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.recycler_item_attendstudent;
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(bContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        presenter.selectAttendanceByClassId(getIntent().getStringExtra("class"), getIntent().getStringExtra("searchday"));
    }


    @Override
    public void showData(Object o) {

    }


    @Override
    public void showClassData(List<ClassModel> classmodels,String currentDate) {
    }

    @Override
    public void showClass2Data(List<BabyAttendancesModel> babyAttendancesModels) {
        adapter.setNewData(babyAttendancesModels);
    }

    @Override
    public void finishSuccess() {
        setResult(AttendClassActivity.requestAttendCode);
        finish();
    }

    protected void btnRightOnClick(View v) {
        if (!getIntent().getBooleanExtra("isfinish", false)) {
            presenter.insertAttendance(new Gson().toJson(list));
        }
    }
}
