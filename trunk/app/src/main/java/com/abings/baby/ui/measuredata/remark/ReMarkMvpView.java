package com.abings.baby.ui.measuredata.remark;

import com.hellobaby.library.data.model.ReviewModel;
import com.hellobaby.library.ui.base.MvpView;

/**
 * Created by zwj on 2016/9/28.
 * description : 登录的按钮
 */

public interface ReMarkMvpView<T> extends MvpView<T> {
    public void setReMark(ReviewModel reMark);
}
