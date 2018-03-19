package com.abings.baby.ui.album;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abings.baby.R;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.hellobaby.library.ui.slide.PagerAdapterItemClick;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.LogZS;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoPagerAdapter extends PagerAdapter {

    private List<WaterFallItem> paths = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private PhotoViewAttacher mAttacher;
    private PagerAdapterItemClick mItemClick;

    public PhotoPagerAdapter(Context mContext, List<WaterFallItem> paths) {
        this.mContext = mContext;
        this.paths = paths;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        WaterFallItem waterFallItem = paths.get(position);
        final String url = paths.get(position).getUrl();
        View itemView;
        if (waterFallItem.getHeight() / waterFallItem.getWidth() > (8 / 3)) {
            itemView = mLayoutInflater.inflate(R.layout.item_preview_ssi, container, false);
            final SubsamplingScaleImageView longImageView = (SubsamplingScaleImageView) itemView.findViewById(R.id.iv_subsamplingScaleImageView);
            ImageLoader.loadLongImage(mContext,waterFallItem.getUrl(),longImageView);
            longImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClick.onItemClick();
                }
            });
        }else {
            itemView = mLayoutInflater.inflate(R.layout.item_preview, container, false);
            final PhotoView imageView = (PhotoView) itemView.findViewById(R.id.iv_pager);
            LogZS.i("第"+position+"张图片："+url);
            ImageLoader.loadImg1080(mContext, url, imageView);
            mAttacher = new PhotoViewAttacher(imageView);
            mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    mItemClick.onItemClick();
                }
            });
        }
        container.addView(itemView);
        return itemView;
    }


    @Override
    public int getCount() {
        return paths.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setOnItemClick(PagerAdapterItemClick itemClick) {
        mItemClick = itemClick;
    }
}