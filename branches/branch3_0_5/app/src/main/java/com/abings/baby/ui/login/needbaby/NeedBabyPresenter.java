package com.abings.baby.ui.login.needbaby;

import com.abings.baby.ZSApp;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by zwj on 2017-01-21.
 * description:
 */

public class NeedBabyPresenter extends BasePresenter<NeedBabyMvpView> {


    private final DataManager dataManager;

    @Inject
    public NeedBabyPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * 关注与他人的宝宝
     *
     * @param babyId
     */
    public void insertCareBaby(String babyId) {
        resetSubscription();
        final String userId = String.valueOf(ZSApp.getInstance().getUserId());
        dataManager.insertCareBaby(userId, babyId)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.careBabySuccess();
                    }
                });
    }
}
