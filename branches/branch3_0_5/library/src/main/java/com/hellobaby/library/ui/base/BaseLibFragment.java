package com.hellobaby.library.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hellobaby.library.R;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.ProgressDialogHelper;
import com.hellobaby.library.widget.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by zwj on 2016/10/10.
 * description :基类
 */

public abstract class BaseLibFragment<T> extends Fragment implements MvpView<T> {
    protected String bClassSimpleName;
    protected View mContentView;
    protected Unbinder bUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bClassSimpleName = this.getClass().getSimpleName();
        setRetainInstance(true);
        //注入
        initDaggerInject();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            mContentView = inflater.inflate(getContentViewLayoutID(), null);
            bUnbinder = ButterKnife.bind(this, mContentView);
            //初始化方法
            initViewsAndEvents();
            return mContentView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }


    }


    /**
     * 注入Dagger
     */
    protected abstract void initDaggerInject();

    /**
     * 获取布局id
     *
     * @return
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 初始化事件
     */
    protected abstract void initViewsAndEvents();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bUnbinder != null) {
            bUnbinder.unbind();
        }
    }

    @Override
    public void showProgress(boolean isShow) {
        if (isShow) {
            ProgressDialogHelper.getInstance().showProgressDialog(getContext(), "正在加载...");
        } else {
            ProgressDialogHelper.getInstance().hideProgressDialog();
        }
    }

    /**
     * 默认
     *
     * @param intent
     */
    public void startActivityDefault(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out);
    }

    /**
     * 默认
     *
     * @param intent
     * @param requestCode
     */
    public void startActivityForResultDefault(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out);
    }

    /**
     * 打印info日志
     *
     * @param mes
     */
    protected void logI(String mes) {
        LogZS.i("[" + bClassSimpleName + "]" + mes);
    }

    /**
     * 打印error日志
     *
     * @param mes
     */
    protected void logE(String mes) {
        LogZS.e("[" + bClassSimpleName + "]" + mes);
    }

    /**
     * 展示toast弹窗
     *
     * @param text
     */
    protected void showToast(String text) {
        ToastUtils.showNormalToast(getContext(), text);
    }
    /**
     * 展示toast弹窗  错误展示
     *
     * @param text
     */
    protected void showToastError(String text) {
        ToastUtils.showNormalToast(getContext(), text);
    }

    /**
     * 展示toast弹窗  成功展示
     *
     * @param text
     */
    protected void showToastSuccess(String text) {
        ToastUtils.showNormalToast(getContext(), text);
    }

    @Override
    public void showError(String err) {
        showToastError(err);
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

}
