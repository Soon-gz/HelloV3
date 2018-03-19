package com.abings.baby.ui.contact;

import android.content.Intent;

import com.abings.baby.data.injection.DaggerUtils;
import com.hellobaby.library.data.model.Contact;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.contact.BaseContactDetailActivity;
import com.hellobaby.library.ui.contact.BaseContactsFragment;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;


/**
 *
 */
public class ContactsFragment extends BaseContactsFragment implements ContactsMvpView {
    @Inject
    ContactsPresenter presenter;

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent((((BaseLibActivity) getActivity()).getActivityComponent()), getActivity()).inject(this);
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        presenter.attachView(this);
        presenter.selectTeacherphonesfrombaby();
    }



    @Override
    public void refreshContacts(List<Contact> contactModels) {
        insertContacts(contactModels);
    }

    @Override
    public void toRelation(List<UserModel> userModels,String selectBabyName) {

        List<UserModel> list = userModels;
        if (list != null && list.size() > 0) {
            //宝宝名字进入关系人列表
            Intent inten = new Intent(getActivity(), BaseContactDetailActivity.class);
            inten.putExtra("contact", (Serializable) list);
            inten.putExtra("selectBabyName", selectBabyName);
            startActivity(inten);
        } else {
            showToast("无家长联系方式！");
        }
    }


    @Override
    protected void onItemClickUser(Contact contact, int position) {
        presenter.selectTBabysphonesfrombaby(contact);
    }

    @Override
    public void showData(Object o) {

    }

}
