package com.hellobaby.library.data.remote.rx;

import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.ui.base.MvpView;

import rx.Observable;
import rx.functions.Func1;


/**
 * Created by zwj on 2016/9/29.
 * description:基类，不确定数据源的类型
 */

public abstract class Func1Class<T, R> implements Func1<BaseModel<T>, Observable<R>> {
    protected MvpView mvpView;

    public Func1Class(MvpView mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public Observable<R> call(BaseModel<T> baseModel) {
        if (baseModel.isSuccess()) {
            //成功
            return callSuccess(baseModel.getData());
        } else if (baseModel.isError()) {
            //业务逻辑错误
            callError(baseModel);
        } else if (baseModel.isServerError()) {
            //服务器错误
            callServerError(baseModel);
        } else {
            //未知错误
            mvpView.showError("未知异常 Func1Class");
        }
        return null;
    }


    protected abstract Observable<R> callSuccess(T t);

    protected void callError(BaseModel baseModel) {
        mvpView.showError(baseModel.getMsg());
    }

    protected void callServerError(BaseModel baseModel) {
        mvpView.showError(baseModel.getMsg());
    }
}
