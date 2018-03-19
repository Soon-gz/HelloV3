package com.hellobaby.library.ui.slide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;

import java.util.List;


/**
 * Created by zwj on 2016/12/23.
 * description : 幻灯片，提供图片左右滑动查看
 */

public class SlideActivity extends BaseLibTitleActivity {


    public static final String kDatas = "List<SlideBean>";
    public static final String kCurrentPosition = "position";

    ProgressBar mProgressBar;//album_detail_loading
    PhotoViewPager mViewPager;//album_detail_viewpager
    private int mCurrentPosition = 0;
    private List<SlideBean> mData;
    private SlideAdapter mAdapter;
    private int initDataSize;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_slide;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        mViewPager = (PhotoViewPager) findViewById(R.id.libSlide_viewpager);
        mProgressBar = (ProgressBar) findViewById(R.id.libSlide_loading);

        setBtnLeftClickFinish();
        mData = getIntent().getParcelableArrayListExtra(kDatas);
        initDataSize = mData.size();
        int position = getIntent().getIntExtra(kCurrentPosition, 0);


        mViewPager.setOffscreenPageLimit(mData.size());
        mAdapter = new SlideAdapter(bContext, mData);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position);
        mProgressBar.setVisibility(View.GONE);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                setTitlePage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setTitlePage(position);
    }

    private void setTitlePage(int currentPosition) {
        mCurrentPosition = currentPosition;
        setTitleText((currentPosition + 1) + "/" + mData.size());
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showError(String err) {

    }


}
