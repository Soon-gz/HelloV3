package com.abings.baby.ui.Information.InfomationChild;

import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseInfoHintModel;
import com.hellobaby.library.data.model.BaseInfoHintOldModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.CommentModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/6/19.
 */

public class BaseInfoHintPresenter extends BasePresenter<BaseInfoHintMvpView> {
    private final DataManager mDataManager;

    @Inject
    public BaseInfoHintPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void loadUnreadMsgList(){
        mDataManager.selectInfoUpdateList()
                .compose(RxThread.<BaseModel<List<BaseInfoHintModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<BaseInfoHintModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<BaseInfoHintModel> commentModels) {
                        bMvpView.getUnreadMsgList(commentModels);
                    }
                });
    }

    public void loadOldreadedMsgList(String pageNum){
        mDataManager.selectCommList(pageNum)
                .compose(RxThread.<BaseModel<BaseInfoHintOldModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<BaseInfoHintOldModel>(bMvpView) {
                    @Override
                    protected void callSuccess(BaseInfoHintOldModel commentModels) {
                        bMvpView.getOldreadedMsgList(commentModels);
                    }
                });
    }
}
