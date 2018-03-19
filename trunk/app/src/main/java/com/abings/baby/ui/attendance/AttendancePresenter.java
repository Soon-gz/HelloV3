package com.abings.baby.ui.attendance;

import com.abings.baby.ZSApp;
import com.abings.baby.ui.measuredata.remark.ReMarkMvpView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AgentModel;
import com.hellobaby.library.data.model.AgentSendContentModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.ReviewModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

public class AttendancePresenter extends BasePresenter<AttendanceMvpView> {

    private final DataManager mDataManager;

    @Inject
    public AttendancePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    //请假
    public void insertLeave(String startTime,
                            String endTime, String description, String type) {
        bMvpView.showProgress(true);
        String babyId = ZSApp.getInstance().getBabyId();
        String userId = ZSApp.getInstance().getUserId();
        String classId = ZSApp.getInstance().getClassId();
        String className = ZSApp.getInstance().getBabyModel().getClassName();
        String babyName = ZSApp.getInstance().getBabyModel().getBabyName();
        resetSubscription();
        mDataManager.insertLeave(userId, babyId, classId, startTime, endTime, description, type, className, babyName)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("提交成功");
                    }
                });
    }

    //代接送
    public void insertAgent(AgentModel agentModel) {
        bMvpView.showProgress(true);
        AgentSendContentModel agentSendContent = new AgentSendContentModel();
        agentSendContent.setBabyName(ZSApp.getInstance().getBabyModel().getBabyName());
        agentSendContent.setClassId(ZSApp.getInstance().getClassId());
        agentSendContent.setClassName(ZSApp.getInstance().getBabyModel().getClassName());
        agentSendContent.setCreatorName(ZSApp.getInstance().getLoginUser().getUserName());
        agentSendContent.setCreatorPhoneNum(ZSApp.getInstance().getLoginUser().getPhoneNum());
        resetSubscription();
        Gson gson = new Gson();
        mDataManager.insertAgent(gson.toJson(agentModel), gson.toJson(agentSendContent))
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg(jsonObject.getString("qrCode"));
                    }
                });
    }

    //    selectTodayAttendance
    //获取当天考情
    public void selectTodayAttendance() {
        bMvpView.showProgress(true);
        String babyId = ZSApp.getInstance().getBabyId();
        resetSubscription();
        Gson gson = new Gson();
        mDataManager.selectTodayAttendance(babyId)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
//                    bMvpView.showMsg(jsonObject.getString("qrCode"));
                        bMvpView.showJsonObject(jsonObject);
                    }
                });
    }
    //获取历史考勤
    public void selectHistoryAttendance() {
        bMvpView.showProgress(true);
        String babyId = ZSApp.getInstance().getBabyId();
        resetSubscription();
        Gson gson = new Gson();
        mDataManager.selectHistoryAttendance(babyId)
                .compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
//                    bMvpView.showMsg(jsonObject.getString("qrCode"));
                        bMvpView.showJsonArray(jsonObject);
                    }
                });
    }
}
