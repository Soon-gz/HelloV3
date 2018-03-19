package com.hellobaby.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;

/**
 * Created by zwj on 2016/11/7.
 * description :
 */

public class GaussLayoutUtils {


    public static View setGaussLayout(Context context, View view, View gaussLayout) {
        if (gaussLayout != null) {
            gaussLayout.setDrawingCacheEnabled(true);//当前界面的截图
            gaussLayout.buildDrawingCache();//创建位图
            Bitmap screen = Bitmap.createBitmap(gaussLayout.getDrawingCache());
            gaussLayout.setDrawingCacheEnabled(false);//禁用，提高性能
            screen = scaleBitmap(screen, screen.getWidth() / 2, screen.getHeight() / 2);//压缩bitmap到指定大小
            //将截屏进行模糊
            screen = Blur.fastblur(context, screen, 10);
            BitmapDrawable bd = new BitmapDrawable(screen);
            view.setBackgroundDrawable(bd);
        }else{
            Log.d("ZSLog","gauss layout == null");
        }
        return view;
    }

    /**
     * 把一个bitmap压缩，压缩到指定大小
     *
     * @param bm
     * @param width
     * @param height
     * @return
     */
    private static Bitmap scaleBitmap(Bitmap bm, float width, float height) {
        if (bm == null) {
            return null;
        }
        int bmWidth = bm.getWidth();
        int bmHeight = bm.getHeight();
        float scaleWidth = width / bmWidth;
        float scaleHeight = height / bmHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        if (scaleWidth == 1 && scaleHeight == 1) {
            return bm;
        } else {
            Bitmap resizeBitmap = Bitmap.createBitmap(bm, 0, 0, bmWidth,
                    bmHeight, matrix, false);
            bm.recycle();//回收图片内存
            bm.setDensity(240);
            return resizeBitmap;
        }
    }
}
