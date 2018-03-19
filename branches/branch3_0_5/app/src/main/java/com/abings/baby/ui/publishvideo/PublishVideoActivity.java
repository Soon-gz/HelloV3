package com.abings.baby.ui.publishvideo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.abings.baby.R;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.abings.baby.ui.main.MainActivity;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.ui.publish.video.BaseVideoPlayActivity;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.BottomDialogUtils;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zwj on 2016/7/13.
 * 发布视频界面
 */
public class PublishVideoActivity extends BaseTitleActivity implements PublishVideoMvpView {
    public final static String kVIDEO_THUMB = "video_thumb";
    public final static String kVIDEO_MP4 = "video_mp4";
    @Inject
    PublishVideoPresenter presenter;
    @BindView(R.id.publishvideo_et)
    EditText etContent;//内容
    @BindView(R.id.publishvideo_image)
    ImageView imageView;//预览图片
    @BindView(R.id.publishvideo_iv_playIcon)
    ImageView ivPlayIcon;//播放图
    private String videoThumbPath;
    private String videoPath;

    private final int REQUEST_VIDEO_CODE = 30;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_publishvideo;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        presenter.attachView(this);
        videoThumbPath = getIntent().getStringExtra(kVIDEO_THUMB);
        videoPath = getIntent().getStringExtra(kVIDEO_MP4);
        File file = new File(videoThumbPath);
        if (file.exists()) {
//            Bitmap bitmap = BitmapFactory.decodeFile(videoThumbPath);
//            imageView.setImageBitmap(bitmap);
            ImageLoader.loadRoundCenterCrop(bContext, videoThumbPath, imageView);
            ivPlayIcon.setVisibility(View.VISIBLE);
        } else {
            showMsg("预览图不存在");
        }
        setBtnRightDrawableRes(R.drawable.title_update);
        bIvRight.setVisibility(View.GONE);
        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //到这里肯定有视频了
                backClick();
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
        if (!LoginUtils.isEmptyEdit(etContent) && videoPath != null) {
            //1.输入不为空   &&
            bIvRight.setVisibility(View.VISIBLE);
        } else {
            bIvRight.setVisibility(View.GONE);
        }
    }

    private void backClick() {
        if (LoginUtils.isInputEdit(etContent) || videoPath != null) {
            BottomDialogUtils.getBottomExitEditDialog(bContext);
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && (LoginUtils.isInputEdit(etContent) || videoPath != null)) {
            backClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showData(Object o) {

    }

    @OnClick(R.id.publishvideo_image)
    public void onClickImageVideo() {
        //点击图片播放
        VideoPlayActivity.startActivityForResultVideoPlayRecord(bContext, videoPath, VideoPlayActivity.MORE_TYPE_RECORD, BaseVideoPlayActivity.class);
    }

    @Override
    public void publishSuccess() {
        MainActivity.isPublishVideo = true;
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected void btnRightOnClick(View v) {
        String content = etContent.getText().toString().trim();
        if (content.length() <= 0) {
            showToast("请输入内容再上传");
            return;
        }
        presenter.videoUpload(content, videoThumbPath, videoPath);
    }

    @Override
    public void onBackPressed() {
        BottomDialogUtils.getBottomExitEditDialog(bContext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == VideoPlayActivity.resultCode) {
            finish();
        }
    }
}
