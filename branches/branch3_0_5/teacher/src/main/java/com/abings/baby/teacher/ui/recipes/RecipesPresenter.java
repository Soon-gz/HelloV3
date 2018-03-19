package com.abings.baby.teacher.ui.recipes;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/1/6.
 */

public class RecipesPresenter extends BasePresenter<RecipesMvp> {
    private final DataManager mDataManager;

    @Inject
    public RecipesPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void getRecipes(){
        mDataManager.selectTrecipeTeacher(ZSApp.getInstance().getSchoolId()).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showListData(jsonObject);
                    }
                });
    }
}
