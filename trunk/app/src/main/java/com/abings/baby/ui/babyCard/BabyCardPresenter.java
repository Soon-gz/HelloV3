package com.abings.baby.ui.babyCard;

import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BabyRelationModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/3/13.
 */

public class BabyCardPresenter extends BasePresenter<BabyCardMvpView<List<BabyRelationModel>>> {

    private DataManager dataManager;

    @Inject
    public BabyCardPresenter(DataManager dataManager){
        this.dataManager = dataManager;
    }

    public void getBabyRelationse(int babyId){
        dataManager.getBabyRelationes(babyId)
                .compose(RxThread.<BaseModel<List<BabyRelationModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<BabyRelationModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<BabyRelationModel> babyRelationModels) {
                        bMvpView.showData(babyRelationModels);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        bMvpView.noCards();
                    }

                });
    }

    /**
     * 扫描二维码
     * @param code
     */
    public void qrScanCode(String code){
        dataManager.qrScanCode(code).compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.qrScanCode();
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        bMvpView.qrcodeUnuse();
                    }
                });
    }

}
