package com.abings.baby.ui.babyCard.babyCardRelations;

import com.abings.baby.ZSApp;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.ui.base.MvpView;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ShuWen on 2017/3/13.
 */

public class BabyCardRelationPresenter extends BasePresenter<BabyCardRelationMvpView> {
    private DataManager dataManager;

    @Inject
    public BabyCardRelationPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * 添加一个接送卡使用者
     * @param qrcode
     * @param userName
     * @param phoneNum
     * @param relation
     * @param filePath
     */
    public void insertTPCard(String qrcode, String userName, String phoneNum, String relation, final String filePath) {
        dataManager.insertBabycard(ZSApp.getInstance().getUserId(), ZSApp.getInstance().getBabyId(), qrcode, userName, phoneNum, relation)
                .flatMap(new Func1Class<String, BaseModel<String>>(bMvpView) {
                    @Override
                    protected Observable<BaseModel<String>> callSuccess(String s) {
                        int fileId = Integer.parseInt(s);
                        if (filePath != null) {
                            return dataManager.uploadBabyCardHead(fileId, filePath);
                        } else {
                            bMvpView.uploadWithOutHeadImg();
                            return null;
                        }
                    }
                }).compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
            @Override
            protected void callSuccess(String s) {
                bMvpView.uploadSuccess();
            }
        });
    }

    /**
     * 更新接送卡
     * @param pickUpId
     * @param userName
     * @param phoneNum
     * @param relation
     */
    public void updateBabyCard(final int pickUpId, String userName, String phoneNum, String relation){
        dataManager.updateBabyCard(pickUpId, userName, phoneNum, relation)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject s) {
                        bMvpView.updateSuccess();
                    }
                });
    }

    public void uploadHead(int pickUpId, String headImgPath) {
        dataManager.uploadBabyCardHead(pickUpId,headImgPath)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.updateHead();
                    }
                });
    }

    public void deleteBabyCard(int pickUpId){
        dataManager.deleteBabyCard(pickUpId)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.deleteSuccess();
                    }
                });
    }
}
