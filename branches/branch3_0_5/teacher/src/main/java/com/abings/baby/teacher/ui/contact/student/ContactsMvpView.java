package com.abings.baby.teacher.ui.contact.student;


import com.hellobaby.library.data.model.Contact;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

public interface ContactsMvpView extends MvpView {

    /**
     * 刷新班级和班级内同学的通讯录
     */
    public void refreshContacts(List<Contact> contactModels);

    /**
     *
     * @param userModels
     */
    public void toRelation(List<UserModel> userModels, String selectBabyName);
}
