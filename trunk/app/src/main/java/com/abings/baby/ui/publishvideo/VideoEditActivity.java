package com.abings.baby.ui.publishvideo;


import android.content.Intent;

import com.hellobaby.library.ui.publish.video.edit.BaseVideoEditActivity;

public class VideoEditActivity extends BaseVideoEditActivity {

    @Override
    public void videoEditSuccess(String videoPath) {
        Intent intent = getIntent();
        intent.putExtra(PublishVideoActivity.kVIDEO_MP4,videoPath);
        setResult(-1,intent);
        finish();
    }
}
