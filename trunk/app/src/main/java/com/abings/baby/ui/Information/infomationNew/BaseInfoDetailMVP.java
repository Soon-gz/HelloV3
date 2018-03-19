package com.abings.baby.ui.Information.infomationNew;

import com.hellobaby.library.data.model.SelectInfoDetailModel;
import com.hellobaby.library.ui.base.MvpView;

/**
 * Created by ShuWen on 2017/6/13.
 */

public interface BaseInfoDetailMVP extends MvpView{
    void addCommentSuccess();
    void deleteCommentSuccess();
    void deleteAlbumSuccess();
    void selectInfoDetails(SelectInfoDetailModel detailModel);
    void noContent();
}
