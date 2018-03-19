package com.hellobaby.library.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.ScreenUtils;

import java.util.Hashtable;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zwj on 2016/12/6.
 * description :
 */

public class PopupWindowQRCode {

    private static PopupWindowQRCode instance;
    private PopupWindow popupWindow;
    private IZSMainTitleScreenLightListener screenLightListener;

    private PopupWindowQRCode(IZSMainTitleScreenLightListener screenLightListener) {
        this.screenLightListener = screenLightListener;
    }

    public static PopupWindowQRCode getInstance(IZSMainTitleScreenLightListener screenLightListener) {
        if (instance == null) {
            instance = new PopupWindowQRCode(screenLightListener);
        }
        return instance;
    }

    public void showPopup(Context context, View view, String content, BabyModel babyModel, UserModel userModel, final Activity activity) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
        View layout = LayoutInflater.from(context).inflate(R.layout.libppw_qrcode, null);
        ImageView ivQRCode = (ImageView) layout.findViewById(R.id.ppwQRCode_iv);
        CircleImageView ppwQRCode_baby_head = (CircleImageView) layout.findViewById(R.id.ppwQRCode_baby_head);
        CircleImageView ppwQRCode_user_head = (CircleImageView) layout.findViewById(R.id.ppwQRCode_user_head);
        TextView ppwQRCode_baby_name = (TextView) layout.findViewById(R.id.ppwQRCode_baby_name);
        TextView ppwQRCode_user_name = (TextView) layout.findViewById(R.id.ppwQRCode_user_name);
        TextView ppwQRCode_user_relation = (TextView) layout.findViewById(R.id.ppwQRCode_user_relation);
        ppwQRCode_baby_name.setText(babyModel.getBabyName());
        ppwQRCode_user_name.setText(userModel.getUserName());
        ppwQRCode_user_relation.setText(userModel.getRelation());
        ImageLoader.loadHeadTarget(context, Const.URL_BabyHead+babyModel.getHeadImgUrl(),ppwQRCode_baby_head);
        ImageLoader.loadHeadTarget(context, Const.URL_userHead+userModel.getHeadImageurl(),ppwQRCode_user_head);


        ivQRCode.setImageBitmap(getRoundedCornerBitmap(getQRCode(content),25));

        RelativeLayout rl = (RelativeLayout) layout.findViewById(R.id.ppwQRCode_rl);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 21 && activity!= null) {
                    activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.normal_background));
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
                popupWindow.dismiss();
            }
        });


        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (Build.VERSION.SDK_INT >= 21 && activity!= null) {
                    activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.normal_background));
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }

                if (screenLightListener!= null){
                    screenLightListener.dissChangeScreenLisght();
                }
            }
        });
        // 设置此参数获得焦点
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, ScreenUtils.getStatusHeight(context));
    }

    public void dismiss() {
        if (screenLightListener!= null){
            screenLightListener.dissChangeScreenLisght();
        }

    }

    //获取圆角图片
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 将指定的内容生成成二维码,暂时默认一个值
     *
     * @return 返回生成好的二维码
     */
    private Bitmap getQRCode(String content) {
        try {
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); //编码
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 800, 800, hints);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
