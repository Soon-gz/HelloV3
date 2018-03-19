package com.abings.baby.teacher.ui.splash;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.login.LoginActivity;
import com.abings.baby.teacher.ui.login.LoginMvpView;
import com.abings.baby.teacher.ui.login.LoginPresenter;
import com.abings.baby.teacher.ui.main.MainActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AppVersionModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.upapp.UpAppDialogActivity;
import com.hellobaby.library.utils.AppUtils;
import com.hellobaby.library.utils.SharedPreferencesUtils;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by zwj on 2016/11/7.
 * description : 闪图
 */

public class SplashActivity extends BaseLibActivity implements LoginMvpView {
    @Inject
    LoginPresenter presenter;
    @BindView(R.id.splash_main)
    LinearLayout splash_main;
    @Override
    public void toMain() {
        startActivity(new Intent(bContext, MainActivity.class));
        presenter.setAlias();
        finishDefault();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent, this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 19){
            splash_main.setSystemUiVisibility(View.INVISIBLE);
        }
        presenter.attachView(this);
        presenter.appVersionGet();

//        new CheckVersionThread(this, Const.apkDownPath, Const.url_teacher).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String phoneNum = (String) SharedPreferencesUtils.getParam(bContext, Const.keyPhoneNum, "");
                String pwd = (String) SharedPreferencesUtils.getParam(bContext, Const.keyPwd, "");
                if (phoneNum != null && !phoneNum.isEmpty() && pwd != null && !pwd.isEmpty()) {
                    presenter.loginTeacher(phoneNum, pwd);
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finishDefault();
                }
            }
        }, 1000);
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showError(String err) {
//        super.showError(err);
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.putExtra("isErr", true);
        startActivity(intent);
        finishDefault();
    }


    @Override
    public void toUpdate(AppVersionModel model) {
        if (model!= null && !model.isForceFlag()) {
            String nowVersion = AppUtils.getVersionName(bContext);
            if (!nowVersion.equals(model.getVersion())) {
                setNotificationUpdate(model);
            }
        }
    }


    private void setNotificationUpdate(AppVersionModel model) {
        int NOTIFICATION_FLAG = 1;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(bContext, UpAppDialogActivity.class);

        intent.putExtra(AppVersionModel.kName, model);
        PendingIntent pendingIntent = PendingIntent.getActivity(bContext, 0, intent, 0);
        Notification notify = new Notification.Builder(bContext)
                .setSmallIcon(R.drawable.app_log)
                .setTicker("新版本来了,请尽快升级哦！")
                .setContentTitle("升级提醒")
                .setContentText("新版本来了,请尽快升级哦！")
                .setContentIntent(pendingIntent).getNotification();
        notify.defaults = Notification.DEFAULT_ALL;
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notify.flags |= Notification.FLAG_SHOW_LIGHTS;
        manager.notify(NOTIFICATION_FLAG, notify);
    }
}
