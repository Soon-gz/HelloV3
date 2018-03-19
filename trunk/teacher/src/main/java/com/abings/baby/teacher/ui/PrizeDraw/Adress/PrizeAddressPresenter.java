package com.abings.baby.teacher.ui.PrizeDraw.Adress;

import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.PrizeAddressModel;
import com.hellobaby.library.data.model.PrizeContactInfoModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/5/9.
 */

public class PrizeAddressPresenter extends BasePresenter<PrizeAddressMvpView> {
    private final DataManager mDataManager;

    @Inject
    public PrizeAddressPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void getContactInfo(){
        mDataManager.getContactInfo()
                .compose(RxThread.<BaseModel<List<PrizeContactInfoModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<PrizeContactInfoModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<PrizeContactInfoModel> prizeContactInfoModels) {
                        if(prizeContactInfoModels != null && prizeContactInfoModels.size() > 0){
                            bMvpView.getContactInfo(prizeContactInfoModels.get(0));
                        }
                    }
                });
    }

    public void insertContactInfo(String name,String phoneNum,String fullAddress,String locationArea,String drawId,String orderNum,String id){
        mDataManager.insertContactInfo(name, phoneNum, fullAddress, locationArea, drawId, orderNum,id)
                .compose(RxThread.<BaseModel<PrizeAddressModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<PrizeAddressModel>(bMvpView) {
                    @Override
                    protected void callSuccess(PrizeAddressModel prizeAddressModel) {
                        bMvpView.inserContactInfo(prizeAddressModel);
                    }
                });
    }



}
