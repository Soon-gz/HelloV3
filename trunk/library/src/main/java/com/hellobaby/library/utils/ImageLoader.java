package com.hellobaby.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.hellobaby.library.R;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.ui.main.fm.school.SchoolItem;
import com.hellobaby.library.ui.slide.SlideBean;

import java.io.File;

import rx.Subscriber;
import rx.functions.Func1;
import uk.co.senab.photoview.PhotoView;

import static com.davemorrissey.labs.subscaleview.ImageSource.uri;
import static com.hellobaby.library.utils.StringUtils.getImage1080;
import static com.hellobaby.library.utils.StringUtils.getImageSmall;

/**
 *
 */
public class ImageLoader {
    private static int[] holderColors = {R.drawable.holder_color_1, R.drawable.holder_color_2,
            R.drawable.holder_color_3, R.drawable.holder_color_4,
            R.drawable.holder_color_5, R.drawable.holder_color_6,
            R.drawable.holder_color_7, R.drawable.holder_color_8,
            R.drawable.holder_color_9
    };
    private static int holderColorIndex = 0;


    /**
     * 展示原始图片，不下载缩放
     *
     * @param context
     * @param url
     * @param iv
     */
    public static void loadOriginalImg(Context context, String url, ImageView iv) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                .crossFade()
                .into(iv);
    }

    public static void loadImg1080(Context context, String url, ImageView iv) {
        Glide.with(context)
                .load(getImage1080(url))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(iv);
    }

    /**
     * 加载长图显示
     *
     * @param context
     * @param url
     * @param subsamplingScaleImageView
     */
    public static void loadLongImage(Context context, String url, final SubsamplingScaleImageView subsamplingScaleImageView) {
        subsamplingScaleImageView.setVisibility(View.VISIBLE);
        subsamplingScaleImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
        subsamplingScaleImageView.setMinScale(1.0F);//最小显示比例
        subsamplingScaleImageView.setMaxScale(10.0F);//最大显示比例（太大了图片显示会失真，因为一般微博长图的宽度不会太宽）
//            final String testUrl = "http://cache.attach.yuanobao.com/image/2016/10/24/332d6f3e63784695a50b782a38234bb7/da0f06f8358a4c95921c00acfd675b60.jpg";
        //下载图片保存到本地
        Glide.with(context)
                .load(url)
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                        // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
                        subsamplingScaleImageView.setImage(uri(Uri.fromFile(resource)), new ImageViewState(2.0F, new PointF(0, 0), 0));
                    }
                });
    }

    /**
     * 加载长图显示
     *
     * @param context
     * @param url
     */
    public static void loadLongOrCommonImage(final Context context, final String url, final SubsamplingScaleImageView longImageView, final PhotoView commonImageView) {
        rx.Observable.just(url)
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        try {
                            return Glide.with(context)
                                    .load(getImage1080(s))
                                    .asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }).compose(RxThread.<Bitmap>subscribe_Io_Observe_On())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        if ((bitmap.getHeight() / bitmap.getWidth()) > (8 / 3)) {
                            commonImageView.setVisibility(View.GONE);
                            longImageView.setVisibility(View.VISIBLE);
                            longImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
                            longImageView.setMinScale(1.0F);//最小显示比例
                            longImageView.setMaxScale(10.0F);//最大显示比例（太大了图片显示会失真，因为一般微博长图的宽度不会太宽）
                            Glide.with(context)
                                    .load(url)
                                    .downloadOnly(new SimpleTarget<File>() {
                                        @Override
                                        public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                                            // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
                                            longImageView.setImage(uri(Uri.fromFile(resource)), new ImageViewState(2.0F, new PointF(0, 0), 0));
                                        }
                                    });
                        } else {
                            longImageView.setVisibility(View.GONE);
                            commonImageView.setVisibility(View.VISIBLE);
                            commonImageView.setImageBitmap(bitmap);
                        }
                    }
                });

    }

    /**
     * 头像专用 --- 全图展示
     *
     * @param context
     * @param url     全尺寸链接
     * @param iv
     */
    public static void loadHeadOriginal(Context context, String url, ImageView iv) {
        int placeholder = R.drawable.head_holder;
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .placeholder(placeholder)
                .into(iv);
    }


    // 有一个和特殊，需要处理
    public static void load(Context context, String url, ImageView iv) {
        Glide.with(context)
                .load(getImageSmall(url))
                .diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                .crossFade()
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


    /**
     * 加载图片需要色块当底色的  --  缩图展示
     *
     * @param context
     * @param url
     * @param iv
     */
    public static void loadImg(Context context, String url, ImageView iv) {
        int placeholder = holderColors[holderColorIndex % holderColors.length];
        holderColorIndex++;
        baseLoad(context, getImageSmall(url), iv, placeholder);
    }

    /**
     * 头像专用 --- 缩图展示
     *
     * @param context
     * @param url     全尺寸链接
     * @param iv
     */
    public static void loadHead(Context context, String url, ImageView iv) {
        int placeholder = R.drawable.head_holder;
        Glide.with(context)
                .load(getImageSmall(url))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .placeholder(placeholder)
                .into(iv);
    }

    /**
     * 头像专用，对于某些第一次不会显示的头像   --- 缩图展示
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
                .load(getImageSmall(url))
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholder)
                .error(placeholder)
                .into(target);
    }

    public static void load(Context context, int resId, ImageView iv) {
        Glide.with(context)
                .load(resId)
                .crossFade()
                .into(iv);
    }

    /**
     * 需要在子线程执行   --- 缩图展示
     *
     * @param context
     * @param url
     * @return
     */
    public static Bitmap load(Context context, String url) {
        try {
            return Glide.with(context)
                    .load(getImageSmall(url))
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap loadResizeH4000(Context context, String url) {
        return baseLoadResizeH(context, url);
    }

    /**
     * 需要在子线程执行   --- 缩图展示
     *
     * @param context
     * @param url
     * @return
     */
    private static Bitmap baseLoadResizeH(Context context, String url) {
        try {
            return Glide.with(context)
                    .load(StringUtils.getImageResize_H3000(url))
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap loadResizeW400CropH(Context context, String url,int crop_h) {
        return baseLoadResizeWCropH(context, url, 400, crop_h);
    }

    private static Bitmap baseLoadResizeWCropH(Context context, String url, int resize_h, int crop_h) {
        try {
            return Glide.with(context)
                    .load(StringUtils.getImageResizeCrop(url, resize_h, crop_h))
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
     * 圆角图 全图展示   --- 缩图展示
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadRound(final Context context, String url, final ImageView imageView) {
        int placeholder = holderColors[holderColorIndex % holderColors.length];
        holderColorIndex++;
        Glide.with(context)
                .load(getImageSmall(url))
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

    public static void loadRoundResize400CropH(final Context context, String url, final ImageView imageView,int cropH) {
        int placeholder = holderColors[holderColorIndex % holderColors.length];
        holderColorIndex++;
        Glide.with(context)
                .load(StringUtils.getImageResize400CropH(url,cropH))
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
     * 圆角图,显示中间  --- 只是展示本地的
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
     * 圆角图,显示中间  带占位符的  ---展示缩图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadRoundCenterCropImg(final Context context, String url, final ImageView imageView) {
        int placeholder = holderColors[holderColorIndex % holderColors.length];
        holderColorIndex++;
        Glide.with(context).load(getImageSmall(url)).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(placeholder).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                roundedBitmapDrawable.setCornerRadius(ScreenUtils.dip2px(context, 8)); //设置圆角半径（根据实际需求）
                roundedBitmapDrawable.setAntiAlias(true); //设置反走样
                imageView.setImageDrawable(roundedBitmapDrawable); //显示圆角图片
            }
        });
    }
    public static void loadRoundCenterCropInfomationImg(final Context context, String url, final ImageView imageView) {
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
    public static void loadRoundCenterCropClassAssistantImg(final Context context, String url, final ImageView imageView) {
        loadRoundCenterCropInfomationImg(context,StringUtils.getImageSmall(url),imageView);
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
