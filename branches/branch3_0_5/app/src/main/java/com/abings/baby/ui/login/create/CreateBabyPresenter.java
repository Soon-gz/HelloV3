package com.abings.baby.ui.login.create;

import com.abings.baby.ZSApp;
import com.google.gson.Gson;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/30.
 */

public class CreateBabyPresenter extends BasePresenter<CreateBabyMvpView> {

    private final DataManager dataManager;
    private BabyModel mBabyModel;

    @Inject
    public CreateBabyPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * 1.调用 tbaby/insertTBaby ,上传的用户名必须和学校预留的名字一致才可以关联，否则无法关联
     * 2.如果有头像替换的话，再执行tbaby/upload-file上传头像
     * 创建宝宝接口
     * @param babyModel
     * @param headImgPath
     * @param phoneNum
     */
    public void createBaby(final BabyModel babyModel, final String headImgPath, String phoneNum) {
        bMvpView.showProgress(true);
        resetSubscription();
        String userId = ZSApp.getInstance().getUserId();
        dataManager
                .createBaby(userId, new Gson().toJson(babyModel), phoneNum)
                .flatMap(new Func1Class<BabyModel, BaseModel<BabyModel>>(bMvpView) {

                    @Override
                    protected Observable<BaseModel<BabyModel>> callSuccess(BabyModel retBabyModel) {
                        mBabyModel = retBabyModel;
                        if (headImgPath == null) {
                            BaseModel<BabyModel> baseModel = new BaseModel();
                            baseModel.setCode("200");
                            return Observable.just(baseModel);
                        }
                        return dataManager.babyUploadHeadImg(String.valueOf(retBabyModel.getBabyId()), headImgPath);
                    }
                })
                .compose(RxThread.<BaseModel<BabyModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberBase<BaseModel<BabyModel>>(bMvpView) {
                    @Override
                    public void onNext(BaseModel<BabyModel> albumModelBaseModel) {
                        bMvpView.createBabyFinish();
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        setMessageTotalNum();
                    }
                });
    }

    /**
     * 3. 根据tbaby/insertTBaby下行babyModel中的classId是否有值
     * if(classId是空的){不调setMessageTotalNum（发件箱增加一个数量）}，
     * if(classId是有值的){表示已经关联到幼儿园了，调用setMessageTotalNum（发件箱增加一个数量为了解决消息人数和班级人数保持一致）}
     */
    private void setMessageTotalNum() {
        if(mBabyModel==null){
            return;
        }
        String classId = String.valueOf(mBabyModel.getClassId());
        if (classId == null || classId.isEmpty()) {
            return;
        }
        dataManager.setMessageTotalNum(classId)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showMsg("关联幼儿园成功");
                    }
                });
    }
}
