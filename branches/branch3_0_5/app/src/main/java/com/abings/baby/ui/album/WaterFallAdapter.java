package com.abings.baby.ui.album;

import android.content.Context;

import com.abings.baby.R;
import com.hellobaby.library.utils.ImageLoader;
import com.abings.baby.widget.baseadapter.BaseAdapter;
import com.abings.baby.widget.baseadapter.ViewHolder;

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
        ImageLoader.loadRound(mContext, data.getUrl(), imageView);
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
