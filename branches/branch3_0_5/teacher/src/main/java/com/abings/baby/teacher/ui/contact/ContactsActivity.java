package com.abings.baby.teacher.ui.contact;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.abings.baby.teacher.ui.contact.school.ContactsSchoolFragment;
import com.abings.baby.teacher.ui.contact.student.ContactsFragment;
import com.hellobaby.library.ui.contact.BaseContactsActivity;


public class ContactsActivity extends BaseContactsActivity {

    @Override
    protected boolean isTeacher() {
        return true;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
    }

    @Override
    public Fragment setClassFragment() {
        return new ContactsFragment();
    }

    @Override
    public Fragment setSchoolFragment() {
        return new ContactsSchoolFragment();
    }


    @Override
    public void showData(Object o) {

    }
}
