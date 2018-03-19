package com.hellobaby.library.ui.slide;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.hellobaby.library.R;
import com.hellobaby.library.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class SlideAdapter extends PagerAdapter {

    private List<SlideBean> slideBeans = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private PhotoViewAttacher mAttacher;
    private PagerAdapterItemClick mItemClick;


    public SlideAdapter(Context mContext, List<SlideBean> paths) {
        this.mContext = mContext;
        this.slideBeans = paths;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView;
        SlideBean slideBean = slideBeans.get(position);
        if(slideBean.isLongImage()){
            itemView = mLayoutInflater.inflate(R.layout.item_slide_ssi, container, false);
            container.addView(itemView);
            SubsamplingScaleImageView longImageView = (SubsamplingScaleImageView) itemView.findViewById(R.id.itemSlide_subsamplingScaleImageView);
            longImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClick.onItemClick();
                }
            });
            ImageLoader.loadLongImage(mContext,slideBean.getUrl(),longImageView);
        }else {
            itemView = mLayoutInflater.inflate(R.layout.item_slide, container, false);
            PhotoView photoView = (PhotoView) itemView.findViewById(R.id.itemSlide_pager);
            container.addView(itemView);
            mAttacher = new PhotoViewAttacher(photoView);
            mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    mItemClick.onItemClick();
                }
            });
            ImageLoader.loadImg1080(mContext,slideBean.getUrl(),photoView);
        }
        return itemView;
    }


    @Override
    public int getCount() {
        return slideBeans.size();
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