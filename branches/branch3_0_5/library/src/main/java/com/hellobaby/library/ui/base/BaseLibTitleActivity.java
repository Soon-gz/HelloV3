package com.hellobaby.library.ui.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hellobaby.library.R;

/**
 * Created by zwj on 2016/11/22.
 * description :
 */

public abstract class BaseLibTitleActivity<T>  extends BaseLibActivity<T> {

    /**
     * 含标题的root的布局
     */
    protected LinearLayout bLlRoot;
    /**
     * 标题布局
     */
    protected RelativeLayout bTitleBaseRL;
    /**
     * 左边按钮
     */
    protected ImageView bIvLeft;
    /**
     * 右边按钮
     */
    protected ImageView bIvRight;
    /**
     * 内容
     */
    protected FrameLayout bFrameLayout;
    /**
     * 中间标题
     */
    protected TextView bTvTitle;
    @Override
    protected void setContentLayout(int layoutResID) {
        setContentView(R.layout.libactivity_base_title);
        bLlRoot = (LinearLayout) findViewById(R.id.libTitle_ll_root);
        bTitleBaseRL = (RelativeLayout) findViewById(R.id.libTitle_RL);
        bIvLeft = (ImageView) findViewById(R.id.libTitle_iv_left);
        bTvTitle = (TextView) findViewById(R.id.libTitle_tv_title);
        bIvRight = (ImageView) findViewById(R.id.libTitle_iv_right);
        bFrameLayout = (FrameLayout) findViewById(R.id.libTitle_frame_layout);
        View contentView = View.inflate(bContext, layoutResID, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(layoutParams);
//        contentView.setBackgroundColor(Color.WHITE);
        bFrameLayout.addView(contentView);
        initClick();
    }

    protected void initClick() {
        bIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRightOnClick(v);
            }
        });
    }

    protected void btnRightOnClick(View v) {
    }

    protected ImageView setBtnLeftDrawable(Drawable drawable) {
        return setBtnDrawable(bIvLeft, drawable);
    }

    protected ImageView setBtnLeftDrawableRes(@DrawableRes int resid) {
        return setBtnDrawableRes(bIvLeft, resid);
    }

    protected ImageView setBtnRightDrawable(Drawable drawable) {
        bIvRight.setVisibility(View.VISIBLE);
        return setBtnDrawable(bIvRight, drawable);
    }

    protected ImageView setBtnRightDrawableRes(@DrawableRes int resid) {
        bIvRight.setVisibility(View.VISIBLE);
        return setBtnDrawableRes(bIvRight, resid);
    }

    protected void setTitleBackground(int color) {
        bTitleBaseRL.setBackgroundColor(getResources().getColor(color));
    }

    private ImageView setBtnDrawable(ImageView btn, Drawable drawable) {
//        btn.setBackgroundDrawable(drawable);
        btn.setImageDrawable(drawable);
        return btn;
    }

    private ImageView setBtnDrawableRes(ImageView btn, @DrawableRes int resid) {
//        btn.setBackgroundResource(resid);
        btn.setImageResource(resid);
        return btn;
    }

    public TextView setTitleText(String title) {
        bTvTitle.setText(title);
        return bTvTitle;
    }

    public TextView setTitleRes(@StringRes int resid) {
        bTvTitle.setText(resid);
        return bTvTitle;
    }

    public void setBtnLeftClickFinish() {
        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void finish() {
        hintKb();
        super.finish();
    }


    // 此方法只是关闭软键盘
    protected void hintKb() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
