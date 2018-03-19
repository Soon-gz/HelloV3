package com.hellobaby.library.widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;

/**
 * 等待圆圈
 */
public class ProgressDialogHelper {
    private ProgressDialog progressDialog = null;
    private final String defaultMessage = "正在加载...";

    private static final ProgressDialogHelper mInstance = new ProgressDialogHelper();
    private String content;

    private ProgressDialogHelper() {
    }

    public static ProgressDialogHelper getInstance() {
        return mInstance;
    }

    synchronized public boolean isWorking() {
        if (progressDialog == null || !progressDialog.isShowing()) return false;
        return true;
    }

    /**
     * 设置进度条风格，风格为圆形，旋转的
     *
     * @param act
     * @param title   标题
     * @param message 内容
     */
    synchronized public void showProgressDialog(Activity act, String title, String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(act);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            if (title != null) {
                progressDialog.setTitle(title);
            }
            if (message == null) {
                message = defaultMessage;
            }
            progressDialog.setMessage(message);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    /**
     * 风格为圆形，旋转的。无标题
     *
     * @param act
     * @param message 内容
     */
    synchronized public void showProgressDialog(Context act, String message) {
        debugLog("showProgressDialog()  message = " + message);
        if (progressDialog != null && progressDialog.isShowing() && content.equals(message)) {

            //正在展示&相同的content的progressDialog视为同一个，不再重新创建
            debugLog("showProgressDialog()  ..............");
            return;
        } else if (progressDialog != null && progressDialog.isShowing() && !content.equals(message)) {
            //正在展示 & content不同
            hideProgressDialog();
        }

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(act);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            if (message == null) {
                message = defaultMessage;
            }
            content = message;
            progressDialog.setMessage(content);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    synchronized public void showProgressDialog(Activity act, String message, OnCancelListener listener) {
        debugLog("showProgressDialog()  message = " + message);
        if (progressDialog != null && progressDialog.isShowing() && content.equals(message)) {
            debugLog("showProgressDialog()  ..............");
            return;
        } else if ((content == null || content.isEmpty()) || progressDialog != null && progressDialog.isShowing() && !content.equals(message)) {
            hideProgressDialog();
        }

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(act);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            if (message == null) {
                message = defaultMessage;
            }
            content = message;
            progressDialog.setMessage(content);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            progressDialog.setOnCancelListener(listener);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    /**
     * 设置消息
     *
     * @param message
     */
    synchronized public void setMessage(String message) {
        if (progressDialog != null) {
            progressDialog.setMessage(message);
        }
    }

    /**
     * 隐藏
     */
    synchronized public void hideProgressDialog() {
        debugLog("hideProgressDialog()  ..............");
        if (progressDialog != null) {
            progressDialog.dismiss();
            content = "";
        }
        progressDialog = null;
    }

    private void debugLog(String msg) {
//        LogZS.d("[" + this.getClass().getSimpleName() + "] " + msg);
    }
}
