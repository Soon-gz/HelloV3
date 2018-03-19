package com.abings.baby.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.abings.baby.R;
import com.abings.baby.ui.login.LoginActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.ui.upapp.CheckVersionThread;

/**
 * Created by zwj on 2016/11/7.
 * description : 闪图
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new CheckVersionThread(this, Const.apkDownPath, Const.url_baby).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
