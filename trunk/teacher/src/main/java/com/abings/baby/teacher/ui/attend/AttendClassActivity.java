package com.abings.baby.teacher.ui.attend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.attend.student.AttendStudentListActivity;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.hellobaby.library.data.model.BabyAttendancesModel;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.model.TeacherStudentAttendModel;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.BottomPickerDateDialog;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by zwj on 2016/12/1.
 * description : 出席，班级
 */

public class AttendClassActivity extends BaseTitleActivity implements AttendClassMvpView {
    @Inject
    AttendClassPresenter presenter;
    @BindView(R.id.attendClass_rv)
    RecyclerView recyclerView;
    private List<ClassModel> list = new ArrayList<>();
    private BaseAdapter<ClassModel> adapter;
    private String day = "";
    private String currentDate = null;
    /**
     * false的时候，下一级界面展示右上角
     * true的时候，下一级界面不展示右上角
     */
    private Boolean isFinish1 = false;
    private Boolean isFinish2 = false;
    public static final int requestAttendCode = 99;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_attend_class;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        setBtnLeftClickFinish();
        LinearLayoutManager layoutManager = new LinearLayoutManager(bContext);
        setBtnRightDrawableRes(R.drawable.select_date_black);
        adapter = new BaseAdapter<ClassModel>(bContext, list, false) {
            @Override
            protected void convert(ViewHolder holder, ClassModel data) {
                holder.setText(R.id.attendClassItem_tv_className, data.getClassName());
                if (data.getMorningArrivalNum() != 0 || data.getMorningNonArrivalNum() != 0||data.getMorningLeaveNum()!=0) {
                    holder.setText(R.id.tv_shang,"上午\n已完成").setBgRes(R.id.tv_shang,R.drawable.attend_circle_class_complete);
//                    isFinish1 = true;
                } else {
                    holder.setText(R.id.tv_shang,"上午\n未完成").setBgRes(R.id.tv_shang,R.drawable.attend_circle_absence_bg);
//                    isFinish1 = false;
                }
                if (data.getAfternoonArrivalNum() != 0 || data.getAfternoonNonArrivalNum() != 0||data.getAfternoonLeaveNum()!=0) {
                    holder.setText(R.id.tv_xia,"下午\n已完成").setBgRes(R.id.tv_xia,R.drawable.attend_circle_class_complete);
//                    isFinish2 = true;
                } else {
                    holder.setText(R.id.tv_xia,"下午\n未完成").setBgRes(R.id.tv_xia,R.drawable.attend_circle_absence_bg);
//                    isFinish2 = false;
                }
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.recycler_item_attendclass;
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new OnItemClickListeners<ClassModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, ClassModel data, int position) {
                final Intent intent = new Intent(bContext, AttendStudentListActivity.class);
                intent.putExtra("class", data.getClassId());
                intent.putExtra("searchday", day);
                intent.putExtra("classname", data.getClassName());
                if (data.getMorningArrivalNum() != 0 || data.getMorningNonArrivalNum() != 0||data.getMorningLeaveNum()!=0) {
                    isFinish1 = true;
                }else {
                    isFinish1 = false;
                }
                if (data.getAfternoonArrivalNum() != 0 || data.getAfternoonNonArrivalNum() != 0||data.getAfternoonLeaveNum()!=0) {
                    isFinish2 = true;
                }else {
                    isFinish2 = false;
                }
                if(!day.equals(currentDate)&&!day.equals("")) {
                    intent.putExtra("isFinish1", true);
                    intent.putExtra("isFinish2", true);
                }else{
                    intent.putExtra("searchday", currentDate);
                    intent.putExtra("isFinish1", isFinish1);
                    intent.putExtra("isFinish2", isFinish2);
                }
                viewHolder.getView(R.id.tv_xia).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("isXia",true);
                        startActivityForResult(intent, requestAttendCode);
                    }
                });
                startActivityForResult(intent, requestAttendCode);
            }
        });
//        presenter.selectAttendanceByTeacherId("");
        presenter.selectAttendanceByTeacherIdWithType("");
    }

    @Override
    public void showData(Object o) {

    }


    @Override
    public void showClassData(List<ClassModel> classmodels, String currentDate) {
        adapter.setNewData(classmodels);
        this.currentDate = currentDate;
        if(!day.equals("")&&!day.equals(currentDate))
        setTitleText(day);
        else
            setTitleText("今天");
    }

    @Override
    public void showClass2Data(List<BabyAttendancesModel> babyAttendancesModels) {

    }

    @Override
    public void showClass3Data(List<TeacherStudentAttendModel> babyAttendancesModels) {

    }

    @Override
    public void showClass4Data(List<BabyAttendancesModel> babyAttendancesModels) {

    }

    @Override
    public void finishSuccess() {

    }

    protected void btnRightOnClick(View v) {
        BottomDialogUtils.getBottomDatePickerDialog(this, day, true, new BottomPickerDateDialog.BottomOnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth, String showDate) {
                day = DateUtil.upServerDateFormat(showDate);
//                presenter.selectAttendanceByTeacherId(day);
                presenter.selectAttendanceByTeacherIdWithType(day);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == requestAttendCode) {
            //点名完后回来的界面
//            presenter.selectAttendanceByTeacherId("");
            presenter.selectAttendanceByTeacherIdWithType("");
        }
    }
}
