package com.hellobaby.library.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hellobaby.library.R;
import com.hellobaby.library.utils.ScreenUtils;

import java.util.Hashtable;

/**
 * Created by zwj on 2016/12/6.
 * description :
 */

public class PopupWindowQRCode {

    private static PopupWindowQRCode instance;
    private PopupWindow popupWindow;

    private PopupWindowQRCode() {
    }

    public static PopupWindowQRCode getInstance() {
        if (instance == null) {
            instance = new PopupWindowQRCode();
        }
        return instance;
    }

    public void showPopup(Context context, View view, String content) {
        View layout = LayoutInflater.from(context).inflate(R.layout.libppw_qrcode, null);
        ImageView ivQRCode = (ImageView) layout.findViewById(R.id.ppwQRCode_iv);
        ivQRCode.setImageBitmap(getQRCode(content));
        RelativeLayout rl = (RelativeLayout) layout.findViewById(R.id.ppwQRCode_rl);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置此参数获得焦点
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, ScreenUtils.getStatusHeight(context));
    }

    public void dismiss() {
        popupWindow.dismiss();
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
