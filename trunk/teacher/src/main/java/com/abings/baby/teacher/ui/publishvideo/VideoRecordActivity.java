package com.abings.baby.teacher.ui.publishvideo;


import android.content.Intent;

import com.hellobaby.library.ui.publish.video.BaseVideoRecordActivity;

/**
 * Created by zwj on 2016/11/24.
 * description :
 */

public class VideoRecordActivity extends BaseVideoRecordActivity {
    public final static String kVIDEO_THUMB = "video_thumb";
    public final static String kVIDEO_MP4 = "video_mp4";
    @Override
    protected void recordFinish(String recordMp4Dir, String recordThumbDir) {
//        Intent intent = new Intent(VideoRecordActivity.this, PublishVideoActivity.class);
//        intent.putExtra(PublishVideoActivity.kVIDEO_THUMB, recordThumbDir);
//        intent.putExtra(PublishVideoActivity.kVIDEO_MP4, recordMp4Dir);
//        startActivity(intent);
        Intent intent = new Intent();
        intent.putExtra(kVIDEO_THUMB, recordThumbDir);
        intent.putExtra(kVIDEO_MP4, recordMp4Dir);
        setResult(30,intent);
        finish();
    }
}
