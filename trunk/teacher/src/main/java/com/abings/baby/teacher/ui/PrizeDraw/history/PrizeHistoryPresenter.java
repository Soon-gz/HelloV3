package com.abings.baby.teacher.ui.PrizeDraw.history;

import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.PrizeHistoryBean;
import com.hellobaby.library.data.model.PrizeOrderDetailModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/5/9.
 */

public class PrizeHistoryPresenter extends BasePresenter<MvpView> {
    private final DataManager mDataManager;

    @Inject
    public PrizeHistoryPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }


    public void getRecordLine(){
        mDataManager.getRecordLine()
                .compose(RxThread.<BaseModel<List<PrizeHistoryBean>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<PrizeHistoryBean>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<PrizeHistoryBean> prizeHistoryBeen) {
                        bMvpView.showData(prizeHistoryBeen);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        super.onError(e);
                        bMvpView.showMsg("记录为空！");
                    }
                });
    }


    public void getOrderList(){
        mDataManager.getOrderList()
                .compose(RxThread.<BaseModel<List<PrizeOrderDetailModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<PrizeOrderDetailModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<PrizeOrderDetailModel> prizeHistoryBeen) {
                        bMvpView.showData(prizeHistoryBeen);
                    }
                });
    }

    public void sureGet(String orderId){
        mDataManager.sureGetThings(orderId)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showData(jsonObject);
                    }
                });
    }


}
