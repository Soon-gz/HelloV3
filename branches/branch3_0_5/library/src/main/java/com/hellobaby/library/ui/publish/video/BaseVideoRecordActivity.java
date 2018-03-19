package com.hellobaby.library.ui.publish.video;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;

import java.io.File;


/**
 * Created by zwj on 2016/6/28.
 * 视频录制界面
 */
public abstract class BaseVideoRecordActivity extends BaseLibTitleActivity implements VideoRecordSurface.OnRecordListener {


    //开始按钮
    protected Button btnStart;
    protected FrameLayout frameLayout;
    //播放进度
    protected VideoProgressView videoProgressView;
    //按钮提示
    protected TextView tvTips;
    private int iTime;
    private VideoRecordSurface videoRecordSurface;
    private String videoSavePath;
    public static final String kVideoSavePath = "videoSavePath";


    protected abstract void recordFinish(String recordMp4Dir, String recordThumbDir);

    /**
     * 开启录制
     *
     * @param videoPath 小视频录制后存储位置
     */
    public static Intent startRecordActivity(@NonNull String videoPath) {
        Intent intent = new Intent();
        intent.putExtra(kVideoSavePath, videoPath);
        return intent;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_video_record;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        setBtnLeftClickFinish();
        setBtnLeftDrawableRes(R.drawable.title_left_arrow_white);
        bTitleBaseRL.setBackgroundColor(Color.BLACK);
        //必须传递的值
        videoSavePath = getIntent().getStringExtra(kVideoSavePath);
        File file = new File(videoSavePath);
        if (!file.exists()) {
            file.mkdir();
        }
        initView();

        videoRecordSurface = new VideoRecordSurface(this, videoSavePath);
        frameLayout.addView(videoRecordSurface);
        btnStart.setOnTouchListener(new View.OnTouchListener() {
            private float moveY;
            private float moveX;
            Rect rect = new Rect();
            boolean isInner = true;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        //按住事件发生后执行代码的区域
                        tvTips.setVisibility(View.VISIBLE);
                        videoRecordSurface.record(BaseVideoRecordActivity.this);
                        videoProgressView.startProgress(videoRecordSurface.mRecordMaxTime);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        //移动事件发生后执行代码的区域
                        if (rect.right == 0 && rect.bottom == 0) {
                            btnStart.getFocusedRect(rect);
                        }
                        moveX = event.getX();
                        moveY = event.getY();
                        if (moveY > 0 && moveX > 0 && moveX <= rect.right && moveY <= rect.bottom) {
                            //内
                            isInner = true;
                            if (!"移开取消".equals(tvTips.getText().toString().trim())) {
                                tvTips.setBackgroundColor(Color.TRANSPARENT);
                                tvTips.setTextColor(getResources().getColor(R.color.video_green));
                                tvTips.setText("移开取消");
                            }
                            btnStart.setVisibility(View.INVISIBLE);
                        } else {
                            //外
                            isInner = false;
                            if (!"松开取消".equals(tvTips.getText().toString().trim())) {
                                tvTips.setBackgroundColor(Color.RED);//getResources().getColor(android.R.color.holo_red_dark));
                                tvTips.setTextColor(Color.WHITE);
                                tvTips.setText("松开取消");
                            }
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        //松开事件发生后执行代码的区域
                        tvTips.setVisibility(View.INVISIBLE);
                        videoProgressView.stopProgress();
                        if (iTime <= videoRecordSurface.mRecordMiniTime || (iTime < videoRecordSurface.mRecordMaxTime && !isInner)) {
                            if (isInner) {
                                Toast.makeText(BaseVideoRecordActivity.this, "录制时间太短", Toast.LENGTH_SHORT).show();
                            } else {
                                //
                            }
                            videoRecordSurface.stopRecord();
                            videoRecordSurface.repCamera();
                            btnStart.setVisibility(View.VISIBLE);
                        } else {
                            videoRecordSurface.stop();
                            String recordThumbDir = videoRecordSurface.getRecordThumbDir();
                            String recordMp4Dir = videoRecordSurface.getRecordDir();
//                            Intent intent = new Intent(BaseVideoRecordActivity.this, PublishVideoActivity.class);
//                            intent.putExtra(kVIDEO_THUMB, recordThumbDir);
//                            intent.putExtra(kVIDEO_MP4, recordMp4Dir);
//                            startActivity(intent);
                            recordFinish(recordMp4Dir, recordThumbDir);
                        }
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initView() {
        btnStart = (Button) findViewById(R.id.libVideoRecorder_btn_start);
        frameLayout = (FrameLayout) findViewById(R.id.libVideoRecorder_fl);
        videoProgressView = (VideoProgressView) findViewById(R.id.libVideoRecorder_progress);
        tvTips = (TextView) findViewById(R.id.libVideoRecorder_tv_tips);
    }


    @Override
    protected void onStop() {
        super.onStop();
        videoRecordSurface.stopRecord();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onRecordFinish() {
        if (videoProgressView != null) {
            videoProgressView.stopProgress();
        }
    }

    @Override
    public void onRecordProgress(int progress) {
        iTime = progress;
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showError(String err) {

    }
}