package com.abings.baby.ui.contact;

import com.abings.baby.ZSApp;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.Contact;
import com.hellobaby.library.data.model.TeacherModel;
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
        final String babyId = ZSApp.getInstance().getBabyId();
        bMvpView.showProgress(true);
        resetSubscription();

        dataManager.selectTeacherphonesfrombaby(babyId)
                .compose(RxThread.<BaseModel<List<TeacherModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<TeacherModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<TeacherModel> teacherModels) {
                        int size = teacherModels.size();
                        List<Contact> listContactTeacher = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            Contact contact = new Contact();
                            TeacherModel teacher = teacherModels.get(i);
                            contact.setId(Integer.valueOf(teacher.getTeacherId()));
                            contact.setName(teacher.getTeacherName());
                            contact.setIsTeacher(true);
                            contact.setPhone(teacher.getPhoneNum());
                            contact.setPosition(teacher.getPosition());
                            contact.setHeadImageurl(teacher.getHeadImageurl());
                            contact.setPinyin("#" + PingYinUtil.converterToFirstSpell(contact.getName()));
                            listContactTeacher.add(contact);
                        }
                        Collections.sort(listContactTeacher, new PinyinComparator());
                        contacts.addAll(listContactTeacher);
                        bMvpView.refreshContacts(contacts);
                    }
                });

//        dataManager.selectTeacherphonesfrombaby(babyId)
////                .flatMap(new Func1Class<List<TeacherModel>, BaseModel<List<BabyModel>>>(bMvpView) {
//                .flatMap(new Func1Class<List<TeacherModel>, String>(bMvpView) {
//                    @Override
////                    protected Observable<BaseModel<List<BabyModel>>> callSuccess(List<TeacherModel> teacherContactModels) {
//                    protected Observable<String> callSuccess(List<TeacherModel> teacherContactModels) {
//                            int size = teacherContactModels.size();
//                        List<Contact> listContactTeacher = new ArrayList<>();
//                        for (int i = 0; i < size; i++) {
//                            Contact contact = new Contact();
//                            TeacherModel teacher = teacherContactModels.get(i);
//                            contact.setId(Integer.valueOf(teacher.getTeacherId()));
//                            contact.setName(teacher.getTeacherName());
//                            contact.setIsTeacher(true);
//                            contact.setPhone(teacher.getPhoneNum());
//                            contact.setPosition(teacher.getPosition());
//                            contact.setHeadImageurl(teacher.getHeadImageurl());
//                            contact.setPinyin("#" + PingYinUtil.converterToFirstSpell(contact.getName()));
//                            listContactTeacher.add(contact);
//                        }
//                        Collections.sort(listContactTeacher, new PinyinComparator());
//                        contacts.addAll(listContactTeacher);
//                        return Observable.just("加载所有数据完成");
////                        return dataManager.selectTBabyphonesfrombaby(babyId);
//                    }
//                })
////                .flatMap(new Func1Class<List<BabyModel>, String>(bMvpView) {
////                    @Override
////                    protected Observable<String> callSuccess(List<BabyModel> list) {
////                        int size = list.size();
////                        List<Contact> listContactBaby = new ArrayList<>();
////                        for (int i = 0; i < size; i++) {
////                            BabyModel babyModel = list.get(i);
////                            Contact contact = new Contact();
////                            contact.setId(babyModel.getBabyId());
////                            contact.setName(babyModel.getBabyName());
////                            contact.setIsTeacher(false);
////                            contact.setHeadImageurl(babyModel.getHeadImgUrl());
////                            contact.setPinyin(PingYinUtil.converterToFirstSpell(contact.getName()));
////                            listContactBaby.add(contact);
////                        }
////                        Collections.sort(listContactBaby, new PinyinComparator());
////                        contacts.addAll(listContactBaby);
////                        return Observable.just("加载所有数据完成");
////                    }
////                })
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
     * @param contact
     */
    public void selectTBabysphonesfrombaby(Contact contact) {
        String selectBabyId = String.valueOf(contact.getId());
        final String selectBabyName = contact.getName();
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.selectTBabysphonesfrombaby(selectBabyId)
                .compose(RxThread.<BaseModel<List<UserModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<UserModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<UserModel> list) {
                        bMvpView.toRelation(list, selectBabyName);
                    }
                });

    }



}
