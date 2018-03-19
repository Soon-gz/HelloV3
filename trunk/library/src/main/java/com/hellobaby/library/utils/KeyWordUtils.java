package com.hellobaby.library.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/1/20.
 */

public class KeyWordUtils {
    /**
     * 关键字高亮变色
     *
     * @param text    文字
     * @param keyword 文字中的关键字
     * @return
     */
    public static SpannableString matcherSearchTitle(String text,
                                                     String keyword) {
        SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(Color.GREEN), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    /**
     * 多个关键字高亮变色
     *
     * @param color   变化的色值
     * @param text    文字
     * @param keyword 文字中的关键字数组
     * @return
     */
    public static SpannableString matcherSearchTitle(int color, String text,
                                                     String[] keyword) {
        SpannableString s = new SpannableString(text);
        for (int i = 0; i < keyword.length; i++) {
            Pattern p = Pattern.compile(keyword[i]);
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }

    /**
     * 电话字体高亮
     *
     * @param text    电话
     * @return
     */
    public static SpannableString matcherSearchPhone(final String text,
                                                     final Context context) {
        final String PHONE_PATTERN = "((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}";
        final SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(PHONE_PATTERN);
        final Matcher m = p.matcher(s);
        while (m.find()) {
            final int start = m.start();
            final int end = m.end();
            s.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" +text.substring(start,end));
                    intent.setData(data);
                    context.startActivity(intent);
                    return;
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
//                    ds.setTextSize(35);//设置字体大小
                    ds.setFakeBoldText(true);//设置粗体
                    ds.setColor(Color.argb(255,38,157,241));//设置字体颜色
                    ds.setUnderlineText(false);//设置取消下划线
                }
            },start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }
}
