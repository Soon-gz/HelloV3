package com.abings.baby.teacher.data;

import com.abings.baby.teacher.BuildConfig;
import com.abings.baby.teacher.ZSApp;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.persistentcookiejar.ClearableCookieJar;
import com.hellobaby.library.data.persistentcookiejar.PersistentCookieJar;
import com.hellobaby.library.data.persistentcookiejar.cache.SetCookieCache;
import com.hellobaby.library.data.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.hellobaby.library.data.remote.APIService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zwj on 2016/9/27.
 * description :
 */

public class RetrofitUtils {
    private static Retrofit singleton;
    private static final String BASE_URL = Const.baseUrl;
    public static final String BASE_VIDEO_URL = BASE_URL+"upload/msgVideos/";

    public static <T> T createApi(Class<T> clazz, OkHttpClient okHttpClient, RxJavaCallAdapterFactory factory) {
        synchronized (RetrofitUtils.class) {
            if (singleton == null) {
                Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());
                //拦截器
                if (okHttpClient != null) {
                    builder.client(okHttpClient);
                }
                if (factory != null) {
                    builder.addCallAdapterFactory(factory);
                }
                singleton = builder.build();
            }
        }
        return singleton.create(clazz);
    }
    /**
     * @param factory
     * @return
     */
    public static  APIService createApi(RxJavaCallAdapterFactory factory) {
        return createApi(APIService.class, createInterceptor(), factory);
    }


    /**
     * 拦截器
     *
     * @return
     */
    private static OkHttpClient createInterceptor() {
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(ZSApp.getInstance()));
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new ServerInterceptor()).addInterceptor(logging);
        builder.cookieJar(cookieJar);
        builder.connectTimeout(2*60*1000, TimeUnit.MILLISECONDS);
        builder.readTimeout(2*60*1000,TimeUnit.MILLISECONDS);
        builder.writeTimeout(2*60*1000,TimeUnit.MILLISECONDS);
//        builder.certificatePinner(new CertificatePinner.Builder().add("123","123").build());
        return builder.build();
    }
}
