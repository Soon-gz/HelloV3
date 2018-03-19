package com.hellobaby.timecard.data.injection.component;

import android.content.Context;

import com.hellobaby.library.injection.ActivityContext;
import com.hellobaby.library.injection.PerActivity;
import com.hellobaby.library.injection.component.BaseActivityComponent;
import com.hellobaby.timecard.data.injection.module.ActivityModule;
import com.hellobaby.timecard.ui.main.MainActivity;
import com.hellobaby.timecard.ui.camera.CameraActivity;
import com.hellobaby.timecard.ui.setting.SettingActivity;
import com.hellobaby.timecard.ui.setting.login.SettingLoginActivity;
import com.hellobaby.timecard.uiPortrait.MainActivity_portrait;

import dagger.Component;

/**
 * Created by zwj on 2016/9/27.
 * description :
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent extends BaseActivityComponent {
    @ActivityContext
    Context context();

    void inject(MainActivity mainActivity);

    void inject(MainActivity_portrait mainActivity_portrait);

    void inject(SettingLoginActivity settingLoginActivity);

    void inject(SettingActivity settingActivity);

    void inject(CameraActivity cameraActivity);
}
