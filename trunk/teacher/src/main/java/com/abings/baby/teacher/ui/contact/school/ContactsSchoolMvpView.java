package com.abings.baby.teacher.ui.contact.school;


import com.hellobaby.library.data.model.Contact;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

public interface ContactsSchoolMvpView extends MvpView {

    /**
     * 刷新班级和班级内同学的通讯录
     */
    public void refreshContacts(List<Contact> contactModels);
}
