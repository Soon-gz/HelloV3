package com.abings.baby.teacher.ui.PrizeDraw.PrizeDetail;

import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.ExchangeModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.ui.base.MvpView;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/5/9.
 */

public class PrizeDetailPresenter extends BasePresenter<MvpView>{
    private final DataManager mDataManager;

    @Inject
    public PrizeDetailPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void exchangePool(String exchangeId){
        mDataManager.exchangePool(exchangeId)
                .compose(RxThread.<BaseModel<ExchangeModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<ExchangeModel>(bMvpView) {
                    @Override
                    protected void callSuccess(ExchangeModel exchangeModel) {
                        bMvpView.showData(exchangeModel);
                    }
                });
    }
}
