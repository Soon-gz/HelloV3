package com.hellobaby.library.widget.custom;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.hellobaby.library.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

public class ShareBottomDialog extends BottomBaseDialog<ShareBottomDialog> implements View.OnClickListener {

    private Context context;

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

    public void shareWeixin(Activity activity, String title, String text, UMImage umImage, UMShareListener listener, String url,Boolean is_log) {
        ShareAction action = new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(listener);
        if (umImage != null) {
            action.withMedia(umImage);
        } else {
            action.withTitle(title)
                    .withText(text);
        }
        if (is_log) {
            action.withTitle(title)
                    .withText(text)
                    .withTargetUrl(url);
        }
        action.share();
    }

    public void shareQQ(Activity activity, String title, String text, UMImage umImage, UMShareListener listener,String url,Boolean is_log) {
        ShareAction action = new ShareAction(activity).setPlatform(SHARE_MEDIA.QQ).setCallback(listener);
        if (umImage != null) {
            action.withMedia(umImage);
        } else {
            action.withTitle(title)
                    .withText(text);
        }
        if (is_log) {
            action.withTitle(title)
                    .withText(text)
                    .withTargetUrl(url);
        }
        action.share();
    }

    public void shareWeixinCircle(Activity activity, String title, String text, UMImage umImage, UMShareListener listener,String url, Boolean is_log) {
        ShareAction action = new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(listener);
        if (umImage != null) {
            action.withMedia(umImage);
        } else {
            action.withTitle(title)
                    .withText(text);
        }
        if (is_log) {
            action.withTitle(title)
                    .withText(text)
                    .withTargetUrl(url);
        }
        action.share();
    }

    public void shareWeibo(Activity activity, String title, String text, UMImage umImage, UMShareListener listener) {
        ShareAction action = new ShareAction(activity).setPlatform(SHARE_MEDIA.SINA).setCallback(listener);
        if (umImage != null) {
            action.withMedia(umImage);
        }
        action.withTitle(title)
                .withText(text)
                .share();
    }
    //分享微博，携带链接
    public void shareWeiboWithTarget(Activity activity, String title, String text, UMImage umImage, UMShareListener listener,String target) {
        ShareAction action = new ShareAction(activity).setPlatform(SHARE_MEDIA.SINA).setCallback(listener);
        if (umImage != null) {
            action.withMedia(umImage);
        }
        if (target != null){
            action.withTargetUrl(target);
        }
        action.withTitle(title)
                .withText(text)
                .share();
    }

    public ShareBottomDialog(Context context, View animateView, UMShareListener umShareListener) {
        super(context, animateView);
    }

    public static ShareBottomDialog getSharePicBottomDialog(final Activity activity, View animateView, final String title, final String content, final UMImage ImageUrl) {
        final UMShareListener umShareListener = new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(activity, platform + " 分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享取消", Toast.LENGTH_SHORT).show();
            }
        };
        final ShareBottomDialog dialog = new ShareBottomDialog(activity, animateView, umShareListener);
        dialog.setListner(new ShareBottomDialog.onItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                dialog.dismiss();
                switch (position) {
                    case 0:
                        dialog.shareWeixin(activity, title, content, ImageUrl, umShareListener,"", false);
                        break;
                    case 1:
                        dialog.shareWeixinCircle(activity, title, content, ImageUrl, umShareListener,"", false);
                        break;
                    case 2:
                        dialog.shareQQ(activity, title, content, ImageUrl, umShareListener,"", false);
                        break;
                    case 3:
                        dialog.shareWeibo(activity, title, content, ImageUrl, umShareListener);
                        break;
                    default:
                        break;
                }
            }
        }).show();
        return dialog;
    }

    public static ShareBottomDialog getShareSimplePicBottomDialog(final Activity activity, View animateView,final UMImage ImageUrl) {
        final UMShareListener umShareListener = new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(activity, platform + " 分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享取消", Toast.LENGTH_SHORT).show();
            }
        };
        final ShareBottomDialog dialog = new ShareBottomDialog(activity, animateView, umShareListener);
        dialog.setListner(new ShareBottomDialog.onItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                dialog.dismiss();
                switch (position) {
                    case 0:
                        dialog.shareWeixin(activity, "", "", ImageUrl, umShareListener,"", false);
                        break;
                    case 1:
                        dialog.shareWeixinCircle(activity, "", "", ImageUrl, umShareListener,"", false);
                        break;
                    case 2:
                        dialog.shareQQ(activity, "", "", ImageUrl, umShareListener,"", false);
                        break;
                    case 3:
                        dialog.shareWeibo(activity, "", "来自哈喽宝贝应用", ImageUrl, umShareListener);
                        break;
                    default:
                        break;
                }
            }
        }).show();
        return dialog;
    }

    public static ShareBottomDialog getShareUrlBottomDialog(final Activity activity, View animateView, final String title, final String content, final UMImage ImageUrl, final String url) {
        final UMShareListener umShareListener = new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(activity, platform + " 分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享取消", Toast.LENGTH_SHORT).show();
            }
        };
        final ShareBottomDialog dialog = new ShareBottomDialog(activity, animateView, umShareListener);
        dialog.setListner(new ShareBottomDialog.onItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                dialog.dismiss();
                switch (position) {
                    case 0:
                        dialog.shareWeixin(activity, title, content, ImageUrl, umShareListener,url, true);
                        break;
                    case 1:
                        dialog.shareWeixinCircle(activity, title, content, ImageUrl, umShareListener,url, true);
                        break;
                    case 2:
                        dialog.shareQQ(activity, title, content, ImageUrl, umShareListener,url, true);
                        break;
                    case 3:
                        dialog.shareWeibo(activity, title, content, ImageUrl, umShareListener);
                        break;
                    default:
                        break;
                }
            }
        }).show();
        return dialog;
    }

    //分享微博，携带url
    public static ShareBottomDialog getShareUrlWithTargetBottomDialog(final Activity activity, View animateView, final String title, final String content, final UMImage ImageUrl, final String url, final String target) {
        final UMShareListener umShareListener = new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(activity, platform + " 分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享取消", Toast.LENGTH_SHORT).show();
            }
        };
        final ShareBottomDialog dialog = new ShareBottomDialog(activity, animateView, umShareListener);
        dialog.setListner(new ShareBottomDialog.onItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                dialog.dismiss();
                switch (position) {
                    case 0:
                        dialog.shareWeixin(activity, title, content, ImageUrl, umShareListener,url, true);
                        break;
                    case 1:
                        dialog.shareWeixinCircle(activity, title, content, ImageUrl, umShareListener,url, true);
                        break;
                    case 2:
                        dialog.shareQQ(activity, title, content, ImageUrl, umShareListener,url, true);
                        break;
                    case 3:
                        dialog.shareWeiboWithTarget(activity, title, content, ImageUrl, umShareListener,target);
                        break;
                    default:
                        break;
                }
            }
        }).show();
        return dialog;
    }

    public static ShareBottomDialog getShareTextBottomDialog(final Activity activity, View animateView,final UMImage ImageUrl) {
        final UMShareListener umShareListener = new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(activity, platform + " 分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享取消", Toast.LENGTH_SHORT).show();
            }
        };
        final ShareBottomDialog dialog = new ShareBottomDialog(activity, animateView, umShareListener);
        dialog.setListner(new ShareBottomDialog.onItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                dialog.dismiss();
                switch (position) {
                    case 0:
                        dialog.shareWeixin(activity, "", "", ImageUrl, umShareListener,"", false);
                        break;
                    case 1:
                        dialog.shareWeixinCircle(activity, "", "", ImageUrl, umShareListener,"", false);
                        break;
                    case 2:
                        dialog.shareQQ(activity, "", "", ImageUrl, umShareListener,"", false);
                        break;
                    case 3:
                        dialog.shareWeibo(activity, "", "来自哈喽宝贝应用", ImageUrl, umShareListener);
                        break;
                    default:
                        break;
                }
            }
        }).show();
        return dialog;
    }
}
