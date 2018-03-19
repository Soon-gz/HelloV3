package com.hellobaby.timecard.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.timecard.R;
import com.hellobaby.timecard.data.injection.DaggerUtils;
import com.hellobaby.timecard.data.injection.component.ActivityComponent;
import com.hellobaby.timecard.widget.DialogUtils;


/**
 * Created by zwj on 2016/11/28.
 * description :
 *
 * @param <T>
 */
public abstract class BaseActivity<T> extends BaseLibActivity<T> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏，就是下面三个虚拟按钮
        super.onCreate(savedInstanceState);
    }

    /**
     * @return
     */
    @Override
    public ActivityComponent getActivityComponent() {
        return DaggerUtils.getActivityComponent(bActivityComponent, this);
    }


    @Override
    protected void showToast(String text) {
        normalToast(text, true);
    }

    @Override
    protected void showToastError(String text) {
        normalToast(text, false);
    }

    public void toastBig(String txt , boolean isError){
        LayoutInflater inflater = LayoutInflater.from(BaseActivity.this.getApplicationContext());
        View layout = inflater.inflate(R.layout.tcm_toast_layout_normal_copy, null);
        TextView tv = (TextView) layout.findViewById(R.id.tcmTishi_tv);
        tv.setText(txt);
        if (isError){
            layout.setBackgroundResource(R.drawable.toast_shadow_red);
        }else {
            layout.setBackgroundResource(R.drawable.toast_shadow_green);
        }
        Toast toast = new Toast(BaseActivity.this.getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void normalToast(String txt, boolean isSuccess) {
        LayoutInflater inflater = LayoutInflater.from(BaseActivity.this.getApplicationContext());
        View layout = inflater.inflate(R.layout.tcm_toast_layout_normal, null);
        TextView tv = (TextView) layout.findViewById(R.id.tcmTishi_tv);
        ImageView iv = (ImageView) layout.findViewById(R.id.tcmTishi_iv);
        tv.setText(txt);
        if (isSuccess) {
            iv.setImageResource(R.drawable.tcm_tishi_success);
        } else {
            iv.setImageResource(R.drawable.tcm_tishi_error);
        }
        Toast toast = new Toast(BaseActivity.this.getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    protected void showTCMDialog(String text,final View.OnClickListener onClickListener) {
        DialogUtils.showTCMDialog(bContext,text,onClickListener);
    }
}
