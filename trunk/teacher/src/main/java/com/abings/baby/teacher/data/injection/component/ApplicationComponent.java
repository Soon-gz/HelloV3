package com.abings.baby.teacher.data.injection.component;


import android.app.Application;
import android.content.Context;

import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.data.ServerInterceptor;
import com.abings.baby.teacher.data.injection.module.ApplicationModule;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.remote.APIService;
import com.hellobaby.library.injection.ApplicationContext;
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