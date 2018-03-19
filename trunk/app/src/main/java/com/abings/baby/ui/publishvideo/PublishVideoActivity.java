package com.abings.baby.ui.publishvideo;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.abings.baby.R;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.abings.baby.ui.main.MainActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.ui.publish.video.BaseVideoPlayActivity;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.ToggleButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.abings.baby.ui.main.MainActivity.BabyFragmentEditResultCode;

/**
 * Created by zwj on 2016/7/13.
 * 发布视频界面
 */
public class PublishVideoActivity extends BaseTitleActivity implements PublishVideoMvpView {
    public final static String kVIDEO_THUMB = "video_thumb";
    public final static String kVIDEO_MP4 = "video_mp4";
    public final static String kIsFromRecord = "video_publish";

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
    @BindView(R.id.publish_video_tbtn_public)
    ToggleButton tBtn;
    String isPublic = "";

    private final int REQUEST_VIDEO_CODE = 30;
    private final int REQUEST_VIDEO_EDIT_CODE = 31;
    private final int REQUEST_VIDEO_RECORD_CODE = 32;
    private boolean isFromRecord;
    MediaMetadataRetriever metadataRetriever;

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
        isFromRecord = getIntent().getBooleanExtra(kIsFromRecord, false);
        metadataRetriever = new MediaMetadataRetriever();
        setBtnRightDrawableRes(R.drawable.title_update);
        bIvRight.setVisibility(View.GONE);

        if (isFromRecord) {
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
            bIvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //到这里肯定有视频了
                    backClick();
                }
            });

            initEditText();
        } else {
            bIvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

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
        if (isFromRecord) {
            //点击图片播放
            VideoPlayActivity.startActivityForResultVideoPlayRecord(bContext, videoPath, VideoPlayActivity.MORE_TYPE_RECORD, BaseVideoPlayActivity.class);
        } else {
            BottomDialogUtils.getBottomPrizeDrawDialog(bContext, "选择小视频来源", "录制小视频", "本地小视频", new BottomDialogUtils.onSureAndCancelClick() {
                @Override
                public void onItemClick() {
                    Intent intent = VideoRecordActivity.startRecordActivity(Const.videoPath);
                    intent.setClass(bContext, VideoRecordActivity.class);
                    startActivityForResult(intent,REQUEST_VIDEO_RECORD_CODE);

                }

                @Override
                public void onCancel() {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_VIDEO_CODE);
                }
            });

        }

    }

    @Override
    public void publishSuccess() {
        MainActivity.isPublishVideo = true;
        setResult(BabyFragmentEditResultCode);
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
        presenter.videoUpload(content, videoThumbPath, videoPath, isPublic);
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
        //录制视频回调
        if (resultCode == RESULT_OK && requestCode == REQUEST_VIDEO_RECORD_CODE){
            videoThumbPath = data.getStringExtra(kVIDEO_THUMB);
            videoPath = data.getStringExtra(kVIDEO_MP4);
            File file = new File(videoThumbPath);
            if (file.exists()) {
//            Bitmap bitmap = BitmapFactory.decodeFile(videoThumbPath);
//            imageView.setImageBitmap(bitmap);
                ImageLoader.loadRoundCenterCrop(bContext, videoThumbPath, imageView);
                ivPlayIcon.setVisibility(View.VISIBLE);
            } else {
                showMsg("预览图不存在");
            }
            bIvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //到这里肯定有视频了
                    backClick();
                }
            });
            if (videoPath != null){
                isFromRecord = true;
            }
            initEditText();
        }
        //选择本地视频回调
        if (resultCode == RESULT_OK && requestCode == REQUEST_VIDEO_CODE) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = { MediaStore.Video.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedVideo ,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String videoPath_cursor = cursor.getString(columnIndex);
            videoPath = videoPath_cursor;

            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(videoPath);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int  duration = mediaPlayer.getDuration();
            if (duration > 10000){
                Intent intent = new Intent(bContext, VideoEditActivity.class);
                intent.putExtra(kVIDEO_MP4,videoPath);
                startActivityForResult(intent,REQUEST_VIDEO_EDIT_CODE);
            }else{
                metadataRetriever.setDataSource(videoPath);
                Bitmap bitmap = metadataRetriever.getFrameAtTime();
                String pic_new_file = Const.videoPath+File.separator+"local_file_"+System.currentTimeMillis()+".png";
                File file = new File(pic_new_file);
                saveBitmap(file,bitmap);

                if (bitmap != null){
                    videoThumbPath = pic_new_file;
                    ImageLoader.loadRoundCenterCrop(bContext, videoThumbPath, imageView);
                    ivPlayIcon.setVisibility(View.VISIBLE);
                }
                if (videoPath != null){
                    isFromRecord = true;
                }
                initEditText();
            }
            cursor.close();
        }
        //编辑视频回调
        if (resultCode == RESULT_OK && requestCode == REQUEST_VIDEO_EDIT_CODE){
            videoPath = data.getStringExtra(kVIDEO_MP4);
            MediaMetadataRetriever metadataRetriever0 = new MediaMetadataRetriever();
            metadataRetriever0.setDataSource(videoPath);
            Bitmap bitmap = metadataRetriever0.getFrameAtTime();
            ivPlayIcon.setVisibility(View.VISIBLE);
            if (videoPath != null){
                isFromRecord = true;
            }
            String pic_new_file = Const.videoPath+File.separator+"local_file_cut_"+System.currentTimeMillis()+".png";
            File file = new File(pic_new_file);
            saveBitmap(file,bitmap);
            videoThumbPath = pic_new_file;
            ImageLoader.loadRoundCenterCrop(bContext, videoThumbPath, imageView);
            initEditText();
        }
    }

    public void saveBitmap(File f,Bitmap bm) {
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initEditText(){
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
        tBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    isPublic = "1";
                } else {
                    isPublic = "";
                }
            }
        });
    }
}
