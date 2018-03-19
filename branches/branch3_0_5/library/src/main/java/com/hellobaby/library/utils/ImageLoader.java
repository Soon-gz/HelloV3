package com.hellobaby.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.hellobaby.library.R;

/**
 *
 */
public class ImageLoader {
    public static void load(Context context, String url, ImageView iv) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                .crossFade()
                .into(iv);
    }

    public static void load(Context context, String url, ImageView iv, int placeholder) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                .crossFade()
                .placeholder(placeholder)
                .into(iv);
    }

    /**
     * 基类
     *
     * @param context
     * @param url
     * @param iv
     * @param placeholder
     */
    private static void baseLoad(Context context, String url, ImageView iv, int placeholder) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                .crossFade()
                .placeholder(placeholder)
                .into(iv);
    }

    private static int[] holderColors = {R.drawable.holder_color_1, R.drawable.holder_color_2,
            R.drawable.holder_color_3, R.drawable.holder_color_4,
            R.drawable.holder_color_5, R.drawable.holder_color_6,
            R.drawable.holder_color_7, R.drawable.holder_color_8,
            R.drawable.holder_color_9
    };
    private static int holderColorIndex = 0;

    /**
     * 加载图片需要色块当底色的
     *
     * @param context
     * @param url
     * @param iv
     */
    public static void loadImg(Context context, String url, ImageView iv) {
        int placeholder = holderColors[holderColorIndex % holderColors.length];
        holderColorIndex++;
        baseLoad(context, url, iv, placeholder);
    }

    /**
     * 头像专用
     *
     * @param context
     * @param url
     * @param iv
     */
    public static void loadHead(Context context, String url, ImageView iv) {
        int placeholder = R.drawable.head_holder;
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .placeholder(placeholder)
                .into(iv);
    }

    /**
     * 头像专用，对于某些第一次不会显示的头像
     *
     * @param context
     * @param url
     * @param iv
     */
    public static void loadHeadTarget(Context context, String url, final ImageView iv) {
        int placeholder = R.drawable.head_holder;
        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                iv.setImageBitmap(resource);
            }
        };
        Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholder)
                .into(target);
    }

    public static void load(Context context, int resId, ImageView iv) {
        Glide.with(context)
                .load(resId)
                .crossFade()
                .into(iv);
    }

    /**
     * 需要在子线程执行
     *
     * @param context
     * @param url
     * @return
     */
    public static Bitmap load(Context context, String url) {
        try {
            return Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 圆角图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadRound(final Context context, String url, final ImageView imageView) {
        int placeholder = holderColors[holderColorIndex % holderColors.length];
        holderColorIndex++;
        Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholder)
                .into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                roundedBitmapDrawable.setCornerRadius(ScreenUtils.dip2px(context, 8)); //设置圆角半径（根据实际需求）
                roundedBitmapDrawable.setAntiAlias(true); //设置反走样
                imageView.setImageDrawable(roundedBitmapDrawable); //显示圆角图片
            }
        });
    }

    /**
     * 圆角图,显示中间
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadRoundCenterCrop(final Context context, String url, final ImageView imageView) {
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                roundedBitmapDrawable.setCornerRadius(ScreenUtils.dip2px(context, 8)); //设置圆角半径（根据实际需求）
                roundedBitmapDrawable.setAntiAlias(true); //设置反走样
                imageView.setImageDrawable(roundedBitmapDrawable); //显示圆角图片
            }
        });
    }


    /**
     * 圆角图,显示中间
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadRoundCenterCropImg(final Context context, String url, final ImageView imageView) {
        int placeholder = holderColors[holderColorIndex % holderColors.length];
        holderColorIndex++;
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(placeholder).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                roundedBitmapDrawable.setCornerRadius(ScreenUtils.dip2px(context, 8)); //设置圆角半径（根据实际需求）
                roundedBitmapDrawable.setAntiAlias(true); //设置反走样
                imageView.setImageDrawable(roundedBitmapDrawable); //显示圆角图片
            }
        });
    }
    /**
     * 圆形图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCircle(final Context context, String url, final ImageView imageView) {
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
}
