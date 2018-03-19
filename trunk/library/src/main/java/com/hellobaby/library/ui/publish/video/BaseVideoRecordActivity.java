package com.hellobaby.library.ui.publish.video;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    protected ProgressBarView videoProgressView;
    //按钮提示
//    protected TextView tvTips;
    private int iTime;
    private VideoRecordSurface videoRecordSurface;
    private FrameLayout video_record_main;
    private String videoSavePath;
    public static final String kVideoSavePath = "videoSavePath";
    private ProgressHandler progressHandler = new ProgressHandler();
    protected abstract void recordFinish(String recordMp4Dir, String recordThumbDir);
    private RelativeLayout libTitle_RL;

    private OrientationSensorListener listener;
    private SensorManager sm;
    private Sensor sensor;
    private TextView lib_center_circle;
    private ImageView libPlayVideo_tv_cancel;
    private ImageView btnChange;

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
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= 19){
            getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        //保持高亮
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new OrientationSensorListener();
        video_record_main = (FrameLayout) findViewById(R.id.video_record_main);
        lib_center_circle = (TextView) findViewById(R.id.lib_center_circle);
        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
        bTitleBaseRL.setVisibility(View.GONE);

        libTitle_RL = (RelativeLayout) findViewById(R.id.libTitle_RL_record);
        libPlayVideo_tv_cancel = (ImageView) findViewById(R.id.libPlayVideo_tv_cancel_record);
        btnChange = (ImageView) findViewById(R.id.btn_change);

        video_record_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 19){
                    getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
                }
                if (libTitle_RL.getVisibility() == View.VISIBLE){
                    libTitle_RL.setVisibility(View.GONE);
                }else {
                    libTitle_RL.setVisibility(View.VISIBLE);
                }
            }
        });
        libPlayVideo_tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
//            private float moveY;
//            private float moveX;
//            Rect rect = new Rect();
//            boolean isInner = true;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        //按住事件发生后执行代码的区域
//                        tvTips.setVisibility(View.VISIBLE);
                        videoRecordSurface.record(BaseVideoRecordActivity.this,listener.getOrientationHintDegrees());
                        btnStart.setVisibility(View.INVISIBLE);
                        videoProgressView.setVisibility(View.VISIBLE);
                        lib_center_circle.setVisibility(View.VISIBLE);
                        startProgress();
                        break;
                    }
//                    case MotionEvent.ACTION_MOVE: {
//                        //移动事件发生后执行代码的区域
//                        if (rect.right == 0 && rect.bottom == 0) {
//                            btnStart.getFocusedRect(rect);
//                        }
//                        moveX = event.getX();
//                        moveY = event.getY();
//                        if (moveY > 0 && moveX > 0 && moveX <= rect.right && moveY <= rect.bottom) {
//                            //内
//                            isInner = true;
//                            if (!"移开取消".equals(tvTips.getText().toString().trim())) {
//                                tvTips.setBackgroundColor(Color.TRANSPARENT);
//                                tvTips.setTextColor(getResources().getColor(R.color.video_green));
//                                tvTips.setText("移开取消");
//                            }
//                        } else {
//                            //外
//                            isInner = false;
//                            if (!"松开取消".equals(tvTips.getText().toString().trim())) {
//                                tvTips.setBackgroundColor(Color.RED);//getResources().getColor(android.R.color.holo_red_dark));
//                                tvTips.setTextColor(Color.WHITE);
//                                tvTips.setText("松开取消");
//                            }
//                        }
//                        break;
//                    }
                    case MotionEvent.ACTION_UP: {
                        //松开事件发生后执行代码的区域
//                        tvTips.setVisibility(View.INVISIBLE);
                        stopProgress();
                        if (iTime <= videoRecordSurface.mRecordMiniTime) {
                            showToast( "录制时间太短");
                            videoRecordSurface.stopRecord();
                            videoRecordSurface.repCamera();
                            btnStart.setVisibility(View.VISIBLE);
                            videoProgressView.setVisibility(View.GONE);
                            lib_center_circle.setVisibility(View.GONE);
                        } else if(iTime < videoRecordSurface.mRecordMaxTime){
                            videoRecordSurface.stop();
                        }
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoRecordSurface.changeCameraPosition();
            }
        });
    }

    private void stopProgress() {
        progressHandler.sendEmptyMessageDelayed(ProgressHandler.REMOVE, 0);
    }


    private void startProgress() {
        progressHandler.sendEmptyMessageDelayed(ProgressHandler.UPDATE, ProgressHandler.TIME);
        progressHandler.setProgress(new ProgressHandler.Progress() {
            @Override
            public void setSchedule(int schedule) {
                videoProgressView.setCurrentProgress(schedule);
            }

            @Override
            public void onSuccess() {
            }
        });
    }

    private void initView() {
        btnStart = (Button) findViewById(R.id.libVideoRecorder_btn_start);
        frameLayout = (FrameLayout) findViewById(R.id.libVideoRecorder_fl);
        videoProgressView = (ProgressBarView) findViewById(R.id.libVideoRecorder_progress);
//        tvTips = (TextView) findViewById(R.id.libVideoRecorder_tv_tips);
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
            stopProgress();
        }
        String recordThumbDir = videoRecordSurface.getRecordThumbDir();
        String recordMp4Dir = videoRecordSurface.getRecordDir();
        recordFinish(recordMp4Dir, recordThumbDir);
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

    @Override
    protected void onPause() {
        sm.unregisterListener(listener);
        super.onPause();
    }

    @Override
    protected void onResume() {
        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }
}