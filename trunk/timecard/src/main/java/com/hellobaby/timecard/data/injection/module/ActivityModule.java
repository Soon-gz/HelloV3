package com.hellobaby.timecard.data.injection.module;

import android.app.Activity;
import android.content.Context;

import com.hellobaby.library.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }

}
