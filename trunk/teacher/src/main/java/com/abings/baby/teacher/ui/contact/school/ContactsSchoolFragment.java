package com.abings.baby.teacher.ui.contact.school;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.hellobaby.library.data.model.Contact;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.contact.BaseContactsFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/12/31.
 * description :
 */

public class ContactsSchoolFragment extends BaseContactsFragment implements ContactsSchoolMvpView {
    @Inject
    ContactsSchoolPresenter presenter;

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent((((BaseLibActivity) getActivity()).getActivityComponent()), getActivity()).inject(this);
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        presenter.attachView(this);
        presenter.schoolTeacherContacts();
    }



    @Override
    public void refreshContacts(List<Contact> contactModels) {
        insertContacts(contactModels);
    }

    @Override
    public void showData(Object o) {
    }

}
