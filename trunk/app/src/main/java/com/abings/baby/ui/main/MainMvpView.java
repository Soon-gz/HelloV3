package com.abings.baby.ui.main;

import com.hellobaby.library.data.model.ServerCarebabyCache;
import com.hellobaby.library.data.model.TAlertBooleanModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

/**
 * Created by zwj on 2016/10/10.
 * description :
 */

public interface MainMvpView <T> extends MvpView<T> {
    public void logoutSuccess();
    public void showBadgeView(TAlertBooleanModel tAlertBooleanModel);
    void showBottomDialog(List<ServerCarebabyCache> list);
    void download(String fileName);
}
