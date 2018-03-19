package com.hellobaby.library.widget;

import android.view.View;

/**
 * Created by zwj on 2016/11/4.
 * description : 点击事件接口
 */

public interface IPopupWindowMenuOnClick {
    /**
     * item的点击事件
     * @param view
     * @param position
     */
    public void onItemClick(View view, int position);
}
