package com.hellobaby.library.ui.upapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

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


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_upapp_dialog;
    }

    @Override
    protected void initDaggerInject() {

    }


    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        tvDesc = (TextView) findViewById(R.id.upappdialog_tv_desc);
        btnCancel = (Button) findViewById(R.id.upappdialog_btn_cancel);
        btnOk = (Button) findViewById(R.id.upappdialog_btn_ok);
        String desc = getIntent().getStringExtra("desc");
        tvDesc.setText(desc);
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

    public void upAppClick() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String url = getIntent().getStringExtra("url");
            toUpApp(url);
        } else {
            Toast.makeText(this, "SDCard不存在", Toast.LENGTH_SHORT).show();
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
        showProgress(true);
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
                        showProgress(false);
                        showError("下载异常，请稍后再试");
                    }

                    @Override
                    public void onNext(String s) {
                        showProgress(false);
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
                                final long finalSum = sum;
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
        File file = new File(path);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }


}
