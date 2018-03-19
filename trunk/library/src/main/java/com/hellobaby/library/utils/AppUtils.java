package com.hellobaby.library.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import java.io.File;

import static android.R.attr.path;

/**
 * Created by zwj on 2016/9/23.
 * description : App系统的帮助类
 */

public class AppUtils {
//
//    /**
//     * 版本号
//     * @param context
//     * @return
//     */
//    public static int getVersionCode(Context context) {
//        int i;
//        try {
//            i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
//        } catch (Throwable throwable) {
//            throw new RuntimeException(throwable);
//        }
//        return i;
//    }

    /**
     * 版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String s;
        try {
            s = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
        return s;
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * android系统版本号
     *
     * @return
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getPhoneInfo() {
        //手机品牌_手机型号_手机版本号
        return android.os.Build.BRAND + "_" + android.os.Build.MODEL + "_" + android.os.Build.VERSION.RELEASE;
    }


    /**
     * 安装App
     *
     * @param context
     * @param path    app路径
     */
    public void installApp(Context context, String path) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        File file = new File(path);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 判断当前应用是否是debug状态
     *
     * @param context
     * @return
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
