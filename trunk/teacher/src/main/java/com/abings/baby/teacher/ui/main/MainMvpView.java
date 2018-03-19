package com.abings.baby.teacher.ui.main;

import com.hellobaby.library.data.model.TeacherAlertBooleanModel;
import com.hellobaby.library.ui.base.MvpView;

/**
 * Created by zwj on 2016/10/10.
 * description :
 */

public interface MainMvpView <T> extends MvpView<T> {
    public void logoutSuccess();

    void changedot(TeacherAlertBooleanModel teacherAlertBooleanModel);
    void download(String fileName);

}
