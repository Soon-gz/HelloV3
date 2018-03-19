package com.hellobaby.library.utils;
import android.content.Context;
import android.telephony.TelephonyManager;
/**
 * Created by ShuWen on 2017/2/14.
 */

public class PhoneUtil {
    /**
     * The device's OS version.
     */
    public static String ANDROID_VERSION  = null;

    /**
     * The device's model name.
     */
    public static String PHONE_MODEL = null;

    /**
     * The device's model manufacturer name.
     */
    public static String PHONE_MANUFACTURER = null;

    /**
     * The device's IMEI.
     */
    public static String PHONE_IMEI = null;


    public static String getDeviceVersion(){
        PhoneUtil.ANDROID_VERSION = android.os.Build.VERSION.RELEASE;
        return PhoneUtil.ANDROID_VERSION;
    }
    public static String getDeviceModel(){
        PhoneUtil.PHONE_MODEL = android.os.Build.MODEL;
        return PhoneUtil.PHONE_MODEL;
    }
    public static String getDeviceManufacture(){
        PhoneUtil.PHONE_MANUFACTURER = android.os.Build.MANUFACTURER;
        return PhoneUtil.PHONE_MANUFACTURER;
    }
    public static String getDeviceIMEI(Context context){
        TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneUtil.PHONE_IMEI = telephonyManager.getDeviceId();
        return PhoneUtil.PHONE_IMEI;
    }
}
