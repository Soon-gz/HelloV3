package com.abings.baby.ui.babyCard;

import com.hellobaby.library.ui.base.MvpView;

/**
 * Created by ShuWen on 2017/3/13.
 */

public interface BabyCardMvpView<T>  extends MvpView<T>{
    void qrScanCode();
    void noCards();
    void qrcodeUnuse();
}
