package com.hellobaby.library.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zwj on 2016/11/7.
 * description :
 */

public class GaussLayoutUtils {


    public static View setGaussLayout(Context context, final View view, View gaussLayout) {
        if (gaussLayout != null) {
//            gaussLayout.setDrawingCacheEnabled(true);//当前界面的截图
//            gaussLayout.buildDrawingCache();//创建位图
//            Bitmap screen = Bitmap.createBitmap(gaussLayout.getDrawingCache());
//            gaussLayout.setDrawingCacheEnabled(false);//禁用，提高性能
//            screen = scaleBitmap(screen, screen.getWidth() / 2, screen.getHeight() / 2);//压缩bitmap到指定大小


//            Bitmap screen = Bitmap.createBitmap(gaussLayout.getWidth(), gaussLayout.getHeight(),
//                    Bitmap.Config.RGB_565);


            Bitmap screen = ScreenUtils.snapShotWithoutStatusBar((Activity) context);



//            saveMyBitmap(screen, "gauss111");
            //将截屏进行模糊
            screen = Blur.fastblur(context, screen, 20);
            BitmapDrawable bd = new BitmapDrawable(screen);
            view.setBackgroundDrawable(bd);

//            saveMyBitmap(screen, "gauss222");


//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            screen.compress(Bitmap.CompressFormat.PNG, 70, baos);
//            byte[] bytes = baos.toByteArray();
//            Glide.with(context)
//                    .load(bytes)
//                    .bitmapTransform(new BlurTransformation(context))// “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
//                    .into(new SimpleTarget<GlideDrawable>() {
//                        @Override
//                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                            view.setBackgroundDrawable(resource);
//                        }
//                    });
        } else {
            Log.d("ZSLog", "gauss layout == null");
        }
        return view;
    }

    public static void saveMyBitmap(Bitmap mBitmap, String bitName) {
        File f = new File(Environment.getExternalStorageDirectory(), bitName + ".jpg");
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
