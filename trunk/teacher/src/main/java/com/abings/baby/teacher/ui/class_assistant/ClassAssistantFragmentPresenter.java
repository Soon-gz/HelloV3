package com.abings.baby.teacher.ui.class_assistant;

import android.os.SystemClock;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.ClassAssistantModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zwj on 2017/5/3.
 * description :
 */

public class ClassAssistantFragmentPresenter extends BasePresenter<ClassAssistantFragmentMvpView> {
    @Inject
    DataManager dataManager;

    @Inject
    public ClassAssistantFragmentPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * 加载第一页
     *
     * @param type
     */
    public void classroomAssistList(int type) {
        dataManager.classroomAssistList(type, 1)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        List<ClassAssistantModel> list = JSONArray.parseArray(jsonObject.getJSONArray("list").toJSONString(), ClassAssistantModel.class);
                        PageModel pageModel = JSON.parseObject(jsonObject.toJSONString(), PageModel.class);
                        bMvpView.showClassAssistantList(list, pageModel);
                    }
                });
    }


    public void classroomAssistLoadMoreList(int type, int pagerNum) {
        dataManager.classroomAssistList(type, pagerNum)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        List<ClassAssistantModel> list = JSONArray.parseArray(jsonObject.getJSONArray("list").toJSONString(), ClassAssistantModel.class);
                        PageModel pageModel = JSON.parseObject(jsonObject.toJSONString(), PageModel.class);
                        bMvpView.showClassAssistantLoadMoreList(list, pageModel);
                    }
                });
    }

    public void classroomAssistSearchList(String search) {
        bMvpView.showProgress(true);
        dataManager.classroomAssistSearchList(search, 1)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        List<ClassAssistantModel> list = JSONArray.parseArray(jsonObject.getJSONArray("list").toJSONString(), ClassAssistantModel.class);
                        PageModel pageModel = JSON.parseObject(jsonObject.toJSONString(), PageModel.class);
                        bMvpView.showClassAssistantList(list, pageModel);
                    }
                });
    }
}
