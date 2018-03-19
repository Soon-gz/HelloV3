package com.abings.baby.ui.base;

import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.data.injection.component.ActivityComponent;
import com.hellobaby.library.ui.base.BaseLibActivity;


/**
 * Created by zwj on 2016/9/28.
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
//        if (mActivityComponent == null) {
//            mActivityComponent = DaggerActivityComponent.builder()
//                    .activityModule(new ActivityModule(activity))
//                    .applicationComponent(ZSApp.getInstance().getComponent())
//                    .build();
//        }
        return DaggerUtils.getActivityComponent(bActivityComponent, this);
    }



}
