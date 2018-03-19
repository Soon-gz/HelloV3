package com.hellobaby.library.ui.slide;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.utils.DownloadHelper;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.custom.ShareBottomDialog;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by zwj on 2016/12/23.
 * description : 幻灯片，提供图片左右滑动查看
 */

public class SlideActivity extends BaseLibActivity {


    public static final String kDatas = "List<SlideBean>";
    public static final String kCurrentPosition = "position";

    ProgressBar mProgressBar;//album_detail_loading
    PhotoViewPager mViewPager;//album_detail_viewpager
    private int mCurrentPosition = 0;
    private List<SlideBean> mData;
    private SlideAdapter mAdapter;
    private int initDataSize;
    private TextView tvTitle;
    private ImageView ivLeft;
    private ImageView ivRight;
    private RelativeLayout rlTitle;
    private RelativeLayout slide_main;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_slide;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        slide_main = (RelativeLayout) findViewById(R.id.slide_main);
        mViewPager = (PhotoViewPager) findViewById(R.id.libSlide_viewpager);
        mProgressBar = (ProgressBar) findViewById(R.id.libSlide_loading);
        tvTitle = (TextView) findViewById(R.id.libSlideTitle_tv_title);
        rlTitle = (RelativeLayout) findViewById(R.id.libSlideTitle_RL);
        ivLeft = (ImageView) findViewById(R.id.libSlideTitle_iv_left);
        ivRight = (ImageView) findViewById(R.id.libSlideTitle_iv_right);
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRightOnClick(v);
            }
        });
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mData = getIntent().getParcelableArrayListExtra(kDatas);
        initDataSize = mData.size();
        int position = getIntent().getIntExtra(kCurrentPosition, 0);
        mViewPager.setOffscreenPageLimit(2);
        mAdapter = new SlideAdapter(bContext, mData);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position);
        mProgressBar.setVisibility(View.GONE);
        rlTitle.setVisibility(View.GONE);
        mAdapter.setOnItemClick(new PagerAdapterItemClick() {
            @Override
            public void onItemClick() {
                getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
                if(rlTitle.getVisibility()==View.GONE){
                    rlTitle.setVisibility(View.VISIBLE);
                }else{
                    rlTitle.setVisibility(View.GONE);
                }
            }
        });
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

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= 19){
            getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        }
    }

    private void setTitlePage(int currentPosition) {
        mCurrentPosition = currentPosition;
        tvTitle.setText((currentPosition + 1) + "/" + mData.size());
    }

    protected void btnRightOnClick(View v) {
        String[] items;
        items = new String[]{"保存到本地", "分享"};
        BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                if (position == 0) {
                    DownloadHelper.downloadFile(bContext,mData.get(mCurrentPosition).getUrl(), Const.saveImagePath, mData.get(mCurrentPosition).getUrl().substring(mData.get(mCurrentPosition).getUrl().lastIndexOf("/")+1,mData.get(mCurrentPosition).getUrl().length()))//
                            .observeOn(AndroidSchedulers.mainThread())//
                            .subscribeOn(Schedulers.io())//
                            .subscribe(new Subscriber<String>() {
                                @Override
                                public void onCompleted() {
                                    showProgress(false);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    showProgress(false);
                                    showError("保存失败，请稍后再试");
                                }

                                @Override
                                public void onNext(String s) {
                                    showProgress(false);
                                    showMsg("图片保存成功");
                                }
                            });
                } else if (position == 1) {
                    ShareBottomDialog.getSharePicBottomDialog(bContext,mData.get(mCurrentPosition).getUrl());
                }
            }
        });
    }

    @Override
    public void showData(Object o) {

    }
}
