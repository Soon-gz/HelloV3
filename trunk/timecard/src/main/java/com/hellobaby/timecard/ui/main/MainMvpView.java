package com.hellobaby.timecard.ui.main;

import com.hellobaby.library.data.model.AppVersionModel;
import com.hellobaby.library.data.model.AttendenceTeacherModel;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.ui.base.MvpView;
import com.hellobaby.timecard.data.model.TCUserModel;

/**
 * Created by Administrator on 2017/1/1.
 */

public interface MainMvpView extends MvpView {

    void showSchoolModel(SchoolModel schoolModel);
    void showInfo(BabyModel babyModel, TCUserModel userModel,String babyId,String userId);
    void uploadHeadImgResult(boolean isSuccess,String name);
    void showTeacherInfo(AttendenceTeacherModel teacherModel,String qrCode,String teacherId, String schoolId);
    void onError(String msg);
    void toUpdate(AppVersionModel model);
    void inputUUIDClick(String uuid);
    void updateStateSuccess();
    void updateError();
}
