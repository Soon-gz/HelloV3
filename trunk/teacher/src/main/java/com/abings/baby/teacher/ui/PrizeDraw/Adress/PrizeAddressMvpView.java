package com.abings.baby.teacher.ui.PrizeDraw.Adress;

import com.hellobaby.library.data.model.PrizeAddressModel;
import com.hellobaby.library.data.model.PrizeContactInfoModel;
import com.hellobaby.library.ui.base.MvpView;

/**
 * Created by ShuWen on 2017/5/9.
 */

public interface PrizeAddressMvpView extends MvpView{
    void getContactInfo(PrizeContactInfoModel infoModel);
    void inserContactInfo(PrizeAddressModel prizeAddressModel);
}
