package com.hellobaby.timecard.data;

import android.os.Handler;
import android.os.Looper;

import com.hellobaby.library.data.BusEvent;
import com.hellobaby.library.utils.NetworkUtil;
import com.hellobaby.timecard.ZSApp;
import com.squareup.otto.Bus;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 服务器的错误
 */
public class ServerInterceptor implements Interceptor {

    @Inject
    Bus eventBus;

    public ServerInterceptor() {
        ZSApp.getInstance().getComponent().inject(this);
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        if(!NetworkUtil.isNetworkConnected(ZSApp.getInstance())){
            postBusEvent(new BusEvent.NotNetError());
            return null;//TODO 待测试，这里什么效果
        }

        Response response = chain.proceed(chain.request());
        if (response.code() == 401) {
           postBusEvent(new BusEvent.ServiceError());
        }
        return response;
    }

    private void postBusEvent(final Object object){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                eventBus.post(object);
            }
        });
    }
}
