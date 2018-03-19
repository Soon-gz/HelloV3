package com.abings.baby.teacher.ui.attendanceManager;

import com.hellobaby.library.data.model.AttendenceLeaveHistoryModel;
import com.hellobaby.library.data.model.SchoolMasterModel;
import com.hellobaby.library.ui.base.MvpView;

/**
 * Created by ShuWen on 2017/11/30.
 */

public interface SchoolMasterMvpview extends MvpView {
    void masterSelfData(AttendenceLeaveHistoryModel historyModel);
    void allSchoolData(SchoolMasterModel schoolMasterModel);
}
