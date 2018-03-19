package com.abings.baby.ui.album;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.util.Log;

import com.hellobaby.library.utils.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/18 11:48
 */
public class DataService extends IntentService {

    public DataService() {
        super("");
    }

    public static void startService(Context context, List<WaterFallItem> datas) {
        Intent intent = new Intent(context, DataService.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) datas);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        List<WaterFallItem> datas = intent.getParcelableArrayListExtra("data");
        handleGirlItemData(datas);
    }

    private void handleGirlItemData(List<WaterFallItem> datas) {
        if (datas.size() == 0) {
            EventBus.getDefault().post("finish");
            return;
        }
        for (WaterFallItem data : datas) {
            Bitmap bitmap = ImageLoader.load(this, data.getUrl());
            if (bitmap != null && (bitmap.getHeight() / bitmap.getWidth() < 8)) {
                data.setWidth(bitmap.getWidth());
                data.setHeight(bitmap.getHeight());
            } else {
                data.setTypeLongImage();
                int cropH = data.getLongImageHeight(bitmap.getWidth());
                Bitmap bitmap2 = ImageLoader.loadResizeW400CropH(this, data.getUrl(), cropH);
                if (null != bitmap2) {
                    data.setHeight(bitmap2.getHeight());
                    data.setWidth(bitmap2.getWidth());
                }
            }
        }
        EventBus.getDefault().post(datas);
    }


}
