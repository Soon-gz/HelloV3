package com.hellobaby.library.ui.contact;

import android.os.Bundle;
import android.widget.ListView;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;

import java.util.List;


public class BaseContactDetailActivity extends BaseLibTitleActivity {

    ListView listView;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_contact_detail;
    }

    @Override
    protected void initDaggerInject() {

    }


    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        setBtnLeftClickFinish();
        setTitleText("通讯录");
        listView = (ListView) findViewById(R.id.listView);
        listView.setDividerHeight(0);
        String selectBabyName = getIntent().getStringExtra("selectBabyName");
        if (getIntent().hasExtra("contact")) {
            List<UserModel> contact = (List<UserModel>) getIntent().getSerializableExtra("contact");
            if (contact != null) {
                ContactDetailListAdapter adapter = new ContactDetailListAdapter(BaseContactDetailActivity.this, contact,selectBabyName);
                listView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showError(String err) {

    }
}
