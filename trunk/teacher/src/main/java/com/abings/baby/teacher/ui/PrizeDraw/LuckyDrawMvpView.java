package com.abings.baby.teacher.ui.PrizeDraw;

import com.hellobaby.library.data.model.PrizeDrawBean;
import com.hellobaby.library.data.model.PrizeLuckyModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

/**
 * Created by ShuWen on 2017/5/9.
 */

public interface LuckyDrawMvpView<T> extends MvpView<T> {
    void getScores(String integer);
    void getRecordDrawList(List<PrizeDrawBean> duiPrizeBeanList);
    void getLuckyDraw(PrizeLuckyModel prizeLuckyModel);
}
