package com.hellobaby.library.ui.publish.video;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.VideoView;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.widget.BottomDialogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 视频播放
 */
public class BaseVideoPlayActivity extends BaseLibActivity {
    //    视频是否来本地的小视频
    public static final String kIsFromLocal = "IsFromLocal";
    /**
     * 视频的路径
     */
    public static final String kVideoPath = "VideoPath";
    /**
     * 视频链接
     */
    public static final String kVideoUrl = "VideoUrl";
    /**
     * 视频的文件目录
     */
    public static final String kVideoFile = "VideoFile";
    /**
     * 视频的名字
     */
    public static final String kVideoName = "VideoName";
    /**
     * 删除类型
     */
    public static final String kMoreType = "MoreType";
    /**
     * 删除类型-相册里的
     */
    public static final String MORE_TYPE_ALBUM = "MORE_TYPE_ALBUM";
    /**
     * 删除类型-录制的
     */
    public static final String MORE_TYPE_RECORD = "MORE_TYPE_RECORD";
    /**
     * 删除类型-校园内的动态
     */
    public static final String MORE_TYPE_DYNAMIC = "MORE_TYPE_DYNAMIC";
    /**
     * 删除类型-资讯
     */
    public static final String MORE_TYPE_INFORMATION = "MORE_TYPE_INFORMATION";
    public static final int resultCode = 789;


    private ImageView imCancel;
    protected ImageView imMore;
    private VideoView videoView;
    private String delType;
    private boolean isFirst = true;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_play_video;
    }

    @Override
    protected void initDaggerInject() {

    }

    /**
     * @param activity
     * @param videoFile        视频文件夹
     * @param videoDownLoadUrl 视频下载的Url
     * @param videoName        视频的名称 TODO 这里可以去掉，可以考虑从Url中获取
     */
    public static void startVideoPlayNet(@NonNull Activity activity, @NonNull String videoFile, @NonNull String videoDownLoadUrl, @NonNull String videoName, String delType, Class clzz) {
        Intent intent = new Intent(activity, clzz);
        intent.putExtra(kIsFromLocal, false);
        intent.putExtra(kVideoFile, videoFile);
        intent.putExtra(kVideoUrl, videoDownLoadUrl);
        intent.putExtra(kVideoName, videoName);
        intent.putExtra(kMoreType, delType);
        activity.startActivity(intent);
    }

    /**
     * @param videoFile        视频文件夹
     * @param videoDownLoadUrl 视频下载的Url
     * @param videoName        视频的名称 TODO 这里可以去掉，可以考虑从Url中获取
     */
    public static Intent getIntentVideoPlayNet(@NonNull String videoFile, @NonNull String videoDownLoadUrl, @NonNull String videoName, String moreType) {
        Intent intent = new Intent();
        intent.putExtra(kIsFromLocal, false);
        intent.putExtra(kVideoFile, videoFile);
        intent.putExtra(kVideoUrl, videoDownLoadUrl);
        intent.putExtra(kVideoName, videoName);
        intent.putExtra(kMoreType, moreType);
        return intent;
    }

    /**
     * 1.播放自己录制的小视频
     * 2.本地已经确认存在的小视频
     *
     * @param activity
     * @param videoPath 视频的真实路径
     */
    public static void startVideoPlayRecord(@NonNull Activity activity, @NonNull String videoPath, String moreType, Class clzz) {
        Intent intent = new Intent(activity, clzz);
        intent.putExtra(kIsFromLocal, true);
        intent.putExtra(kVideoPath, videoPath);
        intent.putExtra(kMoreType, moreType);
        activity.startActivity(intent);
    }

    public static void startActivityForResultVideoPlayRecord(@NonNull Activity activity, @NonNull String videoPath, String moreType, Class clzz) {
        Intent intent = new Intent(activity, clzz);
        intent.putExtra(kIsFromLocal, true);
        intent.putExtra(kVideoPath, videoPath);
        intent.putExtra(kMoreType, moreType);
        activity.startActivityForResult(intent, resultCode);
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        boolean isFromRecord = getIntent().getBooleanExtra(kIsFromLocal, false);
        delType = getIntent().getStringExtra(kMoreType);
        isFirst = true;

        imCancel = (ImageView) findViewById(R.id.libPlayVideo_tv_cancel);
        imMore = (ImageView) findViewById(R.id.libPlayVideo_tv_more);
        videoView = (VideoView) findViewById(R.id.libPlayVideo_videoView);
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if(isFirst){
                    isFirst = false;
                    showError("播放该视频异常");
                }
                return true;
            }
        });
        if (isFromRecord) {
            //录制的来的小视频
            String mVideoPath = getIntent().getStringExtra(kVideoPath);
            File file = new File(mVideoPath);
            if (file.exists()) {
                videoView.setVideoPath(file.getAbsolutePath());
                videoView.start();
                setLoop(file.getAbsolutePath());
            } else {
                logE("not found video " + mVideoPath);
            }
        } else {
            String mVideoFile = getIntent().getStringExtra(kVideoFile);
            String mVideoUrl = getIntent().getStringExtra(kVideoUrl);
            String mVideoName = getIntent().getStringExtra(kVideoName);
            File videoFile = new File(mVideoFile);
            if (!videoFile.exists()) {
                videoFile.mkdirs();
            }
            //网络下载来的视频
            File file = new File(mVideoFile + File.separator + mVideoName);//视频文件目录存在
            if (file.exists()) {
                //视频已经下载
                videoView.setVideoPath(file.getAbsolutePath());
                videoView.start();
                setLoop(file.getAbsolutePath());
            } else {
                //文件不存在，需要下载
                downLoadVideo(mVideoUrl,
                        mVideoFile,//存储路径
                        mVideoName);
            }
        }
        imCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"删除", "取消"};
                BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                        if (position == 0) {
                            AlbumModel albumModel = (AlbumModel) getIntent().getSerializableExtra("AlbumModel");
                            videoDel(albumModel);
                        } else {
                            finish();
                        }
                    }
                });
            }
        });

        if (MORE_TYPE_ALBUM.equals(delType)) {
            imMore.setVisibility(View.VISIBLE);
        } else if (MORE_TYPE_RECORD.equals(delType)) {
            imMore.setVisibility(View.VISIBLE);
        } else if (MORE_TYPE_DYNAMIC.equals(delType)) {
            imMore.setVisibility(View.GONE);
        } else if (MORE_TYPE_INFORMATION.equals(delType)) {
            imMore.setVisibility(View.GONE);
        }
    }

    /**
     * 删除视频
     *
     * @param albumModel
     */
    protected void videoDel(AlbumModel albumModel) {
        if (MORE_TYPE_ALBUM.equals(delType)) {
            VideoDelFromAlbum(albumModel);
        } else if (MORE_TYPE_RECORD.equals(delType)) {
            //删除录制的
            String videoPath = getIntent().getStringExtra(kVideoPath);
            File file = new File(videoPath);
            file.deleteOnExit();
            setResult(resultCode);
            finish();
        } else if (MORE_TYPE_DYNAMIC.equals(delType)) {

        } else if (MORE_TYPE_INFORMATION.equals(delType)) {

        }
    }

    protected void VideoDelFromAlbum(AlbumModel albumModel) {

    }


    public void setLoop(final String videoPath) {
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);

            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVideoPath(videoPath);
                videoView.start();
            }
        });
    }


    public void playVideo(String filepath) {
        videoView.setVideoPath(filepath);
        videoView.start();
        setLoop(filepath);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * TODO 到时候使用统一的下载方法
     *
     * @param videoPath 下载地址
     * @param pathFile  存储文件夹
     * @param fileName  存储的文件名
     * @return
     */
    public void downLoadVideo(String videoPath, final String pathFile, final String fileName) {
        showProgress(true);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.SECONDS)
                .writeTimeout(3000, TimeUnit.SECONDS)
                .readTimeout(3000, TimeUnit.SECONDS);
        OkHttpClient mOkHttpClient = builder.build();
        Request request = new Request.Builder().url(videoPath).tag(this).build();
        Call mCall = mOkHttpClient.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                showProgress(false);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream is = response.body().byteStream();
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    try {
                        is = response.body().byteStream();
                        final long total = response.body().contentLength();
                        long sum = 0;
                        File dir = new File(pathFile);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        final File file = new File(dir, fileName);
                        fos = new FileOutputStream(file);
                        while ((len = is.read(buf)) != -1) {
                            sum += len;
                            fos.write(buf, 0, len);
                            final long finalSum = sum;
                        }
                        fos.flush();
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                playVideo(file.getAbsolutePath());
                            }
                        });
                    } finally {
                        if (is != null) is.close();
                        if (fos != null) fos.close();
                    }

                } else {
                    Log.i("TAG00", "文件下载链接失败。。。");
                }
                showProgress(false);
            }
        });

    }

    @Override
    public void showData(Object o) {

    }
}
