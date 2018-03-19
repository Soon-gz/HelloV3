package com.abings.baby.teacher.ui.attendanceManager;

import com.hellobaby.library.data.model.AskForLeaveModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

/**
 * Created by ShuWen on 2017/11/22.
 */

public interface AskForLeaveMvpView<T> extends MvpView<T> {
     void showListData(List<AskForLeaveModel> leaveModels);

}
