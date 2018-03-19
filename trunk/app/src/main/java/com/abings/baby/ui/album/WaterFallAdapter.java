package com.abings.baby.ui.album;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.abings.baby.R;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.io.File;
import java.util.List;


/**
 * Created by zwj on 2016/10/28.
 * description :
 */

public class WaterFallAdapter extends BaseAdapter<WaterFallItem> {
    public WaterFallAdapter(Context context, List<WaterFallItem> datas) {
        super(context, datas, false);
    }

    @Override
    protected void convert(ViewHolder holder, WaterFallItem data) {
        final ScaleImageView imageView = holder.getView(R.id.waterFall_iv);
        imageView.setInitSize(data.getWidth(), data.getHeight());
        if (data.getId() == null || data.getId().equals(String.valueOf(Integer.MAX_VALUE))) {
            if (data.isTypeLongImage()) {
                File file = new File(data.getUrl());
                if (file.exists()) {
                    try {
                        Bitmap bmp = Bitmap.createBitmap(BitmapFactory.decodeFile(data.getUrl()), 0, 0, data.getWidth(), data.getHeight(), null, false);
                        imageView.setImageBitmap(bmp);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            } else {
                ImageLoader.loadOriginalImg(mContext, data.getUrl(), imageView);
            }
        } else {
            if (data.isTypeLongImage()) {
                ImageLoader.loadRoundResize400CropH(mContext, data.getUrl(), imageView, data.getLongImageHeight());
            } else {
                ImageLoader.loadRound(mContext, data.getUrl(), imageView);
            }
        }
        imageView.setSelected(data.isSelected());
    }


    @Override
    protected int getItemLayoutId() {
        return R.layout.item_water_fall;
    }

    public List<WaterFallItem> getDatas() {
        return mDatas;
    }
}
