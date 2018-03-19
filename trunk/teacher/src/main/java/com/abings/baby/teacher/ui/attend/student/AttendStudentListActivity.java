package com.abings.baby.teacher.ui.attend.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.hellobaby.library.data.model.TeacherStudentAttendModel;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.StringUtils;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zwj on 2016/12/1.
 * description : 班级学生考勤
 */

public class AttendStudentListActivity extends BaseTitleActivity implements AttendClassMvpView {
    @Inject
    AttendClassPresenter presenter;
    //    @BindView(R.id.attendClassItem_tv_className)
//    TextView tvClassName;
    @BindView(R.id.attendClassItem_rv)
    RecyclerView recyclerView;
    @BindView(R.id.attendClass_rv_xia)
    RecyclerView recyclerView_xia;
    @BindView(R.id.icon_ll)
    LinearLayout icon_ll;
    @BindView(R.id.text_ll)
    LinearLayout text_ll;
    @BindView(R.id.tag_shang)
    TextView tag_shang;
    @BindView(R.id.tag_xia)
    TextView tag_xia;
    @BindView(R.id.tag_jiesong)
    TextView tag_jiesong;
    @BindView(R.id.attendClass_rv_jiesong)
    RecyclerView recyclerView_jiesong;
    //下面条子
    @BindView(R.id.bottom_ll)
    RelativeLayout bottom_ll;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.total_tv_dao)
    TextView dao;
    @BindView(R.id.total_tv_jia)
    TextView jia;
    @BindView(R.id.total_tv_wei)
    TextView wei;
    private List<BabyAttendancesModel> list_xia = new ArrayList<>();
    private List<TeacherStudentAttendModel> list_jiesong = new ArrayList<>();
    private BaseAdapter<BabyAttendancesModel> adapter_xia;
    private BaseAdapter<TeacherStudentAttendModel> adapter_jiesong;
    List<BabyAttendancesModel> list;
    BaseAdapter<BabyAttendancesModel> adapter;
    //    LinearLayoutManager layoutManager;
//    type: 0 / 1 0上午 1下午
    int type = 0;

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
//        tvClassName.setText(getIntent().getStringExtra("classname"));
        tv_time.setText(getIntent().getStringExtra("searchday"));
        setTitleText(getIntent().getStringExtra("classname"));
        setBtnLeftClickFinish();
        if (!getIntent().getBooleanExtra("isFinish1", false)) {
            setBtnRightDrawableRes(R.drawable.title_tick_black);
        }
        initShangAdapter();
        initXiaAdapter();
        initJieSongAdapter();
//        recyclerView.setVisibility(View.GONE);
//        recyclerView_xia.setVisibility(View.GONE);
        presenter.selectBabyTimeCardByClass(getIntent().getStringExtra("class"), getIntent().getStringExtra("searchday").equals("") ? DateUtil.getFormatTimeFromTimestamp(System.currentTimeMillis(), "yyyy-MM-dd") : getIntent().getStringExtra("searchday"));
        if (getIntent().getBooleanExtra("isFinish2", false)) {
            presenter.selectAttendanceByClassIdAndType2(getIntent().getStringExtra("class"), getIntent().getStringExtra("searchday"));
        } else {
            presenter.selectAttendanceByClassIdWithTypeUnComplete2(getIntent().getStringExtra("class"), getIntent().getStringExtra("searchday"));
        }
        if (getIntent().getBooleanExtra("isFinish1", false)) {
            presenter.selectAttendanceByClassIdAndType1(getIntent().getStringExtra("class"), getIntent().getStringExtra("searchday"));
        } else {
            presenter.selectAttendanceByClassIdWithTypeUnComplete1(getIntent().getStringExtra("class"), getIntent().getStringExtra("searchday"));
        }
        if(getIntent().getBooleanExtra("isXia",false)){
            tag_xia.setTextColor(getResources().getColor(R.color.black));
            tag_xia.setBackgroundColor(getResources().getColor(R.color.white));
            tag_shang.setTextColor(getResources().getColor(R.color.gray6c));
            tag_shang.setBackgroundColor(getResources().getColor(R.color.subTitleColor));
            tag_jiesong.setTextColor(getResources().getColor(R.color.gray6c));
            tag_jiesong.setBackgroundColor(getResources().getColor(R.color.subTitleColor));
            icon_ll.setVisibility(View.VISIBLE);
            text_ll.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            recyclerView_xia.setVisibility(View.VISIBLE);
            recyclerView_jiesong.setVisibility(View.GONE);
            type = 1;
            if (getIntent().getBooleanExtra("isFinish2", false)) {
                bIvRight.setVisibility(View.GONE);
            } else {
                bIvRight.setVisibility(View.VISIBLE);
            }
            bottom_ll.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tag_shang, R.id.tag_xia, R.id.tag_jiesong})
    public void tagClick(View v) {
        if (v.getId() == R.id.tag_shang) {
            tag_shang.setTextColor(getResources().getColor(R.color.black));
            tag_shang.setBackgroundColor(getResources().getColor(R.color.white));
            tag_xia.setTextColor(getResources().getColor(R.color.gray6c));
            tag_xia.setBackgroundColor(getResources().getColor(R.color.subTitleColor));
            tag_jiesong.setTextColor(getResources().getColor(R.color.gray6c));
            tag_jiesong.setBackgroundColor(getResources().getColor(R.color.subTitleColor));
            icon_ll.setVisibility(View.VISIBLE);
            text_ll.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView_xia.setVisibility(View.GONE);
            recyclerView_jiesong.setVisibility(View.GONE);
            type = 0;
            if (getIntent().getBooleanExtra("isFinish1", false)) {
                bIvRight.setVisibility(View.GONE);
            } else {
                bIvRight.setVisibility(View.VISIBLE);
            }
            bottom_ll.setVisibility(View.VISIBLE);
            reflushcount(list);
        } else if (v.getId() == R.id.tag_xia) {
            tag_xia.setTextColor(getResources().getColor(R.color.black));
            tag_xia.setBackgroundColor(getResources().getColor(R.color.white));
            tag_shang.setTextColor(getResources().getColor(R.color.gray6c));
            tag_shang.setBackgroundColor(getResources().getColor(R.color.subTitleColor));
            tag_jiesong.setTextColor(getResources().getColor(R.color.gray6c));
            tag_jiesong.setBackgroundColor(getResources().getColor(R.color.subTitleColor));
            icon_ll.setVisibility(View.VISIBLE);
            text_ll.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            recyclerView_xia.setVisibility(View.VISIBLE);
            recyclerView_jiesong.setVisibility(View.GONE);
            type = 1;
            if (getIntent().getBooleanExtra("isFinish2", false)) {
                bIvRight.setVisibility(View.GONE);
            } else {
                bIvRight.setVisibility(View.VISIBLE);
            }
            bottom_ll.setVisibility(View.VISIBLE);
            reflushcount(list_xia);
        } else if (v.getId() == R.id.tag_jiesong) {
            tag_jiesong.setTextColor(getResources().getColor(R.color.black));
            tag_jiesong.setBackgroundColor(getResources().getColor(R.color.white));
            tag_shang.setTextColor(getResources().getColor(R.color.gray6c));
            tag_shang.setBackgroundColor(getResources().getColor(R.color.subTitleColor));
            tag_xia.setTextColor(getResources().getColor(R.color.gray6c));
            tag_xia.setBackgroundColor(getResources().getColor(R.color.subTitleColor));
            icon_ll.setVisibility(View.GONE);
            text_ll.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            recyclerView_xia.setVisibility(View.GONE);
            recyclerView_jiesong.setVisibility(View.VISIBLE);
            bIvRight.setVisibility(View.GONE);
            bottom_ll.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showData(Object o) {

    }

    public void initShangAdapter() {
        list = new ArrayList<>();
        adapter = new BaseAdapter<BabyAttendancesModel>(bContext, list, false) {
            @Override
            protected void convert(ViewHolder holder, final BabyAttendancesModel data) {
                holder.setText(R.id.attendStudentItem_tv_name, data.getBabyName());

                /**3假（黄），2未（红），1到（绿）*/
                //假
                final View vacation = holder.getView(R.id.attendStudentItem_view_vacation);
                //未
                final View absence = holder.getView(R.id.attendStudentItem_view_absence);
                //到
                final View attend = holder.getView(R.id.attendStudentItem_view_attend);
                final ImageView header = holder.getView(R.id.attendStudentItem_civ_header);

                if (!StringUtils.isEmpty(data.getHeadImageurl())){
                    ImageLoader.loadHeadTarget(mContext, Const.URL_BabyHead + data.getHeadImageurl(), header);
                }else {
                    header.setImageResource(R.drawable.head_holder);
                }
                vacation.setBackgroundResource(R.drawable.attend_circle_default_bg);
                attend.setBackgroundResource(R.drawable.attend_circle_default_bg);
                absence.setBackgroundResource(R.drawable.attend_circle_default_bg);
                if (data.getState() == 1) {
                    attend.setBackgroundResource(R.drawable.attend_circle_attend_bg);
                } else if (data.getState() == 2) {
                    absence.setBackgroundResource(R.drawable.attend_circle_absence_bg);
                } else if (data.getState() == 3) {
                    vacation.setBackgroundResource(R.drawable.attend_circle_vacation_bg);
                }
                data.setTeacherId(Integer.valueOf(ZSApp.getInstance().getTeacherId()));
                if (!getIntent().getBooleanExtra("isFinish1", false)) {
                    vacation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            data.setState(3);
                            vacation.setBackgroundResource(R.drawable.attend_circle_vacation_bg);
                            attend.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            absence.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            reflushcount(list);
                        }
                    });
                    absence.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            data.setState(2);
                            vacation.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            attend.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            absence.setBackgroundResource(R.drawable.attend_circle_absence_bg);
                            reflushcount(list);
                        }
                    });
                    attend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            data.setState(1);
                            vacation.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            attend.setBackgroundResource(R.drawable.attend_circle_attend_bg);
                            absence.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            reflushcount(list);
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
    }

    public void initXiaAdapter() {
        list_xia = new ArrayList<>();
        adapter_xia = new BaseAdapter<BabyAttendancesModel>(bContext, list_xia, false) {
            @Override
            protected void convert(ViewHolder holder, final BabyAttendancesModel data) {
                holder.setText(R.id.attendStudentItem_tv_name, data.getBabyName());

                /**3假（黄），2未（红），1到（绿）*/
                //假
                final View vacation = holder.getView(R.id.attendStudentItem_view_vacation);
                //未
                final View absence = holder.getView(R.id.attendStudentItem_view_absence);
                //到
                final View attend = holder.getView(R.id.attendStudentItem_view_attend);
                final ImageView header = holder.getView(R.id.attendStudentItem_civ_header);

                if (!StringUtils.isEmpty(data.getHeadImageurl())){
                    ImageLoader.loadHeadTarget(mContext, Const.URL_BabyHead + data.getHeadImageurl(), header);
                }else {
                    header.setImageResource(R.drawable.head_holder);
                }
                vacation.setBackgroundResource(R.drawable.attend_circle_default_bg);
                attend.setBackgroundResource(R.drawable.attend_circle_default_bg);
                absence.setBackgroundResource(R.drawable.attend_circle_default_bg);
                if (data.getState() == 1) {
                    attend.setBackgroundResource(R.drawable.attend_circle_attend_bg);
                } else if (data.getState() == 2) {
                    absence.setBackgroundResource(R.drawable.attend_circle_absence_bg);
                } else if (data.getState() == 3) {
                    vacation.setBackgroundResource(R.drawable.attend_circle_vacation_bg);
                }
                data.setTeacherId(Integer.valueOf(ZSApp.getInstance().getTeacherId()));
                if (!getIntent().getBooleanExtra("isFinish2", false)) {
                    vacation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            data.setState(3);
                            vacation.setBackgroundResource(R.drawable.attend_circle_vacation_bg);
                            attend.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            absence.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            reflushcount(list_xia);
                        }
                    });
                    absence.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            data.setState(2);
                            vacation.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            attend.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            absence.setBackgroundResource(R.drawable.attend_circle_absence_bg);
                            reflushcount(list_xia);
                        }
                    });
                    attend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            data.setState(1);
                            vacation.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            attend.setBackgroundResource(R.drawable.attend_circle_attend_bg);
                            absence.setBackgroundResource(R.drawable.attend_circle_default_bg);
                            reflushcount(list_xia);
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
        recyclerView_xia.setLayoutManager(layoutManager);
        recyclerView_xia.setAdapter(adapter_xia);
    }

    public void initJieSongAdapter() {
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(bContext);
        recyclerView_jiesong.setLayoutManager(layoutManager1);
        adapter_jiesong = new BaseAdapter<TeacherStudentAttendModel>(bContext, list_jiesong, false) {
            @Override
            protected void convert(ViewHolder holder, TeacherStudentAttendModel data) {
                holder.setText(R.id.attendStudentItem_tv_name, data.getBabyName());
                final ImageView header = holder.getView(R.id.attendStudentItem_civ_header);
                if (!StringUtils.isEmpty(data.getHeadImageurl())){
                    ImageLoader.loadHeadTarget(mContext, Const.URL_BabyHead + data.getHeadImageurl(), header);
                }else {
                    header.setImageResource(R.drawable.head_holder);
                }
                if (data.getIsLeave() == 1) {
                    holder.getView(R.id.time_ll).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.litime_teshu).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.litime_teshu).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.time_ll).setVisibility(View.VISIBLE);
                    holder.setText(R.id.daotime_tv, data.getEnterTime() != 0 ? DateUtil.getFormatTimeFromTimestamp(data.getEnterTime(), "HH:mm") : "未考勤");
                    holder.setText(R.id.litime_tv, data.getOutTime() != 0 ? DateUtil.getFormatTimeFromTimestamp(data.getOutTime(), "HH:mm") : "未考勤");
                }
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.recycler_item_attendstudent_jiesong;
            }
        };
        recyclerView_jiesong.setAdapter(adapter_jiesong);

    }

    @Override
    public void showClassData(List<ClassModel> classmodels, String currentDate) {
    }

    @Override
    public void showClass2Data(List<BabyAttendancesModel> babyAttendancesModels) {
        adapter.setNewData(babyAttendancesModels);
        if(type==1){
            reflushcount(list_xia);
        }else {
            reflushcount(list);
        }
    }

    @Override
    public void showClass3Data(List<TeacherStudentAttendModel> babyAttendancesModels) {
        adapter_jiesong.setNewData(babyAttendancesModels);
    }

    @Override
    public void showClass4Data(List<BabyAttendancesModel> babyAttendancesModels) {
        adapter_xia.setNewData(babyAttendancesModels);
        if(type==1){
            reflushcount(list_xia);
        }else {
            reflushcount(list);
        }
    }

    @Override
    public void finishSuccess() {
        setResult(AttendClassActivity.requestAttendCode);
        finish();
    }

    protected void btnRightOnClick(View v) {
        if (type == 0) {
            for (BabyAttendancesModel model : list) {
                model.setTeacherId(Integer.valueOf(ZSApp.getInstance().getTeacherModel().getTeacherId()));
                model.setType(type);
            }
            presenter.insertAttendanceWithType(new Gson().toJson(list));
        } else {
            for (BabyAttendancesModel model : list_xia) {
                model.setTeacherId(Integer.valueOf(ZSApp.getInstance().getTeacherModel().getTeacherId()));
                model.setType(type);
            }
            presenter.insertAttendanceWithType(new Gson().toJson(list_xia));
        }
    }
    public void reflushcount(List<BabyAttendancesModel> list) {
        int count1=0;
        int count2=0;
        int count3=0;
        for (BabyAttendancesModel model : list) {
            if(model.isStateAttend()){
                count1++;
            }else if(model.isStateAbsence()){
                count2++;
            }else if(model.isStateVacation()){
                count3++;
            }
        }
        jia.setText(count3+"");
        wei.setText(count2+"");
        dao.setText(count1+"");
    }
}
