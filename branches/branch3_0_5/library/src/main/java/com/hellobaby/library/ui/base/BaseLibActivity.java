package com.hellobaby.library.ui.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hellobaby.library.R;
import com.hellobaby.library.injection.component.BaseActivityComponent;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.ProgressDialogHelper;
import com.hellobaby.library.widget.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zwj on 2016/9/23.
 * 基础类Activity
 *
 * @param <T>
 */
public abstract class BaseLibActivity<T> extends AppCompatActivity implements MvpView<T> {
    protected Activity bContext = BaseLibActivity.this;
    protected BaseActivityComponent bActivityComponent;
    protected String bClassSimpleName;
    protected Unbinder bUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bClassSimpleName = this.getClass().getSimpleName();

        initDaggerInject();
        if (getContentViewLayoutID() != 0) {
            setContentLayout(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("你必须设置一个可以用的Layout资源 ID");
        }
        initButterKnifeBind();

        initViewsAndEvents(savedInstanceState);
    }

    /**
     * 不是继承BaseActivity，得对ActivityComponent进行初始化
     *
     * @return
     */
    public BaseActivityComponent getActivityComponent() {
        return bActivityComponent;
    }

    /**
     * 加载布局
     *
     * @param layoutResID
     */
    protected void setContentLayout(int layoutResID) {
        setContentView(layoutResID);
    }

    /**
     * 载入layoutID
     *
     * @return
     */
    protected abstract int getContentViewLayoutID();

    /**
     * Dagger 注入控制器
     */
    protected abstract void initDaggerInject();

    /**
     * 控件注入
     */
    protected void initButterKnifeBind() {
        bUnbinder = ButterKnife.bind(this);
    }

    /**
     * 初始化视图或者方法
     *
     * @param savedInstanceState
     */
    protected abstract void initViewsAndEvents(@Nullable Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        setUnbinder();
        super.onDestroy();
    }

    private void setUnbinder() {
        if (bUnbinder != null) {
            bUnbinder.unbind();
        }
    }

    //权限
    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    //权限
    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void showProgress(final boolean isShow) {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (isShow) {
                    ProgressDialogHelper.getInstance().showProgressDialog(bContext, "正在加载...");
                } else {
                    ProgressDialogHelper.getInstance().hideProgressDialog();
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
    }

    /**
     * 默认finish
     */
    public void finishDefault() {
        super.finish();
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
        overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out);
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
        overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out);
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
//        ToastUtils.showToast(bContext, text);
        ToastUtils.showNormalToast(bContext,text);
    }

    /**
     * 展示toast弹窗  错误展示
     *
     * @param text
     */
    protected void showToastError(String text) {
//        ToastUtils.showToast(bContext, text);

        ToastUtils.showNormalToast(bContext,text);
    }

    /**
     * 展示toast弹窗  成功展示
     *
     * @param text
     */
    protected void showToastSuccess(String text) {
        ToastUtils.showNormalToast(bContext, text);
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
