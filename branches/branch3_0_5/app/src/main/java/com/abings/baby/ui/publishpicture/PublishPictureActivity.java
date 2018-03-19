package com.abings.baby.ui.publishpicture;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.abings.baby.R;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.abings.baby.ui.main.MainActivity;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.utils.CameraUtils;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.crop.FileUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 发送小视频
 */
public class PublishPictureActivity extends BaseTitleActivity implements PublishPictureMvpView {


    @Inject
    PublishPicturePresenter presenter;

    @BindView(R.id.publishPicture_gridView)
    GridView gridView;
    @BindView(R.id.publishPicture_et_title)
    EditText etTitle;
    @BindView(R.id.publishPicture_et_content)
    EditText etContent;


    private GridAdapter gridAdapter;
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<>();


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_publishpicture;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        setBtnRightDrawableRes(R.drawable.title_update);
        bIvRight.setVisibility(View.GONE);
        //这个值是传递给下一级展示界面的
        final ArrayList<String> images = new ArrayList<>();
        presenter.attachView(this);
        if (getIntent().hasExtra("type") && getIntent().getStringExtra("type").equals("相机")) {
            mCurrentPhotoPath =  CameraUtils.dispatchTakePictureIntent(PublishPictureActivity.this);
        }
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridView.setNumColumns(3);
        // preview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgs = (String) parent.getItemAtPosition(position);
                images.clear();
                images.addAll(imagePaths);
                if (images.contains(GridAdapter.ADD) || images.size() >= 9) {
                    images.remove(GridAdapter.ADD);
                }
                if (GridAdapter.ADD.equals(imgs)) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(PublishPictureActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(9); // 最多选择照片数量，默认为6
                    intent.setSelectedPaths(images); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(PublishPictureActivity.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(images);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        if (images.size() < 9) {
            imagePaths.add(GridAdapter.ADD);
        }
        gridAdapter = new GridAdapter(this, imagePaths);
        gridView.setAdapter(gridAdapter);


        setTextChangeListener();

        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backClick();
            }
        });
    }

    private void backClick() {
        if (LoginUtils.isInputEdit(etTitle) || !imagePaths.get(0).equals(GridAdapter.ADD)) {//, etContent
            BottomDialogUtils.getBottomExitEditDialog(bContext);
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && (LoginUtils.isInputEdit(etTitle) || !imagePaths.get(0).equals(GridAdapter.ADD))) {//, etContent
            backClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setTextChangeListener() {

        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkEnableUpdate();
            }
        });
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkEnableUpdate();
            }
        });
    }

    /**
     * 检查
     */
    private void checkEnableUpdate() {
        if (!LoginUtils.isEmptyEdit(etTitle) && !imagePaths.get(0).equals(GridAdapter.ADD)) {//, etContent
            //1.输入不为空   &&     3.第一个图片不是add&&
            bIvRight.setVisibility(View.VISIBLE);
        } else {
            bIvRight.setVisibility(View.GONE);
        }
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA_CODE:
                    // 选择照片
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    imagePaths.clear();
                    imagePaths.addAll(list);
                    loadAdapter();
                    break;
                case REQUEST_PREVIEW_CODE:
                    // 预览
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    imagePaths.clear();
                    imagePaths.addAll(ListExtra);
                    loadAdapter();
                    break;
                case CameraUtils.REQUEST_TAKEPHOTO_CODE:
                    Uri uri = Uri.parse(mCurrentPhotoPath);
                    String path = FileUtils.getFilePathFromUri(bContext, uri);
                    if(imagePaths.contains(GridAdapter.ADD)){
                        //带了添加
                        imagePaths.remove(GridAdapter.ADD);
                    }
                    imagePaths.add(path);
                    loadAdapter();
                    break;
            }
        }
    }

    private void loadAdapter() {
        if (!imagePaths.contains(GridAdapter.ADD) && imagePaths.size() < 9) {
            imagePaths.add(GridAdapter.ADD);
        }
        //设置右上角是否显示
        String title = etTitle.getText().toString().trim();
//        String content = etContent.getText().toString().trim();
        if ("".equals(title) || imagePaths.size() <= 1) {// "".equals(content) ||
            bIvRight.setVisibility(View.GONE);
        } else {
            bIvRight.setVisibility(View.VISIBLE);
        }

        gridAdapter = new GridAdapter(this, imagePaths);
        gridView.setAdapter(gridAdapter);
        checkEnableUpdate();

    }

    @Override
    protected void btnRightOnClick(View v) {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        List<String> listImage = new ArrayList<>();
        //创建的时候，不提示
        if (imagePaths.contains(GridAdapter.ADD)) {
            imagePaths.remove(GridAdapter.ADD);
        }
        listImage.addAll(imagePaths);

        if ("".equals(title) ||  listImage.size() <= 0) {//"".equals(content) ||
            showToastError("请输入正确内容后再创建相册");
            return;
        }

        AlbumModel albumModel = new AlbumModel();
        albumModel.setTitle(title);
        albumModel.setContent(content);
        presenter.createAlbum(albumModel, listImage);
    }

    @Override
    public void uploadFinish() {
        setResult(MainActivity.mainRequestCode);
        finish();
    }
    private String mCurrentPhotoPath;
}
