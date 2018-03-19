package com.abings.baby.teacher.ui.publishvideo;

import android.content.Intent;

import com.abings.baby.teacher.ui.publishpicture.PublishPictureActivity;
import com.hellobaby.library.ui.publish.video.edit.BaseVideoEditActivity;

public class VideoEditActivity extends BaseVideoEditActivity {

    @Override
    public void videoEditSuccess(String videoPath) {
        Intent intent = getIntent();
        intent.putExtra(PublishPictureActivity.kVIDEO_MP4,videoPath);
        setResult(-1,intent);
        finish();
    }

}
