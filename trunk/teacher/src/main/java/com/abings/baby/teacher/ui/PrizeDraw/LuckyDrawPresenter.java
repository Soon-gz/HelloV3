package com.abings.baby.teacher.ui.PrizeDraw;

import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.PrizeDrawBean;
import com.hellobaby.library.data.model.PrizeLuckyModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/5/9.
 */

public class LuckyDrawPresenter extends BasePresenter<LuckyDrawMvpView> {
    private final DataManager mDataManager;

    @Inject
    public LuckyDrawPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void getScore(){
        mDataManager.getScores()
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String integer) {
                        bMvpView.getScores(integer);
                    }
                });
    }

    public void getRecordDrawList(){
        mDataManager.getRecordDrawList()
                .compose(RxThread.<BaseModel<List<PrizeDrawBean>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<PrizeDrawBean>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<PrizeDrawBean> duiPrizeBeen) {
                        bMvpView.getRecordDrawList(duiPrizeBeen);
                    }
                });
    }

    public void getLuckDraw(){
        mDataManager.getLuckDraw()
                .compose(RxThread.<BaseModel<PrizeLuckyModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<PrizeLuckyModel>(bMvpView) {
                    @Override
                    protected void callSuccess(PrizeLuckyModel prizeLuckyModel) {
                        bMvpView.getLuckyDraw(prizeLuckyModel);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        super.callError(baseModel);
                        bMvpView.showData(baseModel);
                    }
                });
    }

}
