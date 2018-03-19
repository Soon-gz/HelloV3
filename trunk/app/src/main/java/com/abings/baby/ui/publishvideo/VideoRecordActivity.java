package com.abings.baby.ui.publishvideo;


import android.content.Intent;

import com.abings.baby.ui.main.MainActivity;
import com.hellobaby.library.ui.publish.video.BaseVideoRecordActivity;

/**
 * Created by zwj on 2016/11/24.
 * description : 小视频录制界面
 */

public class VideoRecordActivity extends BaseVideoRecordActivity {
    @Override
    protected void recordFinish(String recordMp4Dir, String recordThumbDir) {
        Intent intent = getIntent();
        intent.putExtra(PublishVideoActivity.kVIDEO_THUMB, recordThumbDir);
        intent.putExtra(PublishVideoActivity.kVIDEO_MP4, recordMp4Dir);
        setResult(RESULT_OK,intent);
        finish();
//        startActivityForResult(intent, MainActivity.mainRequestCode);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == MainActivity.mainRequestCode) {
//            setResult(MainActivity.mainRequestCode);
//            finish();
//        }
//    }
}
