package com.abings.baby.teacher.ui.attendanceManager;

import com.abings.baby.teacher.ZSApp;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AttendenceLeaveHistoryModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.SchoolMasterModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.ui.base.MvpView;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/7/4.
 */

public class AttendencePresenter extends BasePresenter<MvpView>{
    private DataManager mDataManager;

    @Inject
    public AttendencePresenter(DataManager mDataManager){
        this.mDataManager = mDataManager;
    }

    /**
     * 获取教师的历史考勤记录  包括了考勤和请假记录  2017/11/24
     */
    public void getAttendenceHistory(){
        mDataManager.selectHistoryAttendance()
                .compose(RxThread.<BaseModel<AttendenceLeaveHistoryModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<AttendenceLeaveHistoryModel>(bMvpView) {
                    @Override
                    protected void callSuccess(AttendenceLeaveHistoryModel model) {
                        bMvpView.showData(model);
                    }
                });
    }

    /**
     * 校长获取自己的考勤记录
     */
    public void getAttendenceHistoryMaster(){
        mDataManager.selectHistoryAttendance()
                .compose(RxThread.<BaseModel<AttendenceLeaveHistoryModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<AttendenceLeaveHistoryModel>(bMvpView) {
                    @Override
                    protected void callSuccess(AttendenceLeaveHistoryModel model) {
                        ((SchoolMasterMvpview)bMvpView).masterSelfData(model);
                    }
                });
    }


    /**
     * 校长查询全校的考勤记录
     * @param schoolId
     */
    public void selectLeaveList(int schoolId){
        mDataManager.selectLeaveList(schoolId)
                .compose(RxThread.<BaseModel<SchoolMasterModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<SchoolMasterModel>(bMvpView) {
                    @Override
                    protected void callSuccess(SchoolMasterModel schoolMasterModel) {
                        ((SchoolMasterMvpview)bMvpView).allSchoolData(schoolMasterModel);
                    }
                });
    }

    /**
     * 作者：ShuWen
     * 日期：2017/12/12  14:55
     * 描述：校长考勤界面请假审批，查看是否有新的请假申请
     *
     * @return [返回类型说明]
     */
    public void selectTeacherLeaveAlert(){
        mDataManager.selectTeacherLeaveAlert(Integer.parseInt(ZSApp.getInstance().getSchoolId()))
                .compose(RxThread.<BaseModel<Integer>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<Integer>(bMvpView) {
                    @Override
                    protected void callSuccess(Integer integer) {
                        ((AttendenceMvpView)bMvpView).showRedPoint(integer);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {

                    }
                });
    }



}
