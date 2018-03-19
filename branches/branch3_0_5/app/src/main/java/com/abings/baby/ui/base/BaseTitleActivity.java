package com.abings.baby.ui.base;

import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.data.injection.component.ActivityComponent;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;

/**
 * Created by zwj on 2016/10/17.
 * description :
 */

public abstract class BaseTitleActivity<T> extends BaseLibTitleActivity<T> {
    /**
     *
     * @return
     */
    @Override
    public ActivityComponent getActivityComponent() {

        return DaggerUtils.getActivityComponent(bActivityComponent, this);
    }
}
