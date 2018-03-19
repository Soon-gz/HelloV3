package com.abings.baby.ui.Information.InfomationChild;

import com.hellobaby.library.data.model.BaseInfoHintModel;
import com.hellobaby.library.data.model.BaseInfoHintOldModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

/**
 * Created by ShuWen on 2017/6/19.
 */

public interface BaseInfoHintMvpView extends MvpView {
    void getUnreadMsgList(List<BaseInfoHintModel> hintModels);
    void getOldreadedMsgList(BaseInfoHintOldModel hintModels);
}
