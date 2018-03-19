package com.abings.baby.data.injection.module;

import android.app.Application;
import android.content.Context;

import com.abings.baby.data.RetrofitUtils;
import com.hellobaby.library.data.remote.APIService;
import com.hellobaby.library.injection.ApplicationContext;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Provide application-level dependencies. Mainly singleton object that can be injected from
 * anywhere in the app.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }


    @Provides
    @Singleton
    APIService provideAPIService() {
        return RetrofitUtils.createApi(RxJavaCallAdapterFactory.create());
    }

    @Provides
    @Singleton
    Bus provideBus(){
        return new Bus();
    }

}
