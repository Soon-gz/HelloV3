package com.hellobaby.library.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hellobaby.library.R;

/**
 * Created by zwj on 2016/9/23.
 * description :
 */

public class ToastUtils {

    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        @Override
        public void run() {
            mToast.cancel();
        }
    };

    public static void showToast(final Context context, final String text) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                normalToast(context.getApplicationContext(),text);
            }
        });

    }

    public static void showNormalToast(final Context context, final String text) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
//        Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                normalToast(context.getApplicationContext(),text);
            }
        });
//        normalToast(context,text);
    }

    public static void showErrToast(Context context, String text) {
//        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        errorToast(context.getApplicationContext(),text);
    }

    public static void showCorrectToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    private static void normalToast(Context context,String txt){
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_layout_normal,null);
        TextView title = (TextView) layout.findViewById(R.id.toastZhishu_tv);
        title.setText(txt);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    private static void errorToast(Context context,String txt){
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_layout_err,null);
        TextView title = (TextView) layout.findViewById(R.id.toastZhishu_tv);
        title.setText(txt);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    private static void BaseToast(Context context,int type){
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_layout,null);
        TextView title = (TextView) layout.findViewById(R.id.toastZhishu_tv);
        title.setText("我是个普通的默认提示框哦");
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }


    public static void customToast(Context mContext, String text, int type) {
        mHandler.removeCallbacks(r);
        if (mToast != null) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        }

        mHandler.postDelayed(r, 1000);
        mToast.show();
    }


}
