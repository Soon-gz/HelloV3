package com.abings.baby.teacher.ui.main.fm.school;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * 作者：ShuWen
 * 时间： 2017/12/28.
 * 描述：
 */

public class SchoolMasterPresenter extends BasePresenter {

    DataManager dataManager;

    @Inject
    public SchoolMasterPresenter(DataManager dataManager){
        this.dataManager = dataManager;
    }

    public void selectClassAndGrade(){
        dataManager.selectClassAndGrade(Integer.parseInt(ZSApp.getInstance().getSchoolId()))
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject s) {
                        bMvpView.showData(s);
                    }
                });
    }

}
