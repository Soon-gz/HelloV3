package com.hellobaby.library.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by zwj on 2016/9/23.
 * description : 登录的基础界面
 */

public class LoginUtils {

    /**
     * 判断按钮是否隐藏
     *
     * @param charSequence   当前的OnTextChanged的字符
     * @param et             另外一个输入框
     * @param viewVisibility 否能展示的控件
     */
    public static void setBtnVisibility(CharSequence charSequence, EditText et, View viewVisibility) {
        setBtnVisibility(charSequence, viewVisibility, et);
    }

    /**
     * 判断按钮是否可以点击
     *
     * @param charSequence   当前的OnTextChanged的字符
     * @param viewVisibility 否能展示的控件
     * @param ets            另外一个输入框
     */
    public static void setBtnVisibility(CharSequence charSequence, View viewVisibility, EditText... ets) {
        boolean isEmpty = false;
        for (EditText et : ets) {
            String etValue = getEditTextString(et);
            if (etValue.isEmpty()) {
                isEmpty = true;
            }
        }
        if (isEmpty || charSequence.toString().trim().isEmpty()) {
            viewVisibility.setEnabled(false);
        } else {
            viewVisibility.setEnabled(true);
        }
        viewVisibility.setVisibility(viewVisibility.isEnabled() ? View.VISIBLE : View.GONE);
    }

    /**
     * 判断编辑的时候，是否已经有输入
     *
     * @param ets 输入框s
     * @return true: 至少一个为有值；false:全为空
     */
    public static boolean isInputEdit(EditText... ets) {
        boolean isInput = false;
        for (EditText et : ets) {
            String etValue = getEditTextString(et);
            if (!etValue.isEmpty()) {
                isInput = true;
            }
        }
        return isInput;
    }
    /**
     * 判断所有输入框是否有一个为空
     *
     * @param ets 输入框s
     * @return true: 至少一个为空；false:都不为空
     */
    public static boolean isEmptyEdit(EditText... ets) {
        boolean isEmpty = false;
        for (EditText et : ets) {
            String etValue = getEditTextString(et);
            if (etValue.isEmpty()) {
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    /**
     * 判断按钮是否可以点击
     *
     * @param charSequence 当前的OnTextChanged的字符
     * @param et           另外一个输入框
     * @param btnEnable    能否点击的按钮
     */
    public static void setBtnEnabled(CharSequence charSequence, EditText et, Button btnEnable) {
        setBtnEnabled(charSequence, btnEnable, et);
    }

    /**
     * 判断按钮是否可以点击
     *
     * @param charSequence 当前的OnTextChanged的字符
     * @param btnEnable    能否点击的按钮
     * @param ets          另外一个输入框
     */
    public static void setBtnEnabled(CharSequence charSequence, Button btnEnable, EditText... ets) {
        boolean isEmpty = false;
        for (EditText et : ets) {
            String etValue = getEditTextString(et);
            if (etValue.isEmpty()) {
                isEmpty = true;
            }
        }
        if (isEmpty || charSequence.toString().trim().isEmpty()) {
            btnEnable.setEnabled(false);
        } else {
            btnEnable.setEnabled(true);
        }
    }

    /**
     * 获取EditText的字符串
     *
     * @param et
     * @return
     */
    private static String getEditTextString(EditText et) {
        return et.getText().toString().trim();
    }
}
