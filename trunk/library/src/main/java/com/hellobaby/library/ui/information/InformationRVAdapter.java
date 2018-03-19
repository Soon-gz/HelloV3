package com.hellobaby.library.ui.information;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hellobaby.library.R;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.List;

/**
 * Created by zwj on 2016/12/27.
 * description :
 */

public class InformationRVAdapter extends BaseAdapter<String> {

    private  int imageWidth;
    private int imagePadding;
    public InformationRVAdapter(Context context, List<String> datas) {
        super(context, datas, true);
    }

    @Override
    protected void convert(ViewHolder holder, String data) {
        ImageView imageView = holder.getView(R.id.ItemSchoolRv_iv);
        LinearLayout linearLayout = holder.getView(R.id.ItemSchoolRv_ll);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(imageWidth, imageWidth);
        linearLayout.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
        linearLayout.setLayoutParams(params);
        ImageLoader.loadRoundCenterCropInfomationImg(mContext, data, imageView);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.recycler_item_school_rvitem;
    }



    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }


    public void setImagePadding(int imagePadding) {
        this.imagePadding = imagePadding;
    }
}
