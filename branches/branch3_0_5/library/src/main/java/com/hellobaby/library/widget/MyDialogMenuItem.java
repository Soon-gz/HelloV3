package com.hellobaby.library.widget;

import com.flyco.dialog.entity.DialogMenuItem;

/**
 * Created by Administrator on 2016/12/8.
 */

public class MyDialogMenuItem extends DialogMenuItem {
    int colorId;

    public MyDialogMenuItem(String operName, int resId, int colorId) {
        super(operName, resId);
        this.colorId = colorId;
    }
}
