package com.hellobaby.library.widget.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zwj on 2016/10/10.
 * description :禁止左右滑动的
 */

public class NoScrollViewPager extends ViewPager {
    private boolean DISABLE=false;
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return DISABLE&&super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return DISABLE&&super.onTouchEvent(arg0);
    }

}
