package com.hellobaby.timecard.data.injection;

import android.app.Activity;

import com.hellobaby.library.injection.component.BaseActivityComponent;
import com.hellobaby.timecard.ZSApp;
import com.hellobaby.timecard.data.injection.component.ActivityComponent;
import com.hellobaby.timecard.data.injection.component.DaggerActivityComponent;
import com.hellobaby.timecard.data.injection.module.ActivityModule;

/**
 * Created by zwj on 2016/11/22.
 * description :
 */

public class DaggerUtils {
    public static ActivityComponent getActivityComponent(BaseActivityComponent mActivityComponent, Activity activity) {
        if (mActivityComponent == null) {
                    mActivityComponent = DaggerActivityComponent.builder()
                            .activityModule(new ActivityModule(activity))
                            .applicationComponent(ZSApp.getInstance().getComponent())
                            .build();

            //mActivityComponent =DaggerActivityComponent.builder().activityModule(new ActivityModule(activity)).applicationComponent(ZSApp.getInstance().getComponent()).build();
                  }
        return (ActivityComponent) mActivityComponent;
    }
}
