package com.hellobaby.library.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.utils.DownloadHelper;
import com.hellobaby.library.utils.ScreenUtils;
import com.hellobaby.library.widget.custom.ShareBottomDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zwj on 2016/12/6.
 * description :
 */

public class PopupWindowQRCodeForReplace {

    private static PopupWindowQRCodeForReplace instance;
    private PopupWindow popupWindow;

    private PopupWindowQRCodeForReplace() {
    }

    public static PopupWindowQRCodeForReplace getInstance() {
        if (instance == null) {
            instance = new PopupWindowQRCodeForReplace();
        }
        return instance;
    }

    public void showPopup(final Context context, View view, final String content) {
        View layout = LayoutInflater.from(context).inflate(R.layout.libppw_qrcode_replace, null);
        ImageView ivQRCode = (ImageView) layout.findViewById(R.id.ppwQRCode_iv);
        ivQRCode.setImageBitmap(getQRCode(content));
        RelativeLayout rl = (RelativeLayout) layout.findViewById(R.id.ppwQRCode_rl);
//        rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置此参数获得焦点
//        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, ScreenUtils.getStatusHeight(context));
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        ImageView ivWeChat = (ImageView) layout.findViewById(R.id.ppwQRCode_iv_wechat);
        ImageView ivSave = (ImageView) layout.findViewById(R.id.ppwQRCode_iv_save);
        ImageView ivClose = (ImageView) layout.findViewById(R.id.ppwQRCode_close);
        final Bitmap bmp = ((BitmapDrawable) ivQRCode.getDrawable()).getBitmap();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ((Activity) context).finish();
            }
        });
        ivWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShareBottomDialog(context).ShareImage(((Activity) context), bmp, bmp, SHARE_MEDIA.WEIXIN);
            }
        });
        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBitmapFile(context, bmp);
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialogUtils.getBottomExitEditDialog((Activity) context);
            }
        });
    }

    public boolean isShow() {
        return popupWindow.isShowing();
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

    public void saveBitmapFile(Context context, Bitmap bitmap) {
        File temp = new File(Const.saveImagePath);//要保存文件先创建文件夹
        if (!temp.exists()) {
            temp.mkdir();
        }
        String filename = "QRCODE_" + Calendar.getInstance().getTimeInMillis() + ".jpg";
        String path = Const.saveImagePath + File.separator + filename;
        File file = new File(path);//将要保存图片的路径和图片名称
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            ToastUtils.showNormalToast(context, "保存到" + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), filename, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }
}
