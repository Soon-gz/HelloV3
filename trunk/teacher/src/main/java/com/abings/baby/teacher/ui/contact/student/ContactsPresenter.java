package com.abings.baby.teacher.ui.contact.student;

import android.util.Log;

import com.abings.baby.teacher.ZSApp;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.Contact;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.PingYinUtil;
import com.hellobaby.library.utils.PinyinComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * 2016/12/30.
 */

public class ContactsPresenter extends BasePresenter<ContactsMvpView> {
    @Inject
    DataManager dataManager;

    private List<Contact> contacts = new ArrayList<>();


    @Inject
    public ContactsPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void selectTeacherphonesfrombaby() {

        final String teacherId = ZSApp.getInstance().getTeacherId();
        bMvpView.showProgress(true);
        resetSubscription();

        dataManager.selectTBabysphonesfromteacher(teacherId)
                .compose(RxThread.<BaseModel<List<BabyModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<BabyModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<BabyModel> babyModels) {
                        int size = babyModels.size();
                        List<Contact> listContactBaby = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            BabyModel babyModel = babyModels.get(i);
                            Contact contact = new Contact();
                            contact.setId(babyModel.getBabyId());
                            contact.setName(babyModel.getBabyName());
                            contact.setIsTeacher(false);
                            contact.setHeadImageurl(babyModel.getHeadImgUrl());
                            contact.setPinyin(PingYinUtil.converterToFirstSpell(contact.getName()));
                            listContactBaby.add(contact);
                        }
                        Collections.sort(listContactBaby, new PinyinComparator());
                        contacts.addAll(listContactBaby);
                        bMvpView.refreshContacts(contacts);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        bMvpView.showMsg("当前班级没有家长联系信息！");
                    }
                });

//        dataManager.selectTBabysphonesfromteacher(teacherId)
//                .flatMap(new Func1Class<List<BabyModel>, String>(bMvpView) {
//                    @Override
//                    protected Observable<String> callSuccess(List<BabyModel> list) {
//                        int size = list.size();
//                        List<Contact> listContactBaby = new ArrayList<>();
//                        for (int i = 0; i < size; i++) {
//                            BabyModel babyModel = list.get(i);
//                            Contact contact = new Contact();
//                            contact.setId(babyModel.getBabyId());
//                            contact.setName(babyModel.getBabyName());
//                            contact.setIsTeacher(false);
//                            contact.setHeadImageurl(babyModel.getHeadImgUrl());
//                            contact.setPinyin(PingYinUtil.converterToFirstSpell(contact.getName()));
//                            listContactBaby.add(contact);
//                        }
//                        Collections.sort(listContactBaby, new PinyinComparator());
//                        contacts.addAll(listContactBaby);
//                        return Observable.just("加载所有数据完成");
//                    }
//
//                    @Override
//                    protected void callError(BaseModel baseModel) {
//                        bMvpView.showMsg("当前班级没有家长联系信息");
//                    }
//                })
//                .compose(RxThread.<String>subscribe_Io_Observe_On())
//                .subscribe(new SubscriberBase<String>(bMvpView) {
//                    @Override
//                    public void onNext(String s) {
//                        bMvpView.refreshContacts(contacts);
//                    }
//                });

    }

    /**
     * 选中宝宝的联系人
     *
     * @param contact
     */
    public void selectTBabysphonesfrombaby(Contact contact) {
        Log.i("ZLog", "--->" + contact.toString());
        String selectBabyId = String.valueOf(contact.getId());
        final String selectBabyName = contact.getName();
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.selectTBabysphonesfrombaby(selectBabyId)
                .compose(RxThread.<BaseModel<List<UserModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<UserModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<UserModel> userModels) {
                        bMvpView.toRelation(userModels, selectBabyName);
                    }
                });
    }


}
