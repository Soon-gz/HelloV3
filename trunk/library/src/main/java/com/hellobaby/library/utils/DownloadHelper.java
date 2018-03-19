package com.hellobaby.library.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.hellobaby.library.Const;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Func1;

import static com.hellobaby.library.Const.FAIL;

/**
 * Created by vishnu on 06/07/15.
 */
public class DownloadHelper {
    /**
     * @param url      下载地址
     * @param path     存储位置
     * @param fileName 存储的文件名
     * @return
     */
    public static Observable<String> downloadFile(final Context mcontext, String url, final String path, final String fileName) {
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
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri uri = Uri.fromFile(file);
                            intent.setData(uri);
                            mcontext.sendBroadcast(intent);
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

    /**
     * 作者：ShuWen
     * 日期：2017/12/11  15:33
     * 描述：下载文件
     * @param mcontext
     * @param url  下载链接
     * @param fileName  文件位置
     * @return  返回一个retorfit的观察者
     */
    public static Observable<String> downloadFile(final Context mcontext, String url, final String fileName) {
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
                            File file = new File(fileName);
                            fos = new FileOutputStream(file);
                            while ((len = is.read(buf)) != -1) {
                                sum += len;
                                fos.write(buf, 0, len);
                                final long finalSum = sum;
                            }
                            fos.flush();
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri uri = Uri.fromFile(file);
                            intent.setData(uri);
                            mcontext.sendBroadcast(intent);
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
}
