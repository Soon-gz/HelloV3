package com.hellobaby.library.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/12/5.
 * description : 权限申请
 */

public class PermissionUtils {
    private static PermissionUtils instance;
    private String[] permissions = new String[]{Manifest.permission.GET_ACCOUNTS, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE
    };
    public static final int requestPermissionCode = 100;

    private PermissionUtils() {
    }

    public static PermissionUtils getInstance() {
        if (instance == null) {
            instance = new PermissionUtils();
        }
        return instance;
    }

    /**
     * 权限申请
     *
     * @param activity
     */
    public void requestPermission(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        boolean isRequestPermission = false;
        for (String p : permissions) {
            if (!hasPermission(p, activity)) {
                isRequestPermission = true;
            }
        }
        if (isRequestPermission) {
            requestPermissionsSafely(permissions, requestPermissionCode, activity);
        }
    }

    public void requestPermission(Activity activity,String[] ps) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        boolean isRequestPermission = false;
        for (String p : ps) {
            if (!hasPermission(p, activity)) {
                isRequestPermission = true;
            }
        }
        if (isRequestPermission) {
            requestPermissionsSafely(ps, requestPermissionCode, activity);
        }
    }


    public boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, Activity activity) {
        if (requestCode == requestPermissionCode) {
            List<String> deniedPermissions = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permissions[i]);
                }
            }

            if (deniedPermissions.size() > 0) {
                //授权失败
                LogZS.d("授权失败:" + deniedPermissions.size());
                return false;
            } else {
                //授权成功
                return true;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
        return true;
    }


    //权限
    @TargetApi(Build.VERSION_CODES.M)
    private boolean hasPermission(String permission, Activity activity) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    //权限
    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissionsSafely(String[] permissions, int requestCode, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(permissions, requestCode);
        }
    }
}
