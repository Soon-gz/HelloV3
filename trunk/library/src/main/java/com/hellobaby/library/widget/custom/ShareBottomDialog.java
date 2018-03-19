package com.hellobaby.library.widget.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.hellobaby.library.R;
import com.hellobaby.library.widget.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;

public class ShareBottomDialog extends BottomBaseDialog<ShareBottomDialog> implements View.OnClickListener {


    UMShareListener umShareListener;

    public ShareBottomDialog(Context context, View animateView) {
        super(context, animateView);
    }

    public ShareBottomDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
//        showAnim(new FlipVerticalSwingEnter());
        dismissAnim(null);
        View inflate = View.inflate(mContext, R.layout.popup_slide_from_bottom, null);
        inflate.findViewById(R.id.weixin).setOnClickListener(this);
        inflate.findViewById(R.id.pyq).setOnClickListener(this);
        inflate.findViewById(R.id.qq).setOnClickListener(this);
        inflate.findViewById(R.id.weibo).setOnClickListener(this);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
    }

    @Override
    public void onClick(View v) {
        if (listner == null) {
            return;
        }
        int i = v.getId();
        if (i == R.id.weixin) {
            listner.onItemClick(0);

        } else if (i == R.id.pyq) {
            listner.onItemClick(1);

        } else if (i == R.id.qq) {
            listner.onItemClick(2);

        } else if (i == R.id.weibo) {
            listner.onItemClick(3);

        } else {
        }
    }

    public onItemClickLitener getListner() {
        return listner;
    }

    public ShareBottomDialog setListner(onItemClickLitener listner) {
        this.listner = listner;
        return this;
    }

    private onItemClickLitener listner;

    public interface onItemClickLitener {
        void onItemClick(int position);
    }

    private void ShareVideo(final Activity activity, String thumb_img, SHARE_MEDIA share_media, String url, String desc, String title) {
        UMImage thumb = new UMImage(activity, thumb_img);
        UMVideo video = new UMVideo(url);
        video.setThumb(thumb);
        video.setDescription(desc);
        video.setTitle(title);
        new ShareAction(activity).withMedia(video).setPlatform(share_media).setCallback(newUmShareL()).share();
    }

    private void ShareText(final Activity activity, String text, SHARE_MEDIA share_media) {
        new ShareAction(activity).withText(text)
                .setPlatform(share_media)
                .setCallback(newUmShareL()).share();
    }

    private void ShareImage(final Activity activity, String image, String thumb, SHARE_MEDIA share_media) {
        UMImage pic = new UMImage(activity, image);
        pic.setThumb(new UMImage(activity, thumb));
        new ShareAction(activity).withMedia(pic).setPlatform(share_media).setCallback(newUmShareL()).withText("文本内容").share();
    }

    public void ShareImage(final Activity activity, Bitmap image, Bitmap thumb, SHARE_MEDIA share_media) {
        UMImage pic = new UMImage(activity, image);
        pic.setThumb(new UMImage(activity, thumb));
        new ShareAction(activity).withMedia(pic).setPlatform(share_media).setCallback(newUmShareL()).withText("文本内容").share();
    }

    public void ShareImagePrize(final Activity activity, Bitmap image, Bitmap thumb, SHARE_MEDIA share_media,Class toClass) {
        UMImage pic = new UMImage(activity, image);
        pic.setThumb(new UMImage(activity, thumb));
        new ShareAction(activity).withMedia(pic).setPlatform(share_media).setCallback(newUmShareLPrize(activity,toClass)).withText("文本内容").share();
    }

    private void ShareWeb(final Activity activity, String thumb_img, SHARE_MEDIA share_media, String url, String desc, String title) {
        UMImage thumb = new UMImage(activity, thumb_img);
        UMWeb web = new UMWeb(url);
        web.setThumb(thumb);
        web.setDescription(desc);
        web.setTitle(title);
        new ShareAction(activity).withMedia(web).setPlatform(share_media).setCallback(newUmShareL()).share();
    }

    private void ShareWeb(final Activity activity, int thumb_img, SHARE_MEDIA share_media, String url, String desc, String title) {
        UMImage thumb = new UMImage(activity, thumb_img);
        UMWeb web = new UMWeb(url);
        web.setThumb(thumb);
        web.setDescription(desc);
        web.setTitle(title);
        new ShareAction(activity).withMedia(web).setPlatform(share_media).setCallback(newUmShareL()).share();
    }

    public static ShareBottomDialog getShareVideoBottomDialog(final Activity activity, final String imageUrl, final String videoUrl, final String desc, final String title) {
        final ShareBottomDialog dialog = new ShareBottomDialog(activity);
        dialog.setListner(new ShareBottomDialog.onItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                dialog.dismiss();
                switch (position) {
                    case 0:
                        dialog.ShareVideo(activity, imageUrl, SHARE_MEDIA.WEIXIN, videoUrl, desc, title);
                        break;
                    case 1:
                        dialog.ShareVideo(activity, imageUrl, SHARE_MEDIA.WEIXIN_CIRCLE, videoUrl, desc, title);
                        break;
                    case 2:
                        dialog.ShareVideo(activity, imageUrl, SHARE_MEDIA.QQ, videoUrl, desc, title);
                        break;
                    case 3:
                        dialog.ShareVideo(activity, imageUrl, SHARE_MEDIA.SINA, videoUrl, desc, title);
                        break;
                    default:
                        break;
                }
            }
        }).show();
        return dialog;
    }

    public static ShareBottomDialog getShareUrlBottomDialog(final Activity activity, final int imageUrl, final String videoUrl, final String desc, final String title) {
        final ShareBottomDialog dialog = new ShareBottomDialog(activity);
        dialog.setListner(new ShareBottomDialog.onItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                dialog.dismiss();
                switch (position) {
                    case 0:
                        dialog.ShareWeb(activity, imageUrl, SHARE_MEDIA.WEIXIN, videoUrl, desc, title);
                        break;
                    case 1:
                        dialog.ShareWeb(activity, imageUrl, SHARE_MEDIA.WEIXIN_CIRCLE, videoUrl, desc, title);
                        break;
                    case 2:
                        dialog.ShareWeb(activity, imageUrl, SHARE_MEDIA.QQ, videoUrl, desc, title);
                        break;
                    case 3:
                        dialog.ShareWeb(activity, imageUrl, SHARE_MEDIA.SINA, videoUrl, desc, title);
                        break;
                    default:
                        break;
                }
            }
        }).show();
        return dialog;
    }

    public static ShareBottomDialog getShareUrlBottomDialog(final Activity activity, final String imageUrl, final String videoUrl, final String desc, final String title) {
        final ShareBottomDialog dialog = new ShareBottomDialog(activity);
        dialog.setListner(new ShareBottomDialog.onItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                dialog.dismiss();
                switch (position) {
                    case 0:
                        dialog.ShareWeb(activity, imageUrl, SHARE_MEDIA.WEIXIN, videoUrl, desc, title);
                        break;
                    case 1:
                        dialog.ShareWeb(activity, imageUrl, SHARE_MEDIA.WEIXIN_CIRCLE, videoUrl, desc, title);
                        break;
                    case 2:
                        dialog.ShareWeb(activity, imageUrl, SHARE_MEDIA.QQ, videoUrl, desc, title);
                        break;
                    case 3:
                        dialog.ShareWeb(activity, imageUrl, SHARE_MEDIA.SINA, videoUrl, desc, title);
                        break;
                    default:
                        break;
                }
            }
        }).show();
        return dialog;
    }

    public static ShareBottomDialog getSharePicBottomDialog(final Activity activity, final String imageUrl) {
        final ShareBottomDialog dialog = new ShareBottomDialog(activity);
        dialog.setListner(new ShareBottomDialog.onItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                dialog.dismiss();
                switch (position) {
                    case 0:
                        dialog.ShareImage(activity, imageUrl, imageUrl, SHARE_MEDIA.WEIXIN);
                        break;
                    case 1:
                        dialog.ShareImage(activity, imageUrl, imageUrl, SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                    case 2:
                        dialog.ShareImage(activity, imageUrl, imageUrl, SHARE_MEDIA.QQ);
                        break;
                    case 3:
                        dialog.ShareImage(activity, imageUrl, imageUrl, SHARE_MEDIA.SINA);
                        break;
                    default:
                        break;
                }
            }
        }).show();
        return dialog;
    }

    public static ShareBottomDialog getSharePicBottomDialog(final Activity activity, final Bitmap imageUrl) {
        final ShareBottomDialog dialog = new ShareBottomDialog(activity);
        dialog.setListner(new ShareBottomDialog.onItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                dialog.dismiss();
                switch (position) {
                    case 0:
                        dialog.ShareImage(activity, imageUrl, imageUrl, SHARE_MEDIA.WEIXIN);
                        break;
                    case 1:
                        dialog.ShareImage(activity, imageUrl, imageUrl, SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                    case 2:
                        dialog.ShareImage(activity, imageUrl, imageUrl, SHARE_MEDIA.QQ);
                        break;
                    case 3:
                        dialog.ShareImage(activity, imageUrl, imageUrl, SHARE_MEDIA.SINA);
                        break;
                    default:
                        break;
                }
            }
        }).show();
        return dialog;
    }

    public static ShareBottomDialog getSharePicBottomDialogPrize(final Activity activity, final Bitmap imageUrl, final Class toClass) {
        final ShareBottomDialog dialog = new ShareBottomDialog(activity);
        dialog.setListner(new ShareBottomDialog.onItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                dialog.dismiss();
                switch (position) {
                    case 0:
                        dialog.ShareImagePrize(activity, imageUrl, imageUrl, SHARE_MEDIA.WEIXIN,toClass);
                        break;
                    case 1:
                        dialog.ShareImagePrize(activity, imageUrl, imageUrl, SHARE_MEDIA.WEIXIN_CIRCLE,toClass);
                        break;
                    case 2:
                        dialog.ShareImagePrize(activity, imageUrl, imageUrl, SHARE_MEDIA.QQ,toClass);
                        break;
                    case 3:
                        dialog.ShareImagePrize(activity, imageUrl, imageUrl, SHARE_MEDIA.SINA,toClass);
                        break;
                    default:
                        break;
                }
            }
        }).show();
        return dialog;
    }

private UMShareListener newUmShareL(){
   return umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showNormalToast(mContext, "分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            t.printStackTrace();
            ToastUtils.showErrToast(mContext, "分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showNormalToast(mContext, "分享取消");
        }
    };
}

private UMShareListener newUmShareLPrize(final Activity activity, final Class toActivity){
   return umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showNormalToast(activity,"领取成功，您的订单已开始处理~");
            activity.finish();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            ToastUtils.showErrToast(mContext, "分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            ToastUtils.showNormalToast(mContext, "分享取消");
        }
    };
    }
}
