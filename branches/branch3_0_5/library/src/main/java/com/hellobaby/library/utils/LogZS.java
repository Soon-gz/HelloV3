package com.hellobaby.library.utils;

import android.util.Log;


/**
 * Created by zwj on 2016/9/23.
 * description : 打印日志
 */

public class LogZS {
    private static final String TAG = "ZSItLog";
    private static final boolean isLog = true;
//    private static volatile LogZS instance;

//    public static LogZS getInstance() {
//        if (null == instance) {
//            synchronized (LogZS.class) {
//                if (null == instance) {
//                    instance = new LogZS();
//                }
//            }
//        }
//        return instance;
//    }

    public static void i(String mes) {
        if(isLog){
            Log.i(TAG, mes);
        }
    }

    public static void e(String mes) {
        if(isLog){
            Log.e(TAG, mes);
        }
    }

    public static void d(String mes){
        if(isLog){
            Log.d(TAG, mes);
        }
    }

//    public static void v(String mes){
//        if(isLog){
//            Log.v(TAG, mes);
//        }
//    }
//    public static void w(String mes){
//        if(isLog){
//            Log.w(TAG, mes);
//        }
//    }

}
