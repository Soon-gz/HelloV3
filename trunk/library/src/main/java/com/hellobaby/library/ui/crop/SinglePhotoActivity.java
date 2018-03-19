package com.hellobaby.library.ui.crop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.utils.CameraUtils;
import com.hellobaby.library.utils.DownloadHelper;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.BottomDialogUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SinglePhotoActivity extends BaseLibTitleActivity {
    ImageView photo;

    private static final int REQUEST_GALLERY = 21;
    private static final int REQUEST_CROP = 22;
    private String mCurrentPhotoPath;
    public static String kAPP_ID = "APPLICATION_ID";
    String bmp;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_single_photo;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        photo = (ImageView) findViewById(R.id.singlephoto_iv);
        setTitle("个人头像");
        setBtnLeftClickFinish();
        setBtnRightDrawable(getResources().getDrawable(R.drawable.title_more_black));
        bmp = getIntent().getStringExtra("bitmap");
        ImageLoader.loadHeadOriginal(this, bmp, photo);
    }


    @Override
    public void showData(Object o) {

    }


    @Override
    protected void btnRightOnClick(View v) {
        if (getIntent().getBooleanExtra("isCreate", false)) {
            String[] items = {"拍照", "图库", "保存到本地"};
            BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                    if (position == 0) {
                        mCurrentPhotoPath = CameraUtils.dispatchTakePictureIntent(bContext);
                    } else if (position == 1) {
                        startGalleryIntent();
                    } else if (position == 2) {
                        if (bmp.equals( "")) {
                            showError("没有头像");
                        } else {
                            DownloadHelper.downloadFile(bContext,bmp, Const.saveImagePath, bmp.substring(bmp.lastIndexOf("/") + 1, bmp.length()))//
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
                        }
                    }
                }
            });
        } else {
            String[] items = {"保存到本地", "取消"};
            BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                    if (position == 1) {
                        return;
                    } else if (position == 0) {
                        if (bmp.equals( "")) {
                            showError("没有头像");
                        } else {
                            DownloadHelper.downloadFile(bContext,bmp, Const.saveImagePath, bmp.substring(bmp.lastIndexOf("/") + 1, bmp.length()))//
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
                                            showError("下载异常，请稍后再试");
                                        }

                                        @Override
                                        public void onNext(String s) {
                                            showProgress(false);
                                            showMsg("图片保存成功");
                                        }
                                    });
                        }
                    }
                }
            });
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraUtils.REQUEST_TAKEPHOTO_CODE && resultCode == RESULT_OK) {
            goCrop(Uri.parse(mCurrentPhotoPath));
        } else if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            goCrop(data.getData());
        } else if (requestCode == REQUEST_CROP && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            intent.setData(data.getData());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void goCrop(Uri sourUri) {
        Intent intent = new Intent(this, CropActivity.class);
        intent.setData(sourUri);
        startActivityForResult(intent, REQUEST_CROP);
    }

    private void startGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALLERY);
    }

}
