package com.abings.baby.teacher.ui.attend;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BabyAttendancesModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.model.Contact;
import com.hellobaby.library.data.model.TeacherStudentAttendModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 2016/01/01.
 */

public class AttendClassPresenter extends BasePresenter<AttendClassMvpView> {
    @Inject
    DataManager dataManager;

    private List<Contact> contacts = new ArrayList<>();


    @Inject
    public AttendClassPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    //    6.5接口功能：教师端-班级考勤，上传(tBabyattendance/insertAttendance)
    public void insertAttendance(String babyattendances) {
        dataManager.insertAttendance(babyattendances).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.finishSuccess();
                        bMvpView.showMsg("点名完成");
                    }
                });
    }
    public void insertAttendanceWithType(String babyattendances) {
        dataManager.insertAttendanceWithType(babyattendances).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.finishSuccess();
                        bMvpView.showMsg("点名完成");
                    }
                });
    }

    //    6.4接口功能：教师端-班级考勤，获取某个班级考勤列表（tBabyattendance/selectAttendanceByClassId）
    public void selectAttendanceByClassId(String classId, String searchtime) {
        dataManager.selectAttendanceByClassId(classId, searchtime).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showClass2Data(JSONArray.parseArray(jsonObject.toJSONString(), BabyAttendancesModel.class));
                    }
                });
    }
    //上午
    public void selectAttendanceByClassIdAndType1(String classId, String searchtime) {
        dataManager.selectAttendanceByClassIdAndType(classId, searchtime,"0").compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showClass2Data(JSONArray.parseArray(jsonObject.toJSONString(), BabyAttendancesModel.class));
                    }
                });
    }
    //下午
    public void selectAttendanceByClassIdAndType2(String classId, String searchtime) {
        dataManager.selectAttendanceByClassIdAndType(classId, searchtime,"1").compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showClass4Data(JSONArray.parseArray(jsonObject.toJSONString(), BabyAttendancesModel.class));
                    }
                });
    }
    //    6.21 接口功能：教师端-班级考勤，获取某个班级考勤列表（点名未完成时）
    public void selectAttendanceByClassIdUnComplete(String classId, String searchtime) {
        dataManager.selectAttendanceByClassIdUnComplete(classId, searchtime).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showClass2Data(JSONArray.parseArray(jsonObject.toJSONString(), BabyAttendancesModel.class));
                    }
                });
    }
    //    6.21 接口功能：教师端-班级考勤，获取某个班级考勤列表（点名未完成时）上午
    public void selectAttendanceByClassIdWithTypeUnComplete1(String classId, String searchtime) {
        dataManager.selectAttendanceByClassIdWithTypeUnComplete(classId, searchtime,"0").compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showClass2Data(JSONArray.parseArray(jsonObject.toJSONString(), BabyAttendancesModel.class));
                    }
                });
    }

    //    6.21 接口功能：教师端-班级考勤，获取某个班级考勤列表（点名未完成时）下午
    public void selectAttendanceByClassIdWithTypeUnComplete2(String classId, String searchtime) {
        dataManager.selectAttendanceByClassIdWithTypeUnComplete(classId, searchtime,"1").compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showClass4Data(JSONArray.parseArray(jsonObject.toJSONString(), BabyAttendancesModel.class));
                    }
                });
    }

    //    6.3接口功能：教师端-班级考勤，获取班级列表+考勤信息（tBabyattendance/selectAttendanceByTeacherId）
    public void selectAttendanceByTeacherId(String searchtime) {
        dataManager.selectAttendanceByTeacherId(searchtime, ZSApp.getInstance().getTeacherId())
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        String currentDate = jsonObject.getString("currentDate");
                        JSONArray jsonArray = jsonObject.getJSONArray("attendances");
                        bMvpView.showClassData(JSONArray.parseArray(jsonArray.toJSONString(), ClassModel.class),currentDate);
                    }
                });
    }

    public void selectAttendanceByTeacherIdWithType(String searchtime) {
        dataManager.selectAttendanceByTeacherIdWithType(searchtime, ZSApp.getInstance().getTeacherId())
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        String currentDate = jsonObject.getString("currentDate");
                        JSONArray jsonArray = jsonObject.getJSONArray("attendances");
                        bMvpView.showClassData(JSONArray.parseArray(jsonArray.toJSONString(), ClassModel.class),currentDate);
                    }
                });
    }

    //    6.21 接口功能：教师端-班级考勤，获取某个班级考勤列表（点名未完成时）
    public void selectBabyTimeCardByClass(String classId, String searchtime) {
        dataManager.selectBabyTimeCardByClass(classId, searchtime).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showClass3Data(JSONArray.parseArray(jsonObject.toJSONString(), TeacherStudentAttendModel.class));
                    }
                });
    }
}
