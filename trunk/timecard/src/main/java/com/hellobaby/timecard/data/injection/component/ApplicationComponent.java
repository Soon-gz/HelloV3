package com.hellobaby.timecard.data.injection.component;


import android.app.Application;
import android.content.Context;

import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.remote.APIService;
import com.hellobaby.library.injection.ApplicationContext;
import com.hellobaby.timecard.ZSApp;
import com.hellobaby.timecard.data.ServerInterceptor;
import com.hellobaby.timecard.data.injection.module.ApplicationModule;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(ZSApp zsApp);
    void inject(ServerInterceptor serverInterceptor);

    @ApplicationContext
    Context context();

    Application application();

    APIService apiService();


    DataManager dataManager();

    Bus bus();



}