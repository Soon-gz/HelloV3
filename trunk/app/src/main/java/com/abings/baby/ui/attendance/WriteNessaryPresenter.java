package com.abings.baby.ui.attendance;

import com.abings.baby.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AgentModel;
import com.hellobaby.library.data.model.AgentSendContentModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.LogZS;

import javax.inject.Inject;

public class WriteNessaryPresenter extends BasePresenter<WriteNessaryMvpView> {

    private final DataManager mDataManager;

    @Inject
    public WriteNessaryPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    /**
     * 修改家长头像
     */
    public void userUploadHeadImg(String headImgPath) {
        resetSubscription();
        bMvpView.showProgress(true);
        String userId = ZSApp.getInstance().getUserId();
        mDataManager.changeHeadImgById(userId, headImgPath)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showMsg("上传成功");
                        ZSApp.getInstance().getLoginUser().setHeadImageurl(s);
                    }
                });
    }

    /**
     * 更新用户信息
     *
     * @param userModel
     */
    public void userUpdateInfo(UserModel userModel) {
        String babyId = ZSApp.getInstance().getBabyId();
        resetSubscription();
        String userId = String.valueOf(userModel.getUserId());
        String userName = userModel.getUserName() == null ? "" : userModel.getUserName();
        String relation = userModel.getRelation() == null ? "" : userModel.getRelation();
        String userEmail = userModel.getUserEmail() == null ? "" : userModel.getUserEmail();

        mDataManager.updateUserInfo(babyId, userId, userName, relation, userEmail)
                .compose(RxThread.<BaseModel<UserModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<UserModel>(bMvpView) {
                    @Override
                    protected void callSuccess(UserModel userModel) {
                        bMvpView.showMsg("提交成功");
                    }
                });
    }
}
