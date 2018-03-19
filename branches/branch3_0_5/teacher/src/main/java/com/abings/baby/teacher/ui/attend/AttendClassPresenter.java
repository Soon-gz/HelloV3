package com.abings.baby.teacher.ui.attend;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BabyAttendancesModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.model.Contact;
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
}
