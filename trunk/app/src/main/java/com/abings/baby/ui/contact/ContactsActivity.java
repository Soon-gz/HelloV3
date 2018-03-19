package com.abings.baby.ui.contact;

import android.support.v4.app.Fragment;

import com.hellobaby.library.ui.contact.BaseContactsActivity;

public class ContactsActivity extends BaseContactsActivity {

    @Override
    protected boolean isTeacher() {
        return false;
    }

    @Override
    public Fragment setClassFragment() {
        return new ContactsFragment();
    }

    @Override
    public void showData(Object o) {

    }
}
