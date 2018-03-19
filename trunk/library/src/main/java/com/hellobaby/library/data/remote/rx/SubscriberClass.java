package com.hellobaby.library.data.remote.rx;


import android.util.Log;

import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.ui.base.MvpView;
import com.hellobaby.library.utils.StringUtils;


/**
 * Created by zwj on 2016/9/29.
 * description:基类，针对于接口的数据源，跟BaseModel有关系的
 */

public abstract class SubscriberClass<T> extends SubscriberBase<BaseModel<T>> {


    public SubscriberClass(MvpView mvpView) {
        super(mvpView);
    }


    @Override
    public void onNext(BaseModel<T> tBaseModel) {
        Log.e("ZLog","code===>"+tBaseModel.getCode()+" ;msg=>"+tBaseModel.getMsg());
        if (tBaseModel.isSuccess()) {
            //成功
            callSuccess(tBaseModel.getData());
        } else if (tBaseModel.isError()) {
            //业务逻辑错误
            callError(tBaseModel);
        } else if (tBaseModel.isServerError()) {
            //服务器错误
            callServerError(tBaseModel);
        } else {
            if(null!=mvpView){
                mvpView.showError("未知异常sub");
            }
        }
    }

    protected abstract void callSuccess(T t);

    protected void callError(BaseModel baseModel) {
        if (!StringUtils.isEmail(baseModel.getMsg()) && ! "".equals(baseModel.getMsg())){
            if(null!=mvpView)
                mvpView.showError(baseModel.getMsg());
        }
    }

    protected void callServerError(BaseModel baseModel) {
        if(null!=mvpView) {
            if (!StringUtils.isEmail(baseModel.getMsg()) && ! "".equals(baseModel.getMsg())){
                mvpView.showError(baseModel.getMsg());
            }
        }
    }
}
