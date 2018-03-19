package com.abings.baby.teacher.ui.attend;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.attend.student.AttendStudentListActivity;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.hellobaby.library.data.model.BabyAttendancesModel;
import com.hellobaby.library.data.model.ClassModel;
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
    private Boolean isFinish = false;
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
        setBtnRightDrawableRes(R.drawable.select_date_black);
        adapter = new BaseAdapter<ClassModel>(bContext, list, false) {
            @Override
            protected void convert(ViewHolder holder, ClassModel data) {
                TextView textView = holder.getView(R.id.attendClassItem_tv_complete);
                if (data.getArrivalNum() != 0 || data.getLeaveNum() != 0 || data.getNonArrivalNum() != 0) {
                    textView.setText("点名已完成");
                    isFinish = true;
                } else {
                    textView.setText("点名未完成");
                    textView.setTextColor(Color.RED);
                    isFinish = false;
                }
                holder.setText(R.id.attendClassItem_tv_className, data.getClassName());
                holder.setText(R.id.attendClassItem_tv_vacation, data.getLeaveNum() + "");
                holder.setText(R.id.attendClassItem_tv_absence, data.getNonArrivalNum() + "");
                holder.setText(R.id.attendClassItem_tv_attend, data.getArrivalNum() + "");
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.recycler_item_attendclass;
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(bContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListeners<ClassModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, ClassModel data, int position) {
                Intent intent = new Intent(bContext, AttendStudentListActivity.class);
                intent.putExtra("class", data.getClassId());
                intent.putExtra("searchday", day);
                intent.putExtra("classname", data.getClassName());
                //如果是完成了的，就不继续考虑
                if (!isFinish) {
                    //未完成
                    if (null != day&&!"".equals(day)) {
                        if(day.equals(currentDate)){
                            //并且是今天的
                            intent.putExtra("isfinish", false);
                        }else{
                            intent.putExtra("isfinish", true);
                        }
                    }else{
                        intent.putExtra("isfinish", false);
                    }
                } else {
                    intent.putExtra("isfinish", true);
                }
                startActivityForResult(intent, requestAttendCode);
            }
        });
        presenter.selectAttendanceByTeacherId("");
    }

    @Override
    public void showData(Object o) {

    }


    @Override
    public void showClassData(List<ClassModel> classmodels, String currentDate) {
        adapter.setNewData(classmodels);
        this.currentDate = currentDate;
        if(!day.equals(""))
        setTitleText(day);
        else
            setTitleText(currentDate);
    }

    @Override
    public void showClass2Data(List<BabyAttendancesModel> babyAttendancesModels) {

    }

    @Override
    public void finishSuccess() {

    }

    protected void btnRightOnClick(View v) {
        BottomDialogUtils.getBottomDatePickerDialog(this, day, true, new BottomPickerDateDialog.BottomOnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth, String showDate) {
                day = DateUtil.upServerDateFormat(showDate);
                presenter.selectAttendanceByTeacherId(day);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == requestAttendCode) {
            //点名完后回来的界面
            presenter.selectAttendanceByTeacherId("");
        }
    }
}
