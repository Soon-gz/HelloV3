package com.abings.baby.ui.album;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.abings.baby.R;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.utils.DownloadHelper;
import com.hellobaby.library.widget.custom.ShareBottomDialog;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zwj on 2016/10/30.
 * description: 单张照片点进来的详情界面
 */

public class AlbumDetailActivity extends BaseTitleActivity {
    public static final int RESULT_CODE_DELETE = 199;
    public static final String kDatas = "List<WaterFallItem>";
    public static final String kDelete = "deleteList";
    public static final String kCurrentPosition = "position";

    @BindView(R.id.album_detail_loading)
    ProgressBar mProgressBar;
    @BindView(R.id.album_detail_viewpager)
    ViewPager mViewPager;
    private int mCurrentPosition = 0;
    private List<WaterFallItem> mData;
    private List<WaterFallItem> deleteList = new ArrayList<>();
    private PhotoPagerAdapter mAdapter;
    private int initDataSize;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_album_detail;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
//        setBtnLeftClickFinish();
        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回已经删除的数据
                if (mData.size() < initDataSize) {
                    Intent data = new Intent();
                    data.putParcelableArrayListExtra(kDelete, (ArrayList<? extends Parcelable>) deleteList);
                    data.putParcelableArrayListExtra(kDatas, (ArrayList<? extends Parcelable>) mData);
                    setResult(RESULT_CODE_DELETE, data);
                }
                finish();
            }
        });
        setBtnRightDrawableRes(R.drawable.title_more_black);
        mData = getIntent().getParcelableArrayListExtra(kDatas);
        initDataSize = mData.size();
        int position = getIntent().getIntExtra(kCurrentPosition, 0);


        mViewPager.setOffscreenPageLimit(mData.size());
        mAdapter = new PhotoPagerAdapter(bContext, mData);
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
    public void showData(Object o) {}

    @Override
    protected void btnRightOnClick(View v) {
        String[] items;
        if(getIntent().hasExtra("canDelete")) {
            items = new String[]{"保存到本地", "分享", "删除"};
        } else {
            items = new String[]{"保存到本地", "分享"};
        }
        BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,String item, long id) {
                if (position == 0) {
                    DownloadHelper.downloadFile(mData.get(mCurrentPosition).getUrl(), Const.saveImagePath, mData.get(mCurrentPosition).getUrl().substring(mData.get(mCurrentPosition).getUrl().lastIndexOf("/")+1,mData.get(mCurrentPosition).getUrl().length()))//
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
                                    showMsg("保存到本地成功");
                                }
                            });
                } else if (position == 1) {
                    //分享
//                    image = new UMImage(this,mData.get(mCurrentPosition).getUrl());
                    //"标题","内容"并没有什么用
                    ShareBottomDialog.getShareSimplePicBottomDialog(bContext,null,new UMImage(bContext,mData.get(mCurrentPosition).getUrl()));
                } else if (position == 2) {
                    //删除
                   deleteList.add(mData.get(mCurrentPosition));
                    mData.remove(mCurrentPosition);
                    mAdapter.notifyDataSetChanged();
                    if (mData.size() == 0) {
                        setTitlePage(0);
                        finish();
                    } else {
                        setTitlePage(mCurrentPosition);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mData.size() < initDataSize) {
            Intent data = new Intent();
            data.putParcelableArrayListExtra(kDelete, (ArrayList<? extends Parcelable>) deleteList);
            data.putParcelableArrayListExtra(kDatas, (ArrayList<? extends Parcelable>) mData);
            setResult(RESULT_CODE_DELETE, data);
            finish();
            return;
        }
        super.onBackPressed();
    }
}
