package com.hellobaby.library.ui.upapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.AppVersionModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.utils.PermissionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class UpAppDialogActivity extends BaseLibActivity {

    private TextView tvDesc;
    private Button btnCancel;
    private Button btnOk;
    private View viewLine;
    private AppVersionModel model;
    private ProgressDialog mProgressDialog;


    public static void startMustAppDialog(Activity mActivity, AppVersionModel model,String versionName) {
        if (model != null && !versionName.equals(model.getVersion())&& model.isForceFlag()) {
            Intent intent = new Intent(mActivity, UpAppDialogActivity.class);
            intent.putExtra(AppVersionModel.kName, model);
            mActivity.startActivity(intent);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_upapp_dialog;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        String[] p = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionUtils.getInstance().requestPermission(bContext, p);
        tvDesc = (TextView) findViewById(R.id.upappdialog_tv_desc);
        btnCancel = (Button) findViewById(R.id.upappdialog_btn_cancel);
        btnOk = (Button) findViewById(R.id.upappdialog_btn_ok);
        viewLine = findViewById(R.id.upappdialog_line);
        model = getIntent().getParcelableExtra(AppVersionModel.kName);
        if(model==null){
            model = new AppVersionModel();
            model.setRemark("未知异常，请稍后再试");
            model.setDownloadUrl("");
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            return;
        }
        tvDesc.setText(model.getRemark());
        if (model.isForceFlag()) {
            viewLine.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            setFinishOnTouchOutside(false);
        } else {
            viewLine.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
        }
        initClick();
    }

    private void initClick() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upAppClick();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upAppCancelClick();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, this)) {
            //成功
        } else {
            showError("获取权限失败");//会对您的使用造成不便
        }
    }

    public void upAppClick() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String url = model.getDownloadUrl();//getIntent().getStringExtra("url");
            if (url != null && !url.isEmpty()) {
                toUpApp(url);
            } else {
                showToast("下载异常，请稍后再试");
            }
        } else {
            showToast("SDCard不存在");
        }
    }

    public void upAppCancelClick() {
        finish();
    }


    @Override
    public void showData(Object o) {

    }

    public void toUpApp(String url) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        mProgressDialog = showProgressHorizontalDialog(mProgressDialog, "下载","正在下载最新版本",bContext,65535);
        downloadFile(url, Const.apkDownPath, fileName)//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribeOn(Schedulers.io())//
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        //关闭当前的Dialog
                        showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showProgress(false);
                        showError("下载异常，请稍后再试");
                    }

                    @Override
                    public void onNext(String s) {
                        showProgress(false);
                        if (null != mProgressDialog && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                            mProgressDialog = null;
                        }
                        installApp(s);
                    }
                });
    }

    /**
     * @param url      下载地址
     * @param path     存储位置
     * @param fileName 存储的文件名
     * @return
     */
    public Observable<String> downloadFile(String url, final String path, final String fileName) {
        return Observable.just(url).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String downloadUrl) {
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();

                    builder.connectTimeout(3000, TimeUnit.MILLISECONDS);
                    builder.readTimeout(3000, TimeUnit.MILLISECONDS);
                    builder.writeTimeout(3000, TimeUnit.MILLISECONDS);
                    OkHttpClient mOkHttpClient = builder.build();
                    Request request = new Request.Builder().url(downloadUrl).tag(this).build();
                    Response response = mOkHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        InputStream is = response.body().byteStream();
                        byte[] buf = new byte[2048];
                        int len = 0;
                        FileOutputStream fos = null;
                        try {
                            is = response.body().byteStream();
                            final long total = response.body().contentLength();
                            mProgressDialog.setMax((int) total);
                            long sum = 0;
                            File dir = new File(path);
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            File file = new File(dir, fileName);
                            fos = new FileOutputStream(file);
                            while ((len = is.read(buf)) != -1) {
                                sum += len;
                                fos.write(buf, 0, len);
                                mProgressDialog.setProgress((int) sum);
                            }
                            fos.flush();
                            return Observable.just(file.getAbsolutePath());
                        } finally {
                            if (is != null) is.close();
                            if (fos != null) fos.close();
                        }

                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }


    public void installApp(String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(path);
        Uri apkURI;
        if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
            apkURI = FileProvider.getUriForFile(bContext, bContext.getApplication().getPackageName() + ".provider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkURI, "application/vnd.android.package-archive");
        }else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        startActivity(intent);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public static ProgressDialog showProgressHorizontalDialog(ProgressDialog dialog, String title, String msg, Context context, int max) {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
        dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(max);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
        return dialog;
    }
}
