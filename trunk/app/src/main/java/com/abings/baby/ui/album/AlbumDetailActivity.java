package com.abings.baby.ui.album;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.data.injection.component.ActivityComponent;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.slide.PagerAdapterItemClick;
import com.hellobaby.library.uploadpic.UploadPicUtils;
import com.hellobaby.library.utils.DownloadHelper;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.custom.ShareBottomDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zwj on 2016/10/30.
 * description: 单张照片点进来的详情界面
 */

public class AlbumDetailActivity extends BaseLibActivity implements AlbumMvpView {
    public static final int RESULT_CODE_DELETE = 199;
    public static final int RESULT_CODE_DELETE_ALBUM = 198;
    public static final String kDatas = "List<WaterFallItem>";
    public static final String kDelete = "deleteList";
    public static final String kCurrentPosition = "position";
    public static final String KALBUMID = "albumid";

    @BindView(R.id.album_detail_loading)
    ProgressBar mProgressBar;
    @BindView(R.id.album_detail_viewpager)
    ViewPager mViewPager;
    private int mCurrentPosition = 0;
    private List<WaterFallItem> mData;
    private List<WaterFallItem> deleteList = new ArrayList<>();
    private PhotoPagerAdapter mAdapter;
    private int initDataSize;
    @BindView(R.id.album_detail_main)
    RelativeLayout album_detail_main;
    @Inject
    AlbumPresenter presenter;


    private TextView tvTitle;
    private ImageView ivLeft;
    private ImageView ivRight;
    private RelativeLayout rlTitle;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_album_detail;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }
    @Override
    public ActivityComponent getActivityComponent() {
        return DaggerUtils.getActivityComponent(bActivityComponent, this);
    }
    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
//        setBtnLeftClickFinish();
        presenter.attachView(this);

        tvTitle = (TextView) findViewById(R.id.albumTitle_tv_title);
        rlTitle = (RelativeLayout) findViewById(R.id.albumTitle_RL);
        ivLeft = (ImageView) findViewById(R.id.albumTitle_iv_left);
        ivRight = (ImageView) findViewById(R.id.albumTitle_iv_right);
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRightOnClick(v);
            }
        });

        album_detail_main.setSystemUiVisibility(View.INVISIBLE);

        ivLeft.setOnClickListener(new View.OnClickListener() {
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
        mData = getIntent().getParcelableArrayListExtra(kDatas);
        initDataSize = mData.size();
        int position = getIntent().getIntExtra(kCurrentPosition, 0);


        mViewPager.setOffscreenPageLimit(2);
        mAdapter = new PhotoPagerAdapter(bContext, mData);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position);
        mProgressBar.setVisibility(View.GONE);
        mAdapter.setOnItemClick(new PagerAdapterItemClick() {
            @Override
            public void onItemClick() {
                if (rlTitle.getVisibility() == View.GONE) {
                    rlTitle.setVisibility(View.VISIBLE);
                } else {
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

    private void setTitlePage(int currentPosition) {
        mCurrentPosition = currentPosition;
        tvTitle.setText((currentPosition + 1) + "/" + mData.size());
    }

    @Override
    public void showData(Object o) {
    }

    protected void btnRightOnClick(View v) {
        String[] items;
        if (getIntent().hasExtra("canDelete")) {
            items = new String[]{"设置为封面", "保存到本地", "分享", "删除"};
            BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                    if (position == 1) {
                        //"保存到本地"
                        DownloadHelper.downloadFile(bContext,mData.get(mCurrentPosition).getUrl(), Const.saveImagePath, mData.get(mCurrentPosition).getUrl().substring(mData.get(mCurrentPosition).getUrl().lastIndexOf("/") + 1, mData.get(mCurrentPosition).getUrl().length()))//
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
                                        showMsg("保存成功");
                                    }
                                });
                    } else if (position == 2) {
                        //分享
                        ShareBottomDialog.getSharePicBottomDialog(bContext, mData.get(mCurrentPosition).getUrl());
                    } else if (position == 0) {
                        //执行设为封面操作
                        presenter.setAlbumCoverByImageId(getIntent().getStringExtra(KALBUMID), mData.get(mCurrentPosition).getId());
                    } else if (position == 3) {

                        String[] delEnsure = new String[]{"删除该照片", "取消"};
                        BottomDialogUtils.getBottomListDialog(bContext, delEnsure, new BottomDialogUtils.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                                if (position == 0) {
                                    if (mData.size() > 1) {
                                        //删除单张
                                        presenter.deleteSingleImgByIds(mData.get(mCurrentPosition).getId(), mData.get(mCurrentPosition).getUrl());
                                        deleteList.add(mData.get(mCurrentPosition));
                                        mData.remove(mCurrentPosition);
                                        mAdapter.notifyDataSetChanged();
                                        if (mData.size() == 0) {
                                            setTitlePage(0);
                                            finish();
                                        } else {
                                            setTitlePage(mCurrentPosition);
                                        }
                                    } else {
                                        //就剩下一张的时候，删除整个相册
                                        AlbumModel albumModel = new AlbumModel();
                                        albumModel.setCommonId(getIntent().getStringExtra(KALBUMID));
                                        presenter.deleteAlbumById(albumModel);
                                    }

                                }
                            }
                        });

                    }
                }
            });
        } else {
            items = new String[]{"保存到本地", "分享"};
            BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                    if (position == 0) {
                        DownloadHelper.downloadFile(bContext,mData.get(mCurrentPosition).getUrl(), Const.saveImagePath, mData.get(mCurrentPosition).getUrl().substring(mData.get(mCurrentPosition).getUrl().lastIndexOf("/") + 1, mData.get(mCurrentPosition).getUrl().length()))//
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
                                        showMsg("保存成功");
                                    }
                                });
                    } else if (position == 1) {
                        //分享
                        ShareBottomDialog.getSharePicBottomDialog(bContext, mData.get(mCurrentPosition).getUrl());
                    }
                }
            });
        }

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

    @Override
    public void initAlbumImgs(AlbumModel albumModel) {

    }

    @Override
    public void albumDelImgs(String imageIds, String imageNames) {

    }

    @Override
    public void albumOptFinish() {

    }

    @Override
    public void albumDelFinish() {
        setResult(RESULT_CODE_DELETE_ALBUM);
        finish();
    }

    @Override
    public void showDelOneImg(final String fileName) {
        //删除oss单张动态图片
//        UploadPicUtils.deleteSingleOssPicture(this,fileName);
        if (mData.size() < initDataSize) {
            Intent data = new Intent();
            data.putParcelableArrayListExtra(kDelete, (ArrayList<? extends Parcelable>) deleteList);
            data.putParcelableArrayListExtra(kDatas, (ArrayList<? extends Parcelable>) mData);
            setResult(RESULT_CODE_DELETE, data);
            finish();
        }
    }
}
