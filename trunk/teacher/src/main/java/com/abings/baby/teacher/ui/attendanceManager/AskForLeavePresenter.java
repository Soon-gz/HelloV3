package com.abings.baby.teacher.ui.attendanceManager;

import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AskForLeaveHistoryModel;
import com.hellobaby.library.data.model.AskForLeaveModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.SchoolMasterModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/11/22.
 */

public class AskForLeavePresenter extends BasePresenter<MvpView> {
    @Inject
    DataManager dataManager;

    @Inject
    public AskForLeavePresenter(DataManager dataManager){
        this.dataManager = dataManager;
    }

    /**
     * AskForLeaveDetailActivity 提交请假申请
     *
     * @param startTime
     * @param endTime
     * @param type
     * @param reason
     */
    public void insertLeaveTeacher(long startTime, long endTime,int type, String reason){
        dataManager.insertLeaveTeacher(startTime, endTime, type, reason).compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showData(s);
                    }
                });
    }

    /**
     * 教师请假历史
     */
    public void selectLeave(){
        dataManager.selectLeave().compose(RxThread.<BaseModel<List<AskForLeaveHistoryModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<AskForLeaveHistoryModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<AskForLeaveHistoryModel> askForLeaveModels) {
                        bMvpView.showData(askForLeaveModels);
                    }
                });
    }

    /**
     * AskForLeaveManagerActivity 校长获取请假审批
     */
    public void selectApprovalList(){
        dataManager.selectApprovalList().compose(RxThread.<BaseModel<List<AskForLeaveModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<AskForLeaveModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<AskForLeaveModel> askForLeaveModels) {
                        ((AskForLeaveMvpView)bMvpView).showListData(askForLeaveModels);
                    }
                });
    }

    /**
     * 校长审批是否同意 AskForLeaveDetailActivity
     * 状态 0:待批准 1：已批准 2：已拒绝
     * @return
     */
    public void updateLeaveState(int leaveId,int state){
        dataManager.updateLeaveState(leaveId,state)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showData(s);
                    }
                });
    }


}
