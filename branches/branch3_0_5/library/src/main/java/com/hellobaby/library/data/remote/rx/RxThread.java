package com.hellobaby.library.data.remote.rx;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zwj on 2016/12/28.
 * description :
 */

public class RxThread {

    public static <T> Observable.Transformer<T, T> subscribe_Io_Observe_On() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
