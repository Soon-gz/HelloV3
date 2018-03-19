package com.abings.baby.teacher.ui.contact.school;

import android.content.Context;

import com.abings.baby.teacher.ZSApp;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.Contact;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.PingYinUtil;
import com.hellobaby.library.utils.PinyinComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 2016/12/30.
 */

public class ContactsSchoolPresenter extends BasePresenter<ContactsSchoolMvpView> {

    private final DataManager dataManager;


    @Inject
    public ContactsSchoolPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    /**
     * 全校老师通讯录
     */
    public void schoolTeacherContacts() {
        resetSubscription();
        String teacherId = ZSApp.getInstance().getSchoolId();
        dataManager.selectTBabysphonesAll(teacherId)
                .compose(RxThread.<BaseModel<List<TeacherModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<TeacherModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<TeacherModel> teacherModels) {
                        if (teacherModels.size() > 0 ){
                            int size = teacherModels.size();
                            List<Contact> listContactBaby = new ArrayList<>();
                            for (int i = 0; i < size; i++) {
                                TeacherModel teacherModel = teacherModels.get(i);
                                Contact contact = new Contact();
                                contact.setId(Integer.valueOf(teacherModel.getTeacherId()));
                                contact.setName(teacherModel.getTeacherName());
                                contact.setIsTeacher(true);
                                contact.setPhone(teacherModel.getPhoneNum());
                                contact.setHeadImageurl(teacherModel.getHeadImageurl());
                                contact.setPinyin(PingYinUtil.converterToFirstSpell(contact.getName()));
                                contact.setPosition(teacherModel.getPosition());
                                listContactBaby.add(contact);
                            }
                            Collections.sort(listContactBaby, new PinyinComparator());
                            bMvpView.refreshContacts(listContactBaby);
                        }
                    }
                });
    }

}
