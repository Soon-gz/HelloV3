package com.abings.baby.teacher.ui.base;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.data.injection.component.ActivityComponent;
import com.hellobaby.library.ui.base.BaseLibActivity;


/**
 * Created by zwj on 2016/11/28.
 * description :
 *
 * @param <T>
 */
public abstract class BaseActivity<T> extends BaseLibActivity<T> {

    /**
     *
     * @return
     */
    @Override
    public ActivityComponent getActivityComponent() {
        return DaggerUtils.getActivityComponent(bActivityComponent, this);
    }



}
